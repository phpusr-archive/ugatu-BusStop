package com.phpusr.busstop.test.java;

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 12.01.13
 * Time: 13:12
 * To change this template use File | Settings | File Templates.
 */

import javax.swing.*;
import java.awt.*;

/**
 * Вставляет изображение на форму
 */

class Graphics2DDrawImage {
    public static void main(String[] a) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 300, 300);
        window.getContentPane().add(new MyCanvas());
        window.setVisible(true);
    }
}

class MyCanvas extends JComponent {

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Image img1 = Toolkit.getDefaultToolkit().getImage("src\\resources\\com\\phpusr\\busstop\\img\\bus\\pacan_bus.gif");
        g2.drawImage(img1, 10, 10, null);
        g2.finalize();
    }
}
