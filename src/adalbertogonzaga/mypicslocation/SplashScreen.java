/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package adalbertogonzaga.mypicslocation;

import java.awt.*;
import javax.swing.*;

/*
* Surrupiado de https://www.devmedia.com.br/desenvolvendo-splash-screens-para-suas-aplicacoes/1667
*
* Direto do tunel do tempo.
*
* */

public class SplashScreen extends JWindow {

    private int duration;

    public SplashScreen(int d) {
        duration = d;
    }

    public void showSplash() {
        JPanel content = (JPanel)getContentPane();
        content.setBackground(Color.white);

        // Configura a posição e o tamanho da janela
        int width = 512;
        int height = 335;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width-width)/2;
        int y = (screen.height-height)/2;
        setBounds(100,100,width,height);

        // Constrói o splash screen
        JLabel label = new JLabel(new ImageIcon("images/18090602738_05ad6262da_k.jpg"));
        JLabel copyrt = new JLabel
                ("Adalberto Gonzaga, Curió", JLabel.CENTER);
        copyrt.setFont(new Font("Sans-Serif", Font.ITALIC, 14));
        content.add(label, BorderLayout.CENTER);
        content.add(copyrt, BorderLayout.SOUTH);
        Color oraRed = new Color(0, 107, 156,  255);
        content.setBorder(BorderFactory.createLineBorder(oraRed, 2));
        // Torna visível
        setVisible(true);

        // Espera ate que os recursos estejam carregados
        try { Thread.sleep(duration); } catch (Exception e) {}
        setVisible(false);
    }

    public void showSplashAndExit() {
        showSplash();
        this.setVisible(false);
    }

}
