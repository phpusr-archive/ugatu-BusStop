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
    /** Ширина окна */
    static final int WIDTH = 600
    /** Высота окна */
    static final int HEIGHT = 600
    /** Y позиция для рисования движения */
    static final int Y_POS = WIDTH / 2
    /** Кол-во увеличения пикселей для показа движения */
    static final int PIXEL_INC = 10
    /** Пауза между кадрами (мс) */
    static final int PAUSE_MILIS = 20
    /** Макс. кол-во Пассажиров для генерации */
    static final int MAX_PASSENGER = 10 //TODO потом убрать

    /** Координаты рисования Пассажиров */
    int xPosBus, yPosPassenger
    /** Кол-во Пассажиров для Выхода и Входа */
    int passengerCountOut, passengerCountIn

    /** Буфер для рисования кадра */
    Image scrnBuf
    /** Холст для рисования от Буфера */
    Graphics scrnG
    /** Утилита для работы с автобусами */
    BusParkUtil busParkUtil
    /** Утилита для рисования объектов */
    DrawUtil drawUtil
    /** Текущий Автобус */
    Bus bus

    /** Стоит-ли Автобус на остановке */
    boolean stop = false
    /** Выходят или Заходят в автобус Пассажиры */
    boolean out = true

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
        xPosBus = -1 * bus.width
    }

    public void paint(Graphics g) {
        //Рисование фона
        drawUtil.drawBackground(scrnG, WIDTH, HEIGHT)
        //Рисование автобуса
        drawUtil.drawBus(scrnG, bus, xPosBus, Y_POS, this)

        //Если автобус подъехал к остановке
        int stopX = WIDTH / 2 - bus.width / 2
        if (xPosBus >= stopX && xPosBus <= stopX+PIXEL_INC) {
            if (!stop) { //Если зашли в это условие первый раз, то генерируем кол-во входящих пассажиров
                passengerCountOut = Math.random() * MAX_PASSENGER //TODO макс будет зависеть от кол-во пассажиров в автобусе
                passengerCountIn = Math.random() * MAX_PASSENGER //TODO макс будет зависеть от кол-во ост пассажиров в автобусе и макс вместиимости автобуса
                println "passengerCountOut: ${passengerCountOut}, passengerCountIn: ${passengerCountIn}"
                out = true
                yPosPassenger = Y_POS + bus.height/2
            }

            if (out) { //Анимация выхода пассажиров
                stop = true
                if (passengerCountOut > 0) {
                    drawUtil.drawPassenger(scrnG, (int)WIDTH/2, yPosPassenger)
                    yPosPassenger += PIXEL_INC
                    if (yPosPassenger > HEIGHT) {
                        passengerCountOut--
                        yPosPassenger = Y_POS + bus.height/2
                    }
                } else {
                    out = false
                    yPosPassenger = HEIGHT
                }
            } else { //Анимация входа пассажиров
                if (passengerCountIn > 0) {
                    drawUtil.drawPassenger(scrnG, (int)WIDTH/2, yPosPassenger)
                    yPosPassenger -= PIXEL_INC
                    if (yPosPassenger < Y_POS + bus.height/2) {
                        passengerCountIn--
                        yPosPassenger = HEIGHT
                    }
                } else {
                    stop = false
                }
            }
        }
        //Если автобус не остановлен, то движение автобуса
        if (!stop) xPosBus += PIXEL_INC
        //Если автобус уехал за пределы
        if (xPosBus > WIDTH) {
            bus = busParkUtil.randomBus
            xPosBus = -1 * bus.width
        }

        //Рисование на форме изображения из буфера
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
