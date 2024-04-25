//java swing deneme
package swingex;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
//C şıkkı arayüz 
public class C_Frame extends JFrame {
    private int[][] matris;
    private int boyut;
    private boolean negatifVar = false;
    private int negatifSayisi = 0;
    private int pozitifSayisi = 0;
    private int enBuyukSayi = Integer.MIN_VALUE;
    private int enKucukSayi = Integer.MAX_VALUE;

    private JTextArea textArea;

    public C_Frame() {
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
                boyutuAl();
                matrisiOlustur(boyut);
                islemleriYap(matris);
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);

        add(panel);
    }

    private void boyutuAl() {
        String input = JOptionPane.showInputDialog("Matris boyutunu girin (10 ile 20000 arasında bir sayı): ");
        try {
            boyut = Integer.parseInt(input);
            if (boyut < 10 || boyut > 20000) {
                JOptionPane.showMessageDialog(null, "Geçersiz boyut. Lütfen 10 ile 20000 arasında bir sayı girin.");
                boyutuAl();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Geçersiz giriş. Lütfen bir tam sayı girin.");
            boyutuAl();
        }
    }

    private void matrisiOlustur(int boyut) {
        matris = new int[boyut][boyut];
        Random rand = new Random();
        for (int i = 0; i < boyut; i++) {
            for (int j = 0; j < boyut; j++) {
                matris[i][j] = rand.nextInt(11) - 5;
            }
        }
    }

    private void islemleriYap(int[][] matris) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < boyut; i++) {
            for (int j = 0; j < boyut; j++) {
                int num = matris[i][j];
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
                new C_Frame().setVisible(true);
            }
        });
    }
}
