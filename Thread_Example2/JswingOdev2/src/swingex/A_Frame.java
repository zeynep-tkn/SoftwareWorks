package swingex;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class A_Frame extends JFrame {
    private int[][] matrix;
    private int matrixSize;
    private boolean negatifVar = false;
    private int negatifSayisi = 0;
    private int pozitifSayisi = 0;
    private int enBuyukSayi = Integer.MIN_VALUE;
    private int enKucukSayi = Integer.MAX_VALUE;
    private Lock lock = new ReentrantLock();

    private JTextArea textArea;

    public A_Frame() {
        setTitle("Matris İşlemleri");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton button = new JButton("Matris İşlemlerini Başlat");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getUserInput();
                initializeMatrix(matrixSize);
                performOperations(matrix);
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);

        add(panel);
    }

    private void getUserInput() {
        String input = JOptionPane.showInputDialog("Matris boyutunu girin (10 ile 20000 arasında bir sayı): ");
        try {
            matrixSize = Integer.parseInt(input);
            if (matrixSize < 10 || matrixSize > 20000) {
                JOptionPane.showMessageDialog(null, "Geçersiz boyut. Lütfen 10 ile 20000 arasında bir sayı girin.");
                getUserInput();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Geçersiz giriş. Lütfen bir tam sayı girin.");
            getUserInput();
        }
    }

    private void initializeMatrix(int size) {
        matrix = new int[size][size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rand.nextInt(11) - 5;
            }
        }
    }

    private void performOperations(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                int num = matrix[i][j];
                lock.lock();
                try {
                    if (num < 0) {
                        negatifVar = true;
                    }
                    if (num < 0) {
                        negatifSayisi++;
                    } else {
                        pozitifSayisi++;
                    }
                    if (num > enBuyukSayi) {
                        enBuyukSayi = num;
                    }
                    if (num < enKucukSayi) {
                        enKucukSayi = num;
                    }
                } finally {
                    lock.unlock();
                }
                sb.append(num).append("\t");
            }
            sb.append("\n");
        }

        sb.append("Negatif sayı var mı? ").append(negatifVar).append("\n");
        sb.append("Negatif sayıların sayısı: ").append(negatifSayisi).append("\n");
        sb.append("Pozitif sayıların sayısı: ").append(pozitifSayisi).append("\n");
        sb.append("En büyük sayı: ").append(enBuyukSayi).append("\n");
        sb.append("En küçük sayı: ").append(enKucukSayi).append("\n");

        textArea.setText("Matris:\n" + sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new A_Frame().setVisible(true);
            }
        });
    }
}
