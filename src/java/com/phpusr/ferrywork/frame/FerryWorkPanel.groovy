package com.phpusr.ferrywork.frame

import com.phpusr.ferrywork.consts.FerryWorkConsts
import com.phpusr.ferrywork.entity.Ferry
import com.phpusr.ferrywork.util.DrawUtil
import com.phpusr.ferrywork.util.FerryUtil

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
 * Панель для отображения симуляции Работы Парома
 */
class FerryWorkPanel extends JPanel {

    /** Координаты рисования Пассажиров */
    int xPosFerry, xPosPassenger
    /** Кол-во Пассажиров для Выхода и Входа */
    int passengerCountOut, passengerCountOutConst, passengerCountIn, passengerCountInConst
    /** Кол-во остановок */
    int stopCount
    /** Пауза между кадрами (мс) */
    int pauseMilis

    /** Буфер для рисования кадра */
    Image scrnBuf
    /** Холст для рисования от Буфера */
    Graphics scrnG
    /** Утилита для работы с Паромом */
    FerryUtil ferryUtil
    /** Утилита для рисования объектов */
    DrawUtil drawUtil
    /** Паром */
    Ferry ferry
    /** Форма управления */
    ControlFrame controlFrame

    /** Стоит-ли Паром на причалах */
    boolean stop
    /** Выходят или Заходят в Паром Пассажиры */
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
        ferryUtil = new FerryUtil()
        drawUtil = new DrawUtil()
        ferry = ferryUtil.randomFerry
        xPosFerry = FerryWorkConsts.BERTH_WIDTH
        pauseMilis = FerryWorkConsts.PAUSE_MILIS

        pause = true
        stop = false
        out = true
        revers = false
    }

    /** Функция Рисования */
    void paint(Graphics g) {
        //Рисование фона
        drawUtil.drawBackground(scrnG, FerryWorkConsts.WIDTH, FerryWorkConsts.HEIGHT, this)
        //Рисование Парома
        drawUtil.drawFerry(scrnG, ferry, xPosFerry, FerryWorkConsts.Y_POS, this)

        //Если Паром не остановлен, то движение Парома
        if (!pause && !stop) {
            if (revers) {
                xPosFerry -= FerryWorkConsts.PIXEL_INC
            } else {
                xPosFerry += FerryWorkConsts.PIXEL_INC
            }
        }
        //Если Паром на Причалах
        if (!pause && (xPosFerry <= FerryWorkConsts.BERTH_WIDTH || xPosFerry >= FerryWorkConsts.WIDTH-FerryWorkConsts.BERTH_WIDTH-ferry.width)) {
            if (!stop) { //Если зашли в это условие первый раз, то генерируем кол-во входящих пассажиров
                revers = !revers
                xPosPassenger = getPassengerStartOutPos()
                stopCount++
                passengerCountOut = Math.round(Math.random() * ferry.passengerCount)
                passengerCountOutConst = passengerCountOut
                passengerCountIn = Math.round(Math.random() * ferry.freeSeat)
                passengerCountInConst = passengerCountIn
                out = true

                if (FerryWorkConsts.ferryLog) println '-----------------------------------------------------------------------------------'
                if (FerryWorkConsts.paintLog) println "$stopCount\t Кол-во выходящих пассажиров: ${passengerCountOut}/$ferry.passengerCount,\t\t Кол-во входящих пассажиров: ${passengerCountIn}/$ferry.freeSeat"
                if (FerryWorkConsts.ferryLog) println '-----------------------------------------------------------------------------------'
                if (FerryWorkConsts.statFerryLog && stopCount % 10 == 0) ferryUtil.printStat()
                updateFerryInfo()
            }

            if (out) { //Анимация выхода пассажиров
                stop = true
                if (passengerCountOut > 0) {
                    drawUtil.drawPassenger(scrnG, xPosPassenger, (int)FerryWorkConsts.HEIGHT/2)
                    //Если True - значит пассажиры выходят Справа
                    xPosPassenger = revers ? xPosPassenger + FerryWorkConsts.PIXEL_INC : xPosPassenger - FerryWorkConsts.PIXEL_INC
                    if (revers && xPosPassenger > FerryWorkConsts.WIDTH || !revers && xPosPassenger < 0) { //Пассажир вышел из Парома
                        passengerCountOut--
                        ferry.delPassenger()
                        xPosPassenger = getPassengerStartOutPos()
                        updateFerryInfo()
                    }
                } else {
                    out = false
                    xPosPassenger = revers ? FerryWorkConsts.WIDTH : 0
                }
            } else { //Анимация входа пассажиров
                if (passengerCountIn > 0) {
                    drawUtil.drawPassenger(scrnG, xPosPassenger, (int)FerryWorkConsts.HEIGHT/2)
                    xPosPassenger = revers ? xPosPassenger - FerryWorkConsts.PIXEL_INC : xPosPassenger + FerryWorkConsts.PIXEL_INC
                    if (revers && xPosPassenger < FerryWorkConsts.WIDTH-ferry.width/2-FerryWorkConsts.DELTA || !revers && xPosPassenger > ferry.width/2+FerryWorkConsts.DELTA) { //Пассажир сел на Паром
                        passengerCountIn--
                        ferry.addPassenger()
                        xPosPassenger = revers ? FerryWorkConsts.WIDTH : 0
                        updateFerryInfo()
                    }
                } else {
                    stop = false
                }
            }
        }

        //Рисование на форме изображения из буфера
        g.drawImage(scrnBuf, 0, 0, this)
    }

    /**
     * Возвращает начальную позицию для выхода Пассажиров
     */
    int getPassengerStartOutPos() {
        return revers ? FerryWorkConsts.WIDTH-ferry.width/2-FerryWorkConsts.DELTA : ferry.width/2+FerryWorkConsts.DELTA
    }

    /** Срабатывает при Изменении свойств в Парома */
    private updateFerryInfo() {
        controlFrame.setPassengerCountOut(passengerCountOutConst - passengerCountOut, passengerCountOutConst, ferry.passengerCountOut, ferryUtil.allPassengerOutCount)
        controlFrame.setPassengerCountIn(passengerCountInConst - passengerCountIn, passengerCountInConst, ferry.passengerCountIn, ferryUtil.allPassengerInCount)
        ferryUtil.updateTblStat(controlFrame.tblStat, controlFrame.model, ferry)
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
                        Thread.sleep(pauseMilis)
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

    /** Увеличение скорости отрисовки */
    void speedUp() {
        pauseMilis /= 2
    }

}
