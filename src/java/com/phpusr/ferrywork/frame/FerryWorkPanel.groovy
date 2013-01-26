package com.phpusr.ferrywork.frame

import com.phpusr.ferrywork.consts.FerryWorkConsts
import com.phpusr.ferrywork.entity.Ferry
import com.phpusr.ferrywork.util.DrawUtil
import com.phpusr.ferrywork.util.FerryUtil
import com.phpusr.ferrywork.util.PassengerUtil

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
    Integer passengerCountOut, passengerCountOutConst, passengerCountIn, passengerCountInConst
    /** Кол-во Автомобилей для Выхода и Входа */
    Integer carCountOut, carCountOutConst, carCountIn, carCountInConst
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
    /** Утилита для работы с Пассажирами */
    PassengerUtil passengerUtil
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
        passengerUtil = new PassengerUtil()
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
                //Пасажиры
                passengerCountOut = Math.round(Math.random() * ferry.passengerCount)
                passengerCountOutConst = passengerCountOut
                passengerCountIn = Math.round(Math.random() * ferry.freeSeat)
                passengerCountInConst = passengerCountIn
                //Автомобили
                carCountOut = Math.round(Math.random() * ferry.carCount)
                carCountOutConst = carCountOut
                carCountIn = Math.round(Math.random() * ferry.freeParking)
                carCountInConst = carCountIn
                out = true

                if (FerryWorkConsts.ferryLog) println '-----------------------------------------------------------------------------------'
                if (FerryWorkConsts.paintLog) println "$stopCount\t Count out passengers: ${passengerCountOut}/$ferry.passengerCount,\t\t Count in passengers: ${passengerCountIn}/$ferry.freeSeat"
                if (FerryWorkConsts.paintLog) println "$stopCount\t Count out cars: ${carCountOut}/$ferry.carCount,\t\t Count in cars: ${carCountIn}/$ferry.freeParking"
                if (FerryWorkConsts.ferryLog) println '-----------------------------------------------------------------------------------'
                if (FerryWorkConsts.statFerryLog && stopCount % 10 == 0) ferryUtil.printStat()
                updateFerryInfo()
            }

            def map
            if (passengerCountOut > 0 || (carCountOut == 0 && passengerCountIn > 0)) {
                map = animOutInPassenger([countOut: passengerCountOut, countIn: passengerCountIn], true)
                passengerCountOut = map.countOut
                passengerCountIn = map.countIn
            } else {
                map = animOutInPassenger([countOut: carCountOut, countIn: carCountIn], false)
                carCountOut = map.countOut
                carCountIn = map.countIn
            }
        }

        //Рисование на форме изображения из буфера
        g.drawImage(scrnBuf, 0, 0, this)
    }

    /** Анимация Выхода и Входа Пассажиров */
    Map animOutInPassenger(Map map, boolean passenger) {
        if (out) { //Анимация выхода пассажиров
            stop = true
            if (map.countOut > 0) {
                drawUtil.drawPassenger(scrnG, xPosPassenger, (int)FerryWorkConsts.HEIGHT/2)
                //Если True - значит пассажиры выходят Справа
                xPosPassenger = revers ? xPosPassenger + FerryWorkConsts.PIXEL_INC : xPosPassenger - FerryWorkConsts.PIXEL_INC
                if (revers && xPosPassenger > FerryWorkConsts.WIDTH || !revers && xPosPassenger < 0) { //Пассажир вышел из Парома
                    map.countOut--
                    if (passenger) {
                        ferry.delPassenger()
                        if (FerryWorkConsts.paintLog) println '>>Del passenger'
                    } else {
                        ferry.delCar()
                        if (FerryWorkConsts.paintLog) println '>>Del car'
                    }
                    xPosPassenger = getPassengerStartOutPos()
                    updateFerryInfo()
                }
            } else {
                if (carCountOut == 0) {
                    out = false
                    xPosPassenger = revers ? FerryWorkConsts.WIDTH : 0
                }
            }
        } else { //Анимация входа пассажиров
            if (map.countIn > 0) {
                drawUtil.drawPassenger(scrnG, xPosPassenger, (int)FerryWorkConsts.HEIGHT/2)
                xPosPassenger = revers ? xPosPassenger - FerryWorkConsts.PIXEL_INC : xPosPassenger + FerryWorkConsts.PIXEL_INC
                if (revers && xPosPassenger < FerryWorkConsts.WIDTH-ferry.width/2-FerryWorkConsts.DELTA || !revers && xPosPassenger > ferry.width/2+FerryWorkConsts.DELTA) { //Пассажир сел на Паром
                    map.countIn--
                    if (passenger) {
                        ferry.addPassenger()
                        if (FerryWorkConsts.paintLog) println '>>Add passenger'
                    } else {
                        ferry.addCar()
                        if (FerryWorkConsts.paintLog) println '>>Add car'
                    }
                    xPosPassenger = revers ? FerryWorkConsts.WIDTH : 0
                    updateFerryInfo()
                }
            } else {
                if (carCountIn == 0) stop = false
            }
        }

        return map
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
