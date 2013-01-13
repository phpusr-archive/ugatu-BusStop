package com.phpusr.busstop.frame

import com.phpusr.busstop.entity.Bus

import javax.swing.*
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.image.BufferedImage

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 13.01.13
 * Time: 16:21
 * To change this template use File | Settings | File Templates.
 */
class BusStopFrame extends JFrame {
    private static final int WIDTH = 600
    private static final int HEIGHT = 600

    int xPos = -250

    Image scrnBuf
    Graphics scrnG
    Bus bus

    public static void main(String[] args) {
        BusStopFrame anim_window = new BusStopFrame("Simple Animation");
        anim_window.go();
        anim_window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });
    }

    BusStopFrame(String s) {
        super(s)
        init()
        setSize(WIDTH, HEIGHT)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        setVisible(true)
    }

    public void init() {
        scrnBuf = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        scrnG = scrnBuf.getGraphics();
        bus = new Bus();
    }

    public void paint(Graphics g) {
        scrnG.setColor(Color.white);
        scrnG.fillRect(0, 0, WIDTH, HEIGHT);
        scrnG.setColor(Color.red);
        try {
            scrnG.drawImage(bus.getImage(), xPos, 35, this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        xPos += 3;
        if (xPos > WIDTH) {
            xPos = -250;
        }
        g.drawImage(scrnBuf, 0, 0, this);
    }

    public void update(Graphics g) { paint(g); }

    public void go() {
        while(true) {
            repaint();
            try {
                Thread.sleep(2);
            } catch (InterruptedException e){}
        }
    }

    @Override
    void paintAll(Graphics g) {
        paint(g);
    }
}
