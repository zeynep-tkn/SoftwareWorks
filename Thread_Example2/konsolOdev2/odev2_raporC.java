package application;
import java.util.Random;
import java.util.Scanner;

public class odev2_raporC {
    private static int boyut;
    private static boolean negatifVar = false;
    private static int negatifSayisi = 0;
    private static int pozitifSayisi = 0;
    private static int enBuyukSayi = Integer.MIN_VALUE;
    private static int enKucukSayi = Integer.MAX_VALUE;

    public static void main(String[] args) {
        System.out.println("N\tZaman (ns)");

        for (int N = 10; N <= 100; N += 10) {
            long baslangicZamani = System.nanoTime(); // Başlangıç zamanını kaydet

            boyut = N;
            islemleriYap();

            long bitisZamani = System.nanoTime(); // Bitiş zamanını kaydet
            long calismaZamani = bitisZamani - baslangicZamani; // Çalışma zamanını hesapla

            System.out.println(N + "\t" + calismaZamani);
        }
    }

    private static void islemleriYap() {
        int[][] matris = matrisOlustur();
        for (int i = 0; i < boyut; i++) {
            for (int j = 0; j < boyut; j++) {
                int num = matris[i][j];
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
            }
        }
    }

    private static int[][] matrisOlustur() {
        int[][] matris = new int[boyut][boyut];
        Random rand = new Random();
        for (int i = 0; i < boyut; i++) {
            for (int j = 0; j < boyut; j++) {
                matris[i][j] = rand.nextInt(11) - 5; // -5 ile 5 arasında rastgele sayılar
            }
        }
        return matris;
    }
}

