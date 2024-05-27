package application;
import java.util.concurrent.locks.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;

public class odev3_B {

    // PrintJob sınıfı
    static class PrintJob {
        int size; // KB cinsinden

        public PrintJob(int size) {
            this.size = size;
        }
    }

    // PrintQueue sınıfı
    static class PrintQueue {
        private final int MAX_BUFFER_SIZE = 2048; // Maksimum tampon boyutu: 2048 KB
        private final Lock lock = new ReentrantLock(); // Kilitleme mekanizması için kullanılacak lock
        private final Condition notFull = lock.newCondition(); // Tampon dolu olmadığında beklemek için
        private final Condition notEmpty = lock.newCondition(); // Tampon boş olmadığında beklemek için
        private final Queue<PrintJob> buffer = new LinkedList<>(); // İşlerin tutulacağı kuyruk
        private int currentBufferSize = 0; // Mevcut tampon boyutu

        public void addJob(PrintJob job) throws InterruptedException {
            lock.lock(); // Kilidi al
            try {
                // Tampon dolu olduğunda beklenir
                while (currentBufferSize + job.size > MAX_BUFFER_SIZE) {
                    notFull.await();
                }
                // İş tampona eklendi
                buffer.add(job);
                currentBufferSize += job.size;
                // Eklenen işin boyutu ve mevcut tampon boyutu yazdırılır
                System.out.println("Eklenen işin boyutu: " + job.size + " KB. Mevcut tampon boyutu: " + currentBufferSize + " KB.");
                // Tamponun artık boş olmadığına dair sinyal gönderilir
                notEmpty.signal();
            } finally {
                lock.unlock(); // Kilidi serbest bırak
            }
        }

        public PrintJob getJob() throws InterruptedException {
            lock.lock(); // Kilidi al
            try {
                // Tampon boş olduğunda beklenir
                while (buffer.isEmpty()) {
                    notEmpty.await();
                }
                // Bir iş alınır
                PrintJob job = buffer.poll();
                if (job != null) {
                    currentBufferSize -= job.size;
                    // İşlenen işin boyutu ve mevcut tampon boyutu yazdırılır
                    System.out.println("İşlenen işin boyutu: " + job.size + " KB. Mevcut tampon boyutu: " + currentBufferSize + " KB.");
                    // Tamponun artık dolu olmadığına dair sinyal gönderilir
                    notFull.signal();
                }
                return job;
            } finally {
                lock.unlock(); // Kilidi serbest bırak
            }
        }
    }

    // Printer sınıfı
    static class Printer implements Runnable {
        private final PrintQueue printQueue;

        public Printer(PrintQueue printQueue) {
            this.printQueue = printQueue;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    // Yazıcı kuyruğundan bir iş alınır
                    PrintJob job = printQueue.getJob();
                    if (job != null) {
                        // İşin boyutu kadar beklenir (yazdırma simülasyonu)
                        Thread.sleep(job.size * 10);
                        // Yazdırılan işin boyutu yazdırılır
                        System.out.println("Yazdırılan işin boyutu: " + job.size + " KB.");
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // JobProducer sınıfı
    static class JobProducer implements Runnable {
        private final PrintQueue printQueue;
        private final Random random = new Random();

        public JobProducer(PrintQueue printQueue) {
            this.printQueue = printQueue;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    // Rasgele bir iş boyutu üretilir (50-512 KB arası)
                    int jobSize = random.nextInt(463) + 50;
                    // Üretilen iş kuyruğa eklenir
                    PrintJob job = new PrintJob(jobSize);
                    printQueue.addJob(job);
                    // Rasgele bir bekleme süresi eklenir (0-1000 ms arası)
                    Thread.sleep(random.nextInt(1000));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Ana program
    public static void main(String[] args) {
        // Yazıcı kuyruğu oluşturulur
        PrintQueue printQueue = new PrintQueue();
        // Yazıcı ve iş üretici thread'ler oluşturulur
        Printer printer = new Printer(printQueue);
        Thread printerThread = new Thread(printer);
        JobProducer producer = new JobProducer(printQueue);
        Thread producerThread = new Thread(producer);
        // Thread'ler başlatılır
        printerThread.start();
        producerThread.start();
        // Opsiyonel olarak, thread'lerin bitmesini beklemek için kullanılabilir
        try {
            printerThread.join();
            producerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
