package com.phpusr.busstop.frame

import com.phpusr.busstop.consts.FerryWorkConsts
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
    int xPosBus, xPosPassenger
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
    /** Паром */
    Ferry ferry
    /** Форма управления */
    ControlFrame controlFrame

    /** Стоит-ли Автобус на остановке */
    boolean stop
    /** Выходят или Заходят в автобус Пассажиры */
    boolean out
    /** Пауза */
    boolean pause
    /** Движение назад */
    boolean revers

    FerryWorkPanel() {
        init()
        setSize(FerryWorkConsts.WIDTH, FerryWorkConsts.HEIGHT)
        setVisible(true)
    }

    /** Инициализация переменных для Рисования */
    private void init() {
        scrnBuf = new BufferedImage(FerryWorkConsts.WIDTH, FerryWorkConsts.HEIGHT, BufferedImage.TYPE_INT_RGB)
        scrnG = scrnBuf.getGraphics()
        busParkUtil = new FerryUtil()
        drawUtil = new DrawUtil()
        ferry = busParkUtil.randomBus
        xPosBus = 0

        stop = false
        out = true
        revers = false
    }

    /** Функция Рисования */
    void paint(Graphics g) {
        //Рисование фона
        drawUtil.drawBackground(scrnG, FerryWorkConsts.WIDTH, FerryWorkConsts.HEIGHT, this)
        //Рисование автобуса
        drawUtil.drawBus(scrnG, ferry, xPosBus, FerryWorkConsts.Y_POS, this)

        //Если автобус не остановлен, то движение автобуса
        if (!stop) {
            if (revers) {
                xPosBus -= FerryWorkConsts.PIXEL_INC
            } else {
                xPosBus += FerryWorkConsts.PIXEL_INC
            }
        }
        //Если автобус уехал за пределы
        if (xPosBus <= 0 || xPosBus >= FerryWorkConsts.WIDTH-ferry.width) {
            if (!stop) { //Если зашли в это условие первый раз, то генерируем кол-во входящих пассажиров
                revers = !revers
                xPosPassenger = revers ? FerryWorkConsts.WIDTH-ferry.width/2 : ferry.width/2
                stopCount++
                passengerCountOut = Math.round(Math.random() * ferry.passengerCount)
                passengerCountOutConst = passengerCountOut
                passengerCountIn = Math.round(Math.random() * ferry.freeSeat)
                passengerCountInConst = passengerCountIn
                out = true

                if (FerryWorkConsts.busLog) println '-----------------------------------------------------------------------------------'
                if (FerryWorkConsts.paintLog) println "$stopCount\t Кол-во выходящих пассажиров: ${passengerCountOut}/$ferry.passengerCount,\t\t Кол-во входящих пассажиров: ${passengerCountIn}/$ferry.freeSeat"
                if (FerryWorkConsts.busLog) println '-----------------------------------------------------------------------------------'
                if (FerryWorkConsts.statBusLog && stopCount % 10 == 0) busParkUtil.printStat()
                updateBusInfo()
            }

            if (out) { //Анимация выхода пассажиров
                stop = true
                if (passengerCountOut > 0) {
                    drawUtil.drawPassenger(scrnG, xPosPassenger, (int)FerryWorkConsts.HEIGHT/3)
                    //Если True - значит пассажиры выходят Справа
                    xPosPassenger = revers ? xPosPassenger + FerryWorkConsts.PIXEL_INC : xPosPassenger - FerryWorkConsts.PIXEL_INC
                    if (revers && xPosPassenger > FerryWorkConsts.WIDTH || !revers && xPosPassenger < 0) { //Пассажир вышел из Парома
                        passengerCountOut--
                        ferry.delPassenger()
                        xPosPassenger = revers ? FerryWorkConsts.WIDTH-ferry.width/2 : ferry.width/2
                        updateBusInfo()
                    }
                } else {
                    out = false
                    xPosPassenger = revers ? FerryWorkConsts.WIDTH : 0
                }
            } else { //Анимация входа пассажиров
                if (passengerCountIn > 0) {
                    drawUtil.drawPassenger(scrnG, xPosPassenger, (int)FerryWorkConsts.HEIGHT/3)
                    xPosPassenger = revers ? xPosPassenger - FerryWorkConsts.PIXEL_INC : xPosPassenger + FerryWorkConsts.PIXEL_INC
                    if (revers && xPosPassenger < FerryWorkConsts.WIDTH-ferry.width/2 || !revers && xPosPassenger > ferry.width/2) { //Пассажир сел на Паром
                        passengerCountIn--
                        ferry.addPassenger()
                        xPosPassenger = revers ? FerryWorkConsts.WIDTH : 0
                        updateBusInfo()
                    }
                } else {
                    stop = false
                }
            }
        }

        //Рисование на форме изображения из буфера
        g.drawImage(scrnBuf, 0, 0, this)
    }

    /** Срабатывает при Изменении свойств в Автобусе */
    private updateBusInfo() {
        controlFrame.setPassengerCountOut(passengerCountOutConst - passengerCountOut, passengerCountOutConst, ferry.passengerCountOut, busParkUtil.allPassengerOutCount)
        controlFrame.setPassengerCountIn(passengerCountInConst - passengerCountIn, passengerCountInConst, ferry.passengerCountIn, busParkUtil.allPassengerInCount)
        busParkUtil.updateTblStat(controlFrame.tblStat, controlFrame.model, ferry)
    }

    void update(Graphics g) { paint(g) }

    /** Запуск Рисования в отдельном потоке */
    void start() {
        pause = false
        new Thread (new Runnable() {
            @Override
            public void run() {
                if (FerryWorkConsts.paintLog) println 'start'
                while(!pause) {
                    repaint()
                    try {
                        Thread.sleep(FerryWorkConsts.PAUSE_MILIS)
                    } catch (InterruptedException e){}
                }
                if (FerryWorkConsts.paintLog) println 'end start'
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
