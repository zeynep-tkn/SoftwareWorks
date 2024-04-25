package application;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class odev2_A {
    private static int[][] matrix; // Matrisi temsil eden dizisi
    private static int matrixSize; // Matrisin boyutu
    private static boolean negatifVar = false; // Matrisin içinde negatif sayı olup olmadığını belirtec
    private static int negatifSayisi = 0; // Matristeki negatif sayıların toplam sayısı
    private static int pozitifSayisi = 0; // Matristeki pozitif sayıların toplam sayısı
    private static int enBuyukSayi = Integer.MIN_VALUE; // Matristeki en büyük sayı
    private static int enKucukSayi = Integer.MAX_VALUE; // Matristeki en küçük sayı
    private static Lock lock = new ReentrantLock(); // ReentrantLock nesnesi, çoklu iş parçacığı arasında güvenliğini sağlamak için kullanılır

    public static void main(String[] args) {
        getUserInput(); // Kullanıcıdan matris boyutunu al
        initializeMatrix(matrixSize); // Matrisi rastgele sayılarla doldur

        Thread[] threads = new Thread[matrixSize]; // Her satırı işleyecek iş parçacığı dizisi

        // Her bir satır için bir iş parçacığı oluştur ve çalıştır
        for (int i = 0; i < matrixSize; i++) {
            final int row = i;
            threads[i] = new Thread(() -> {
                processRow(row); // Satırı işle
            });
            threads[i].start(); // İş parçacığını başlat
        }

        // Tüm iş parçacıklarının tamamlanmasını bekleyelim
        for (Thread thread : threads) {
            try {
                thread.join(); // İş parçacığının tamamlanmasını bekleyelim
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Sonuçları ve matrisi ekrana yazdır
        System.out.println("Matris:");
        printMatrix(matrix);
        System.out.println("Negatif sayı var mı? " + negatifVar);
        System.out.println("Negatif sayıların sayısı: " + negatifSayisi);
        System.out.println("Pozitif sayıların sayısı: " + pozitifSayisi);
        System.out.println("En büyük sayı: " + enBuyukSayi);
        System.out.println("En küçük sayı: " + enKucukSayi);
    }

    private static void getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Matris boyutunu girin (10 ile 20000 arasında bir sayı): ");
        while (true) {
            try {
                matrixSize = scanner.nextInt(); // Kullanıcıdan matris boyutunu al
                if (matrixSize < 10 || matrixSize > 20000) {
                    System.out.print("Geçersiz boyut. Lütfen 10 ile 20000 arasında bir sayı girin: ");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.print("Geçersiz giriş. Lütfen bir tam sayı girin: ");
                scanner.next(); // Hatalı girişi temizle
            }
        }
    }

    private static void initializeMatrix(int size) {
        matrix = new int[size][size]; // Matrisi belirtilen boyutta oluştur
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rand.nextInt(11) - 5; // Matrisi rastgele sayılarla doldur (-5 ile 5 arasında)
            }
        }
    }

    private static void processRow(int row) {
        for (int num : matrix[row]) {
            lock.lock(); // Kilit al
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
                lock.unlock(); // Kilidi serbest bırak
            }
        }
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }
}

