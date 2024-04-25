package application;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class odev2_raporB {
    private static int[][] matris;
    private static int boyut;
    private static boolean negatifVar = false;
    private static int negatifSayisi = 0;
    private static int pozitifSayisi = 0;
    private static int enBuyukSayi = Integer.MIN_VALUE;
    private static int enKucukSayi = Integer.MAX_VALUE;
    private static Semaphore semaphore = new Semaphore(1); // Sadece bir thread'in aynı anda işlem yapmasına izin vermek için

    public static void main(String[] args) {
        System.out.println("N\tZaman (ns)");

        for (int N = 10; N <= 100; N += 10) {
            long baslangicZamani = System.nanoTime(); // Başlangıç zamanını kaydet

            boyut = N;
            matrisiOlustur(boyut);

            Thread[] threadler = new Thread[boyut];

            // Her satır için bir thread oluşturma ve çalıştırma
            for (int i = 0; i < boyut; i++) {
                final int satir = i;
                threadler[i] = new Thread(() -> {
                    satirIsle(satir);
                });
                threadler[i].start();
            }

            // Tüm threadlerin bitmesini bekleyelim
            for (Thread thread : threadler) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            long bitisZamani = System.nanoTime(); // Bitiş zamanını kaydet
            long calismaZamani = bitisZamani - baslangicZamani; // Çalışma zamanını hesapla

            System.out.println(N + "\t" + calismaZamani);
        }
    }

    private static void matrisiOlustur(int boyut) {
        matris = new int[boyut][boyut];
        Random rand = new Random();
        for (int i = 0; i < boyut; i++) {
            for (int j = 0; j < boyut; j++) {
                matris[i][j] = rand.nextInt(11) - 5; // -5 ile 5 arasında rastgele sayılar
            }
        }
    }

    private static void satirIsle(int satir) {
        for (int num : matris[satir]) {
            try {
                semaphore.acquire(); // Kritik bölgeye giriş
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release(); // Kritik bölgeden çıkış
            }
        }
    }
}
