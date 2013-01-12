package com.phpusr.busstop.test.applet; /**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 12.01.13
 * Time: 12:42
 * To change this template use File | Settings | File Templates.
 */
import java.applet.Applet;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Апплет показывающий движующую строку
 */
public class ImgMover extends Applet implements Runnable {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    String mesag ="Scrolling text is a simple animation.";
    Font mfont = new Font("TimesRoman",Font.BOLD, 24);
    int xPos = -250;

    Image scrnBuf;
    Graphics scrnG;

    Thread runner;

    public void init() {
        scrnBuf = createImage(WIDTH, HEIGHT);
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
        scrnG.fillRect(0, 0, WIDTH, HEIGHT);
        scrnG.setColor(Color.red);
        /*scrnG.setFont(mfont);
        scrnG.drawString(mesag, 35, 35);*/
        try {
            URL url = new URL("file:/d:/Java/OOP/kursach/bus/");
            Image image = getImage(url, "pacan_bus.gif");
            scrnG.drawImage(image, xPos, 35, this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        xPos += 3;
        if (xPos > WIDTH) {
            xPos = -250;
        }
        g.drawImage(scrnBuf, 0, 0, this);
    }

}
