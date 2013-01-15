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
    static final int PIXEL_INC = 10
    static final int PAUSE_MILIS = 20
    static final int MAX_PASSENGER = 10

    int xPos, yPosPeople
    int passengerCountIn

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
        yPosPeople = HEIGHT
    }

    public void paint(Graphics g) {
        //Рисование фона
        drawUtil.drawBackground(scrnG, WIDTH, HEIGHT)
        //Рисование автобуса
        scrnG.drawImage(bus.image, xPos, Y_POS, this)

        //Если автобус подъехал к остановке
        int stopX = WIDTH / 2 - bus.width / 2
        if (xPos >= stopX && xPos <= stopX+PIXEL_INC) {
            if (!stop) { //Если зашли в это условие первый раз, то генерируем кол-во входящих пассажиров
                passengerCountIn = Math.random() * MAX_PASSENGER
                println "passengerCountIn: ${passengerCountIn}"
            }
            if (passengerCountIn > 0) {
                stop = true
                scrnG.fillOval((int)WIDTH/2, yPosPeople, 10, 10)
                yPosPeople -= PIXEL_INC
                if (yPosPeople < Y_POS + bus.height/2) {
                    passengerCountIn--
                    yPosPeople = HEIGHT
                }
            } else {
                stop = false
            }
        }
        //Если автобус еще не доехал до остановки
        if (xPos < (WIDTH/2-bus.width/2)) {
            stop = false
        }
        if (!stop) xPos += PIXEL_INC
        //Если автобус уехал за пределы
        if (xPos > WIDTH) {
            bus = busParkUtil.randomBus
            xPos = -1 * bus.width
        }

        g.drawImage(scrnBuf, 0, 0, this)
    }

    public void update(Graphics g) { paint(g) }

    public void go() {
        while(true) {
            repaint()
            try {
                Thread.sleep(PAUSE_MILIS)
            } catch (InterruptedException e){}
        }
    }

}
