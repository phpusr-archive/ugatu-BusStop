package com.phpusr.busstop;

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 12.01.13
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */
import java.awt.*;
import java.awt.event.*;

class Animation extends Frame {
    private Image[] img = new Image[10];
    private int count;

    public static void main(String[] args) {
        Animation anim_window = new Animation("Simple Animation");
        anim_window.go();
        anim_window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });
    }

    Animation (String s) {
        super(s);
        MediaTracker tr=new MediaTracker(this);
        for (int k=0; k<4; k++) {
            img[k] = getToolkit().getImage("image"+(k+1)+".gif");
            tr.addImage(img[k],0);
        } try {
            tr.waitForAll();
        }
        catch (InterruptedException e){}
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
