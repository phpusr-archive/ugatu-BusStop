package com.phpusr.busstop.util

import com.phpusr.busstop.entity.Bus

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 13.01.13
 * Time: 18:01
 * To change this template use File | Settings | File Templates.
 */

/**
 * Утилита для работы с Автобусами
 */
class BusParkUtil {
    /** Относительный путь к папке с ихображениями Автобусов */
    static final String imgPath = '../img/bus'
    /** Список автобусов */
    List<Bus> busList = []

    BusParkUtil() {
        busList << new Bus('Pacan',     '57',   20, "$imgPath/Pacan.png")
        busList << new Bus('Boxer',     '218',  10, "$imgPath/Boxer.png")
        busList << new Bus('Dirty',     '54',   20, "$imgPath/Dirty.png")
        busList << new Bus('Feniks',    '214',  10, "$imgPath/Feniks.png")
        busList << new Bus('Tn',        '54',   20, "$imgPath/Tn.png")
    }

    /** Возвращает рандомный автобус из списка */
    Bus getRandomBus() {
        int num = Math.random() * busList.size()
        return busList.get(num)
    }

    /** Возвращает кол-во всех севших в Автобусы Пассажиров */
    int getAllPassengerInCount() {
        int count = 0
        busList.each { bus ->
            count += bus.passengerCountIn
        }

        return count
    }

    /** Возвращает кол-во всех вышедших из Автобусов Пассажиров */
    int getAllPassengerOutCount() {
        int count = 0
        busList.each { bus ->
            count += bus.passengerCountOut
        }

        return count
    }

    /** Возвращает Автобус, который Больше всех довез Пассажиров */
    Bus getMaxBus() {
        Bus mBus = busList.get(0)
        busList.each { bus ->
            if (bus.passengerCountOut > mBus.passengerCountOut) {
                mBus = bus
            }
        }

        return mBus
    }

    /** Возвращает Автобус, который Меньше всех довез Пассажиров */
    Bus getMinBus() {
        Bus mBus = busList.get(0)
        busList.each { bus ->
            if (bus.passengerCountOut < mBus.passengerCountOut) {
                mBus = bus
            }
        }

        return mBus
    }

    /** Вывод статистики по Пассажирам в Автобусах */
    void printStat() {
        println '-------------------------------------------------------------------------------------------------------------------------'
        println "Кол-во всех севших в автобусы пассажиров:       $allPassengerInCount"
        println "Кол-во всех вышедших из автобусов пассажиров:   $allPassengerOutCount"
        println "Автобус довезший БОЛЬШЕ всех пассажиров:        $maxBus"
        println "Автобус довезший МЕНЬШЕ всех пассажиров:        $minBus"
        println '-------------------------------------------------------------------------------------------------------------------------'
    }
}
