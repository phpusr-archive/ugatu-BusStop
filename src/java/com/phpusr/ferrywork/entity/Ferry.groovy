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
    /** Кол-во мест для Пассажиров */
    int seatCount
    /** Кол-во мест для Автомобилей */
    int parkingCount

    /** Кол-во Пассажиров (внутри) */
    int passengerCount
    /** Кол-во Автомобилей (внутри) */
    int carCount
    /** Кол-во зашедших и вышедших Пассажиров (для Статистики) */
    int passengerCountIn, passengerCountOut
    /** Кол-во зашедших и вышедших Автомобилей (для Статистики) */
    int carCountIn, carCountOut

    Ferry(int number, String name, int seatCount, int parkingCount, String pathToImage) {
        super(name, pathToImage)
        this.number = number
        this.seatCount = seatCount
        this.parkingCount = parkingCount
    }

    /** Добавить Пассажира */
    boolean addPassenger() {
        if (passengerCount < seatCount) {
            passengerCount++
            passengerCountIn++

            if (FerryWorkConsts.ferryLog) println "\t$this"
            return true
        } else {
            if (FerryWorkConsts.ferryLog) println ">>В пароме: $name больше нет мест для пассажиров!"
            return false
        }
    }

    /** Добавить Автомобиль */
    boolean addCar() {
        if (carCount < parkingCount) {
            carCount++
            carCountIn++

            if (FerryWorkConsts.ferryLog) println "\t$this"
            return true
        } else {
            if (FerryWorkConsts.ferryLog) println ">>В пароме: $name больше нет мест для парковки!"
            return false
        }
    }

    /** Удалить Пассажира из Парома */
    boolean delPassenger() {
        if (passengerCount > 0) {
            passengerCount--
            passengerCountOut++

            if (FerryWorkConsts.ferryLog) println "\t$this"
            return true
        } else {
            if (FerryWorkConsts.ferryLog) println ">>В пароме: $name больше нет пассажиров!"
            return false
        }
    }

    /** Удалить Автомобиль из Парома */
    boolean delCar() {
        if (carCount > 0) {
            carCount--
            carCountOut++

            if (FerryWorkConsts.ferryLog) println "\t$this"
            return true
        } else {
            if (FerryWorkConsts.ferryLog) println ">>В пароме: $name больше нет автомобилей!"
            return false
        }
    }

    /** Возвращает кол-во Свободных мест для Пассажиров */
    int getFreeSeat() {
        return seatCount - passengerCount
    }

    /** Возвращает кол-во Свободных мест для Автомобилей */
    int getFreeParking() {
        return parkingCount - carCount
    }

    @Override
    String toString() {
        return "Ferry: $name, Passenger: $passengerCount/$seatCount, All in: $passengerCountIn, All out: $passengerCountOut; Cars: $carCount/$parkingCount, All in: $carCountIn, All out: $carCountOut"
    }
}
