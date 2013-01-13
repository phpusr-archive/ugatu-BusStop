package com.phpusr.busstop.frame

import com.phpusr.busstop.entity.Bus

import javax.swing.*
import java.awt.*
import java.awt.image.BufferedImage

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 13.01.13
 * Time: 16:21
 * To change this template use File | Settings | File Templates.
 */
class BusStopFrame extends JFrame {
    static final int WIDTH = 600
    static final int HEIGHT = 600

    int xPos = -250

    Image scrnBuf
    Graphics scrnG
    Bus bus

    boolean stop = false

    BusStopFrame(String s) {
        super(s)
        init()
        setSize(WIDTH, HEIGHT)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        setVisible(true)
    }

    public void init() {
        scrnBuf = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB)
        scrnG = scrnBuf.getGraphics()
        bus = new Bus()
    }

    public void paint(Graphics g) {
        scrnG.setColor(Color.white)
        scrnG.fillRect(0, 0, WIDTH, HEIGHT)
        scrnG.setColor(Color.red)
        scrnG.drawImage(bus.getImage(), xPos, 35, this)

        xPos += 3
        if (xPos > WIDTH) {
            xPos = -250
        }
        if (xPos >= (WIDTH/2-bus.width/2) && !stop) {
            stop = true
            Thread.sleep(2000)
        }
        if (xPos < (WIDTH/2-bus.width/2)) stop = false


        g.drawImage(scrnBuf, 0, 0, this)
    }

    public void update(Graphics g) { paint(g) }

    public void go() {
        while(true) {
            repaint()
            try {
                Thread.sleep(2)
            } catch (InterruptedException e){}
        }
    }

}
