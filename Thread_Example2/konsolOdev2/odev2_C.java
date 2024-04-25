package application;

import java.util.Random;
import java.util.Scanner;

public class odev2_C {
    private static int[][] matris; // Matrisimizi temsil eden 2D dizisi
    private static int boyut; // Matrisin boyutu
    private static boolean negatifVar = false; // Matrisin içinde negatif sayı olup olmadığını belirten bayrak
    private static int negatifSayisi = 0; // Matristeki negatif sayıların toplam sayısı
    private static int pozitifSayisi = 0; // Matristeki pozitif sayıların toplam sayısı
    private static int enBuyukSayi = Integer.MIN_VALUE; // Matristeki en büyük sayı
    private static int enKucukSayi = Integer.MAX_VALUE; // Matristeki en küçük sayı

    public static void main(String[] args) {
        boyutuAl(); // Kullanıcıdan matris boyutunu al
        matrisiOlustur(boyut); // Matrisi rastgele sayılarla doldur
        islemleriYap(matris); // Matris üzerinde işlemleri gerçekleştir
    }

    private static void boyutuAl() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Matris boyutunu girin (10 ile 20000 arasında bir sayı): ");
        while (true) {
            try {
                boyut = scanner.nextInt(); // Kullanıcıdan matris boyutunu al
                if (boyut < 10 || boyut > 20000) {
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

    private static void matrisiOlustur(int boyut) {
        matris = new int[boyut][boyut]; // Matrisi belirtilen boyutta oluştur
        Random rand = new Random();
        for (int i = 0; i < boyut; i++) {
            for (int j = 0; j < boyut; j++) {
                matris[i][j] = rand.nextInt(11) - 5; // Matrisi rastgele sayılarla doldur (-5 ile 5 arasında)
            }
        }
    }

    private static void islemleriYap(int[][] matris) {
        // Matris üzerinde işlemleri gerçekleştir
        for (int i = 0; i < boyut; i++) {
            for (int j = 0; j < boyut; j++) {
                int num = matris[i][j]; // Matrisin ilgili elemanını al
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
        // Sonuçları ekrana yazdırma
        System.out.println("Matris:");
        matrisiYazdir(matris);
        System.out.println("Negatif sayı var mı? " + negatifVar);
        System.out.println("Negatif sayıların sayısı: " + negatifSayisi);
        System.out.println("Pozitif sayıların sayısı: " + pozitifSayisi);
        System.out.println("En büyük sayı: " + enBuyukSayi);
        System.out.println("En küçük sayı: " + enKucukSayi);
    }

    private static void matrisiYazdir(int[][] matris) {
        // Matrisi ekrana yazdır
        for (int i = 0; i < matris.length; i++) {
            for (int j = 0; j < matris[i].length; j++) {
                System.out.print(matris[i][j] + "\t");
            }
            System.out.println();
        }
    }
}

