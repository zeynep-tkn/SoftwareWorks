//İşletim sistemi ödevi-C
package application;
import java.util.Random;

public class MainCB {
    public static void main(String[] args) {
        int N = 10; // Matris boyutunu tanimladik
        int[][] matrix = generateRandomMatrix(N);//rastgele matris olusturmasi icin metod cagirdik

        // her bir satir icin deger hesaplayip ekrana yazdirdik
        for (int i = 0; i < N; i++) {
            System.out.println("Satır " + i + " için bilgiler:");
            System.out.println("Alt Kume Elemanlari: " + arrayToString(matrix[i]));
            System.out.println("En Büyük: " + findMax(matrix[i]));
            System.out.println("En Küçük: " + findMin(matrix[i]));
            System.out.println("Toplam: " + calculateSum(matrix[i]));
            System.out.println("Ortalama: " + calculateAverage(matrix[i]));
            System.out.println();
        }
    }
//belirtilen boyutta rastgele matris olusturmak icin metod actik 
    private static int[][] generateRandomMatrix(int N) {
        int[][] matrix = new int[N][N];
        Random random = new Random();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = random.nextInt(10) + 1; // 1 den 10 a kadar rastgele sayılar sectik
            }
        }

        return matrix;
    }

    // Yardımcı fonksiyon kullandik bir dizinin maksimum değerini bulmak icin
    private static int findMax(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int num : array) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    // Yardımcı fonksiyon kullandik bir dizinin minimum değerini bulmak icin
    private static int findMin(int[] array) {
        int min = Integer.MAX_VALUE;
        for (int num : array) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }

    // Yardımcı fonksiyon kullandik bir dizinin toplamını hesaplamak icin
    private static int calculateSum(int[] array) {
        int sum = 0;
        for (int num : array) {
            sum += num;
        }
        return sum;
    }

    // Yardımcı fonksiyon kullandik bir dizinin ortalamasını hesaplamak icin
    private static double calculateAverage(int[] array) {
        int sum = calculateSum(array);
        return (double) sum / array.length;
    }

    // Yardımcı fonksiyon kullandik bir diziyi String'e dönüştürmek icin
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
