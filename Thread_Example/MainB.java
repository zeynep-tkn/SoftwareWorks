//İşletim sistemi ödevi-B
package application;
import java.util.Random;
//NWorkerThread adinda sinif actik bu sinif (is parcacigini temsilen) Thread Sinifindan gelen bir alt sinif.
//matris,satir indisi,min, max vb. özellikleri tanimladik
class NWorkerThread extends Thread {
    private int[][] matrix; 
    private int row;
    private int max;
    private int min;
    private int sum;
    private double average;
//kurucu metodu; matrisi ve islenecek satırı alir, diğer degiskenleri baslangic degerlerine ayarlar
    public NWorkerThread(int[][] matrix, int row) {
        this.matrix = matrix;
        this.row = row;
        this.max = Integer.MIN_VALUE;
        this.min = Integer.MAX_VALUE;
        this.sum = 0;
    }
    
  //run metodu is parcaciginin calisma mantigini icerir
  //Is parcacigi belirtilen satirin elemanlarini dolasir; max, min ve sum ve average degerlerini hesaplar

    public void run() {
        int[] rowArray = matrix[row];
        for (int num : rowArray) {
            if (num > max) {
                max = num;
            }
            if (num < min) {
                min = num;
            }
            sum += num;
        }
        average = (double) sum / rowArray.length;
    }

    //get metodlarini, is parcaciginin hesapladigi degerlere erismek icin kullandık
    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public int getSum() {
        return sum;
    }

    public double getAverage() {
        return average;
    }
}
//Main ile N degerine gore rastgele degerli matris olusturduk
public class MainB {
    public static void main(String[] args) {
        int N = 10; // Matris boyutu
        int[][] matrix = generateRandomMatrix(N);
        NWorkerThread[] threads = new NWorkerThread[N];

        // Her bir satır için iş parçası oluşturduk ve çalıştırdık
        for (int i = 0; i < N; i++) {
            threads[i] = new NWorkerThread(matrix, i);
            threads[i].start();
        }

        // join ile tum is parcaciklarinin paralel olarak calismasini sagladik
        try {
            for (int i = 0; i < N; i++) {
                threads[i].join();
            }
        } 
        //hata durumunda ekrana yazdirmasi icin kullandik
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        // is parcaciklarinin hesapladigi degerleri ekrana yazdirdik
        for (int i = 0; i < N; i++) {
            System.out.println("Satır " + i + " için bilgiler:");
            System.out.println("Alt Kume Elemanlari: " + arrayToString(matrix[i]));
            System.out.println("En Büyük: " + threads[i].getMax());
            System.out.println("En Küçük: " + threads[i].getMin());
            System.out.println("Toplam: " + threads[i].getSum());
            System.out.println("Ortalama: " + threads[i].getAverage());
            System.out.println();
        }
    }
//belirtilen boyutta rastgele matris olusturduk
    private static int[][] generateRandomMatrix(int N) {
        int[][] matrix = new int[N][N];
        Random random = new Random();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = random.nextInt(10) + 1; // elemanlari, 1 den 10 a kadar rastgele sayilar
            }
        }

        return matrix;
    }

    // Yardimci fonksiyon olarak kullandigimiz fonksiyon diziyi String'e donusturmemizi sagladi
    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i != array.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
