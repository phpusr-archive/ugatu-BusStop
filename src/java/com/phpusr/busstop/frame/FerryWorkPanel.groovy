package com.phpusr.busstop.frame

import com.phpusr.busstop.consts.BusStopConsts
import com.phpusr.busstop.entity.Ferry
import com.phpusr.busstop.util.DrawUtil
import com.phpusr.busstop.util.FerryUtil

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
 * Панель для отображения симуляции Автобусной остановки
 */
class FerryWorkPanel extends JPanel {

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
    FerryUtil busParkUtil
    /** Утилита для рисования объектов */
    DrawUtil drawUtil
    /** Текущий Автобус */
    Ferry bus
    /** Форма управления */
    ControlFrame controlFrame

    /** Стоит-ли Автобус на остановке */
    boolean stop
    /** Выходят или Заходят в автобус Пассажиры */
    boolean out
    /** Пауза */
    boolean pause

    FerryWorkPanel() {
        init()
        setSize(BusStopConsts.WIDTH, BusStopConsts.HEIGHT)
        setVisible(true)
    }

    /** Инициализация переменных для Рисования */
    private void init() {
        scrnBuf = new BufferedImage(BusStopConsts.WIDTH, BusStopConsts.HEIGHT, BufferedImage.TYPE_INT_RGB)
        scrnG = scrnBuf.getGraphics()
        busParkUtil = new FerryUtil()
        drawUtil = new DrawUtil()
        bus = busParkUtil.randomBus
        xPosBus = -1 * bus.width

        stop = false
        out = true
    }

    /** Функция Рисования */
    void paint(Graphics g) {
        //Рисование фона
        drawUtil.drawBackground(scrnG, BusStopConsts.WIDTH, BusStopConsts.HEIGHT, this)
        //Рисование автобуса
        drawUtil.drawBus(scrnG, bus, xPosBus, BusStopConsts.Y_POS, this)

        //Если автобус подъехал к остановке
        int stopX = BusStopConsts.WIDTH / 2 - bus.width / 2
        if (xPosBus >= stopX && xPosBus <= stopX+BusStopConsts.PIXEL_INC) {
            if (!stop) { //Если зашли в это условие первый раз, то генерируем кол-во входящих пассажиров
                stopCount++
                passengerCountOut = Math.round(Math.random() * bus.passengerCount)
                passengerCountOutConst = passengerCountOut
                passengerCountIn = Math.round(Math.random() * bus.freeSeat)
                passengerCountInConst = passengerCountIn
                out = true
                yPosPassenger = BusStopConsts.Y_POS + bus.height/2

                if (BusStopConsts.busLog) println '-----------------------------------------------------------------------------------'
                if (BusStopConsts.paintLog) println "$stopCount\t Кол-во выходящих пассажиров: ${passengerCountOut}/$bus.passengerCount,\t\t Кол-во входящих пассажиров: ${passengerCountIn}/$bus.freeSeat"
                if (BusStopConsts.busLog) println '-----------------------------------------------------------------------------------'
                if (BusStopConsts.statBusLog && stopCount % 10 == 0) busParkUtil.printStat()
                updateBusInfo()
            }

            if (out) { //Анимация выхода пассажиров
                stop = true
                if (passengerCountOut > 0) {
                    drawUtil.drawPassenger(scrnG, (int)BusStopConsts.WIDTH/2, yPosPassenger)
                    yPosPassenger += BusStopConsts.PIXEL_INC
                    if (yPosPassenger > BusStopConsts.HEIGHT) { //Пассажир вышел из Автобуса
                        passengerCountOut--
                        bus.delPassenger()
                        yPosPassenger = BusStopConsts.Y_POS + bus.height/2
                        updateBusInfo()
                    }
                } else {
                    out = false
                    yPosPassenger = BusStopConsts.HEIGHT
                }
            } else { //Анимация входа пассажиров
                if (passengerCountIn > 0) {
                    drawUtil.drawPassenger(scrnG, (int)BusStopConsts.WIDTH/2, yPosPassenger)
                    yPosPassenger -= BusStopConsts.PIXEL_INC
                    if (yPosPassenger < BusStopConsts.Y_POS + bus.height/2) { //Пассажир сел на Автобус
                        passengerCountIn--
                        bus.addPassenger()
                        yPosPassenger = BusStopConsts.HEIGHT
                        updateBusInfo()
                    }
                } else {
                    stop = false
                }
            }
        }
        //Если автобус не остановлен, то движение автобуса
        if (!stop) xPosBus += BusStopConsts.PIXEL_INC
        //Если автобус уехал за пределы
        if (xPosBus > BusStopConsts.WIDTH) {
            bus = busParkUtil.randomBus
            xPosBus = -1 * bus.width
        }

        //Рисование на форме изображения из буфера
        g.drawImage(scrnBuf, 0, 0, this)
    }

    /** Срабатывает при Изменении свойств в Автобусе */
    private updateBusInfo() {
        controlFrame.setPassengerCountOut(passengerCountOutConst - passengerCountOut, passengerCountOutConst, bus.passengerCountOut, busParkUtil.allPassengerOutCount)
        controlFrame.setPassengerCountIn(passengerCountInConst - passengerCountIn, passengerCountInConst, bus.passengerCountIn, busParkUtil.allPassengerInCount)
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
                        Thread.sleep(BusStopConsts.PAUSE_MILIS)
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
