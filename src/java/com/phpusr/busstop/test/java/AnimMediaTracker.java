package com.phpusr.busstop.test.java;

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 12.01.13
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Накладывает друг на друга картинки
 */
class AnimMediaTracker extends Frame {
    private Image[] img = new Image[10];
    private int count;

    public static void main(String[] args) {
        AnimMediaTracker anim_window = new AnimMediaTracker("Simple Animation");
        anim_window.go();
        anim_window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });
    }

    AnimMediaTracker(String s) {
        super(s);
        MediaTracker tr=new MediaTracker(this);
        for (int k=0; k<4; k++) {
            img[k] = getToolkit().getImage("src\\resources\\com\\phpusr\\busstop\\img\\test\\image"+(k+1)+".gif");
            tr.addImage(img[k],0);
        }
        try {
            tr.waitForAll();
        } catch (InterruptedException e){}
        setSize(400, 300);
        setVisible(true);
    }

    public void paint(Graphics g) {
        g.drawImage(img[count%10], 0, 0, this);
    }

    public void update(Graphics g) { paint(g); }

    public void go() {
        while(count < 100) {
            repaint();
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e){}
            count++;
        }
    }

}
