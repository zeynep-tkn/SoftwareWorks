package application;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class odev2_raporA {
    private static int[][] matrix;
    private static int matrixSize;
    private static boolean negatifVar = false;
    private static int negatifSayisi = 0;
    private static int pozitifSayisi = 0;
    private static int enBuyukSayi = Integer.MIN_VALUE;
    private static int enKucukSayi = Integer.MAX_VALUE;
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        System.out.println("N\tZaman (ns)");
        for (int i = 10; i <= 100; i += 10) {
            matrixSize = i;
            initializeMatrix(matrixSize);

            long startTime = System.nanoTime();

            Thread[] threads = new Thread[matrixSize];

            // Her satır için bir thread oluşturma ve çalıştırma
            for (int j = 0; j < matrixSize; j++) {
                final int row = j;
                threads[j] = new Thread(() -> {
                    processRow(row);
                });
                threads[j].start();
            }

            // Tüm threadlerin bitmesini bekleyelim
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            long endTime = System.nanoTime();
            long totalTime = endTime - startTime;

            System.out.println(matrixSize + "\t" + totalTime);
        }
    }

    private static void initializeMatrix(int size) {
        matrix = new int[size][size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rand.nextInt(11) - 5; // -5 ile 5 arasında rastgele sayılar
            }
        }
    }

    private static void processRow(int row) {
        for (int num : matrix[row]) {
            lock.lock();
            try {
                // a) Negatif sayı kontrolü
                if (num < 0) {
                    negatifVar = true;
                }
                // b) Negatif ve pozitif sayı sayımı
                if (num < 0) {
                    negatifSayisi++;
                } else {
                    pozitifSayisi++;
                }
                // c) En büyük ve en küçük sayı tespiti
                if (num > enBuyukSayi) {
                    enBuyukSayi = num;
                }
                if (num < enKucukSayi) {
                    enKucukSayi = num;
                }
            } finally {
                lock.unlock();
            }
        }
    }
}

