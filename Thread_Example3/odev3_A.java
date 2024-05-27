package application;
import java.util.concurrent.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

//yazıcıya gonderilecek gorevleri temsil eder
class PrintJob {
    int size; // in KB

    public PrintJob(int size) {
        this.size = size;
    }
}

//yazıcı kuyrugunu yonetir
class PrintQueue {
    private final int MAX_BUFFER_SIZE = 2048; // yazıcı kuyrugunun max kapasitesi:2048 KB
    private final Semaphore empty;
    private final Semaphore full;
    private final Semaphore mutex;
    private final Queue<PrintJob> buffer = new LinkedList<>();
    private int currentBufferSize = 0;

    public PrintQueue() {
        empty = new Semaphore(MAX_BUFFER_SIZE);
        full = new Semaphore(0);
        mutex = new Semaphore(1);
    }

    public void addJob(PrintJob job) throws InterruptedException {
        empty.acquire(job.size);
        mutex.acquire();
        buffer.add(job);
        currentBufferSize += job.size;
        System.out.println("Boyutu: " + job.size + " KB olan iş eklendi. Güncel tampon boyutu: " + currentBufferSize + " KB.");

        mutex.release();
        full.release(job.size);
    }

    public PrintJob getJob() throws InterruptedException {
        full.acquire();//kuyrugun dolması beklenir
        mutex.acquire();
        PrintJob job = buffer.poll();
        if (job != null) {
            currentBufferSize -= job.size;
            System.out.println("Boyutu: " + job.size + " KB olan iş işleniyor. Güncel tampon boyutu: " + currentBufferSize + " KB.");
        }
        mutex.release();
        empty.release(job.size);
        return job;
    }
}
//yazıcıyı simüle eder
class Printer implements Runnable {
    private final PrintQueue printQueue;

    public Printer(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                PrintJob job = printQueue.getJob();
                if (job != null) {
                    Thread.sleep(job.size * 10); // yazdırma simülasyon süresi
                    System.out.println("Boyutu: " + job.size + " KB olan iş yazdırıldı.");

                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class JobProducer implements Runnable {
    private final PrintQueue printQueue;
    private final Random random = new Random();

    public JobProducer(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int jobSize = random.nextInt(463) + 50; //50 ila 512 KB arasında rastgele boyut
                PrintJob job = new PrintJob(jobSize);
                printQueue.addJob(job);
                Thread.sleep(random.nextInt(1000)); //Bir sonraki işin eklenmesinden önce rastgele gecikme
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class odev3_A {
    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();//yazıcı kuyrugunu yonetir
        Printer printer = new Printer(printQueue);
        Thread printerThread = new Thread(printer);

        JobProducer producer = new JobProducer(printQueue);
        Thread producerThread = new Thread(producer);

        printerThread.start(); //yaziciyi simule eden thread olusturur
        producerThread.start(); //yazıcıya is ureten thread olusturur
    }
}
