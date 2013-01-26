package com.phpusr.ferrywork.util

import com.phpusr.ferrywork.consts.FerryWorkConsts
import com.phpusr.ferrywork.entity.Car
import com.phpusr.ferrywork.entity.Passenger

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 26.01.13
 * Time: 15:08
 * To change this template use File | Settings | File Templates.
 */

/**
 * Утилита для работы с Пассажирами и Автомобилями
 */
class PassengerUtil {
    /** Путь к папке с ихображениями Пассажиров */
    static final String PAS_IMG_PATH = FerryWorkConsts.PAS_IMG_PATH
    /** Путь к папке с ихображениями Автомобилей */
    static final String CAR_IMG_PATH = FerryWorkConsts.CAR_IMG_PATH
    /** Список Пассажиров */
    List<Passenger> passengerList
    /** Список Автомобилей */
    List<Car> carList

    PassengerUtil() {
        passengerList = []
        passengerList << new Passenger('Alex', "$PAS_IMG_PATH/PassengerForward.png", "$PAS_IMG_PATH/PassengerBack.png")
        carList = []
        carList << new Car('Toyota', "$CAR_IMG_PATH/CarForward.png", "$CAR_IMG_PATH/CarBack.png")
    }

    /** Возвращает рандомного Пассажира из списка */
    Passenger getRandomPassenger() {
        int num = Math.random() * passengerList.size()
        return passengerList.get(num)
    }

    /** Возвращает рандомный Автомобиль из списка */
    Car getRandomCar() {
        int num = Math.random() * carList.size()
        return carList.get(num)
    }
}
