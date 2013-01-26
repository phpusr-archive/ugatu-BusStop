package com.phpusr.ferrywork.entity

import com.phpusr.ferrywork.consts.FerryWorkConsts

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 12.01.13
 * Time: 19:21
 * To change this template use File | Settings | File Templates.
 */

/**
 * Паром
 */
class Ferry extends Entity {
    /** Порядковый № */
    int number
    /** Кол-во мест */
    int seatCount

    /** Кол-во Пассажиров (внутри) */
    int passengerCount
    /** Кол-во зашедших и вышедших Пассажиров (для Статистики) */
    int passengerCountIn, passengerCountOut

    Ferry(int number, String name, int seatCount, String pathToImage) {
        super(name, pathToImage)
        this.number = number
        this.seatCount = seatCount
    }

    /** Добавить пассажира */
    boolean addPassenger() {
        if (passengerCount < seatCount) {
            passengerCount++
            passengerCountIn++

            if (FerryWorkConsts.ferryLog) println "\t$this"
            return true
        } else {
            if (FerryWorkConsts.ferryLog) println ">>В пароме: $name-$route больше нет места!"
            return false
        }
    }

    /** Удалить пассажира из Парома */
    boolean delPassenger() {
        if (passengerCount > 0) {
            passengerCount--
            passengerCountOut++

            if (FerryWorkConsts.ferryLog) println "\t$this"
            return true
        } else {
            if (FerryWorkConsts.ferryLog) println ">>В пароме: $name-$route больше нет пассажиров!"
            return false
        }
    }

    /** Возвращает кол-во Свободных мест в Пароме */
    int getFreeSeat() {
        return seatCount - passengerCount
    }

    @Override
    String toString() {
        return "Паром: $name, Пассажиров: $passengerCount/$seatCount, Всего зашло: $passengerCountIn, Всего вышло: $passengerCountOut"
    }
}
