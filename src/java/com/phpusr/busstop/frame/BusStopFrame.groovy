package com.phpusr.busstop.frame

import com.phpusr.busstop.consts.BusStopConsts
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
    static final int Y_POS = WIDTH / 3
    /** Кол-во увеличения пикселей для показа движения */
    static final int PIXEL_INC = 10
    /** Пауза между кадрами (мс) */
    static final int PAUSE_MILIS = 20

    /** Координаты рисования Пассажиров */
    int xPosBus, yPosPassenger
    /** Кол-во Пассажиров для Выхода и Входа */
    int passengerCountOut, passengerCountOutConst, passengerCountIn, passengerCountInConst
    /** Кол-во остановок */
    int stopCount

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
    /** Форма управления */
    ControlFrame controlFrame

    /** Стоит-ли Автобус на остановке */
    boolean stop
    /** Выходят или Заходят в автобус Пассажиры */
    boolean out
    /** Пауза */
    boolean pause

    BusStopFrame(String s, ControlFrame controlFrame) {
        super(s)
        this.controlFrame = controlFrame
        init()
        setSize(WIDTH, HEIGHT)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        setVisible(true)
    }

    /** Инициализация переменных для Рисования */
    private void init() {
        scrnBuf = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB)
        scrnG = scrnBuf.getGraphics()
        busParkUtil = new BusParkUtil(controlFrame.model)
        drawUtil = new DrawUtil()
        bus = busParkUtil.randomBus
        xPosBus = -1 * bus.width

        stop = false
        out = true
    }

    /** Функция Рисования */
    void paint(Graphics g) {
        //Рисование фона
        drawUtil.drawBackground(scrnG, WIDTH, HEIGHT)
        //Рисование автобуса
        drawUtil.drawBus(scrnG, bus, xPosBus, Y_POS, this)

        //Если автобус подъехал к остановке
        int stopX = WIDTH / 2 - bus.width / 2
        if (xPosBus >= stopX && xPosBus <= stopX+PIXEL_INC) {
            if (!stop) { //Если зашли в это условие первый раз, то генерируем кол-во входящих пассажиров
                stopCount++
                passengerCountOut = Math.round(Math.random() * bus.passengerCount)
                passengerCountOutConst = passengerCountOut
                passengerCountIn = Math.round(Math.random() * bus.freeSeat)
                passengerCountInConst = passengerCountIn
                out = true
                yPosPassenger = Y_POS + bus.height/2

                if (BusStopConsts.busLog) println '-----------------------------------------------------------------------------------'
                if (BusStopConsts.paintLog) println "$stopCount\t Кол-во выходящих пассажиров: ${passengerCountOut}/$bus.passengerCount,\t\t Кол-во входящих пассажиров: ${passengerCountIn}/$bus.freeSeat"
                if (BusStopConsts.busLog) println '-----------------------------------------------------------------------------------'
                if (BusStopConsts.statBusLog && stopCount % 10 == 0) busParkUtil.printStat()
                updateBusInfo()
            }

            if (out) { //Анимация выхода пассажиров
                stop = true
                if (passengerCountOut > 0) {
                    drawUtil.drawPassenger(scrnG, (int)WIDTH/2, yPosPassenger)
                    yPosPassenger += PIXEL_INC
                    if (yPosPassenger > HEIGHT) { //Пассажир вышел из Автобуса
                        passengerCountOut--
                        bus.delPassenger()
                        yPosPassenger = Y_POS + bus.height/2
                        updateBusInfo()
                    }
                } else {
                    out = false
                    yPosPassenger = HEIGHT
                }
            } else { //Анимация входа пассажиров
                if (passengerCountIn > 0) {
                    drawUtil.drawPassenger(scrnG, (int)WIDTH/2, yPosPassenger)
                    yPosPassenger -= PIXEL_INC
                    if (yPosPassenger < Y_POS + bus.height/2) { //Пассажир сел на Автобус
                        passengerCountIn--
                        bus.addPassenger()
                        yPosPassenger = HEIGHT
                        updateBusInfo()
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
            controlFrame.lblImgIcon = new ImageIcon(bus.image)
            xPosBus = -1 * bus.width
        }

        //Рисование на форме изображения из буфера
        g.drawImage(scrnBuf, 0, 0, this)
    }

    /** Срабатывает при Изменении свойств в Автобусе */
    private updateBusInfo() {
        controlFrame.setPassengerCountOut(passengerCountOutConst - passengerCountOut, passengerCountOutConst, bus.passengerCountOut)
        controlFrame.setPassengerCountIn(passengerCountInConst - passengerCountIn, passengerCountInConst, bus.passengerCountIn)
        busParkUtil.updateTblStat(controlFrame.tblStat, controlFrame.model, bus)
    }

    void update(Graphics g) { paint(g) }

    /** Запуск Рисования в отдельном потоке */
    void start() {
        pause = false
        new Thread (new Runnable() {
            @Override
            public void run() {
                if (BusStopConsts.paintLog) println 'start'
                while(!pause) {
                    repaint()
                    try {
                        Thread.sleep(PAUSE_MILIS)
                    } catch (InterruptedException e){}
                }
                if (BusStopConsts.paintLog) println 'end start'
            }
        }).start()
    }

    /** Пауза Рисования */
    void pause() {
        pause = true
    }

    /** Остановка Рисования */
    void stop() {
        pause()
        init()
        repaint()
    }

}
