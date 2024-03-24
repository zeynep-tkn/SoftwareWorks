//İşletim sistemi ödevi-A 
package application;
import java.util.Scanner;

//MWorkerThread adinda sinif actik bu sinif (is parcacigini temsilen) Thread Sinifindan gelen bir alt sinif.
class MWorkerThread extends Thread {
    private int[] array;
    private int startIndex;
    private int endIndex;
    
   //MWorkerThread sinifinin kurucu metodu ile degiskenleri baslattik
    public MWorkerThread(int[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
//run metodu kullanilarak alt kume elemanlari ile dongu olusturduk 
    public void run() {
        int max = array[startIndex];
        int min = array[startIndex];
        int sum = 0;

        System.out.println("Alt Kume Elemanlari:");
        for (int i = startIndex; i <= endIndex; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
            sum += array[i];
            System.out.print(array[i] + " ");
        }
        System.out.println();

        double average = (double) sum / (endIndex - startIndex + 1);
        
        //bulunan degerleri ekrana yazdirdik
        synchronized (System.out) {
            System.out.println("Alt Kume Bilgileri:");
            System.out.println("En Buyuk: " + max);
            System.out.println("En Kucuk: " + min);
            System.out.println("Toplam: " + sum);
            System.out.println("Ortalama: " + average);
            System.out.println();
        }
    }
}
//ana fonksiyonda (N,M) degerleri alinarak rastgele dizi olusturduk
public class MainA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Dizinin boyutunu girin (N):");
        int N = scanner.nextInt();

        System.out.println("Her alt kumenin eleman sayisini girin (M):");
        int M = scanner.nextInt();
     // Fonksiyonunu cagirarak rastgele eleman olustur 
        int[] array = generateRandomArray(N); 

        for (int i = 0; i < N; i += M) {
            int startIndex = i;
            int endIndex = Math.min(i + M - 1, N - 1);
     //Thread ile dizinin islenmesini sağladik
            MWorkerThread thread = new MWorkerThread(array, startIndex, endIndex);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        scanner.close();
    }
  //Bu fonksiyonla kullanicinin girdigi boyutta rastgele dizi olusturduk 
    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 10) + 1;
        }
        return array;
    }
}

