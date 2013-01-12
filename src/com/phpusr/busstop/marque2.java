package com.phpusr.busstop;

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 12.01.13
 * Time: 12:42
 * To change this template use File | Settings | File Templates.
 */
import java.awt.*;

public class marque2 extends
        java.applet.Applet implements Runnable {

    String mesag ="Scrolling text is a simple animation.";
    Font mfont = new Font("TimesRoman",Font.BOLD, 24);
    int Xposition = 200;

    Image scrnBuf;
    Graphics scrnG;

    Thread runner;

    public void init() {
        scrnBuf = createImage(600,50);
        scrnG = scrnBuf.getGraphics();
    }

    public void start() {
        if (runner == null); {
            runner = new Thread(this);
            runner.start();
        }
    }

    public void stop() {
        if (runner != null);
        {
            runner.stop();
            runner = null;

        }
    }

    public void run() {
        while(true) {
            repaint();
            try {Thread.sleep(2);}
            catch(InterruptedException e) { }
        }
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        scrnG.setColor(Color.white);
        scrnG.fillRect(0, 0, 600, 100);
        scrnG.setColor(Color.red);
        /*scrnG.setFont(mfont);
        scrnG.drawString(mesag, 35, 35);*/

        scrnG.drawRect(Xposition, 35, 30, 30);

        Xposition --;
        if (Xposition < -400) {
            Xposition = 200;
        }
        g.drawImage(scrnBuf, 0, 0, this);
    }

}
