//İşletim sistemi ödevi-C
package application;
import java.util.Scanner;

public class MainCA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Dizinin boyutunu girin (N):");//dizinin boyutunu aldik
        int N = scanner.nextInt();

        System.out.println("Her alt kumenin eleman sayisini girin (M):");//alt küme eleman sayisini aldik
        int M = scanner.nextInt();

        int[] array = generateRandomArray(N); // Diziyi rastgele olusturmak icin metod cagirdik

        int chunkSize = M;
        
        //diziyi alt kumeler halinde islemek icin for dongusu kullandik
        for (int i = 0; i < N; i += M) {
            int startIndex = i;
            int endIndex = Math.min(i + M - 1, N - 1);

            // Her alt kümenin elemanlarini tek tek yazdirdik
            System.out.println("Alt Kume Elemanlari:");
            for (int j = startIndex; j <= endIndex; j++) {
                System.out.print(array[j] + " ");
            }
            System.out.println();

            processChunk(array, startIndex, endIndex);//isleme sokma metodunu cagirdik
        }

        scanner.close();
    }
//alt kumeyi isleme sokmak icin metod kullandik
    private static void processChunk(int[] array, int startIndex, int endIndex) {
        int max = array[startIndex];
        int min = array[startIndex];
        int sum = 0;
//alt kumenin elemanlarini isledik
        for (int i = startIndex; i <= endIndex; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
            sum += array[i];
        }
//ortalamayi hesapladik
        double average = (double) sum / (endIndex - startIndex + 1);

        System.out.println("Alt Kume Bilgileri:");
        System.out.println("En Buyuk: " + max);
        System.out.println("En Kucuk: " + min);
        System.out.println("Toplam: " + sum);
        System.out.println("Ortalama: " + average);
        System.out.println();
    }
//rasgele dizi olusturma metodumuzu kullandik
    private static int[] generateRandomArray(int N) {
        int[] array = new int[N];
        for (int i = 0; i < N; i++) {
            array[i] = (int) (Math.random() * 10) + 1; // Rastgele 1-10 arasında sayılar
        }
        return array;
    }
}
