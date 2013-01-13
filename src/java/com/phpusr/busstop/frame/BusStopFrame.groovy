package com.phpusr.busstop.frame

import com.phpusr.busstop.entity.Bus
import com.phpusr.busstop.util.BusParkUtil
import com.phpusr.busstop.util.DrawUtil

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

/**
 * Форма для отображения симуляции Автобусной остановки
 */
class BusStopFrame extends JFrame {
    static final int WIDTH = 600
    static final int HEIGHT = 600
    static final int Y_POS = 100
    static final int STOP_MILIS = 1000

    int xPos

    Image scrnBuf
    Graphics scrnG
    BusParkUtil busParkUtil
    DrawUtil drawUtil
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
        busParkUtil = new BusParkUtil()
        drawUtil = new DrawUtil()
        bus = busParkUtil.randomBus
        xPos = -1 * bus.width
    }

    public void paint(Graphics g) {
        //Рисование фона
        drawUtil.drawBackground(scrnG, WIDTH, HEIGHT)
        //Рисование автобуса
        scrnG.drawImage(bus.image, xPos, Y_POS, this)

        //Если автобус уехал за пределы
        if (xPos > WIDTH) {
            bus = busParkUtil.randomBus
            xPos = -1 * bus.width
        }
        //Если автобус подъехал к остановке
        if (xPos >= (WIDTH/2-bus.width/2) && !stop) {
            stop = true
            Thread.sleep(STOP_MILIS)
        }
        //Если автобус еще не доехал до остановки
        if (xPos < (WIDTH/2-bus.width/2)) {
            stop = false
        }
        xPos += 3

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
