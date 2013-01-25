package com.phpusr.busstop.entity

import com.phpusr.ferrywork.consts.FerryWorkConsts

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

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
class Ferry {
    /** Порядковый № */
    int number
    /** Название */
    String name
    /** Кол-во мест */
    int seatCount
    /** Изображение */
    BufferedImage image

    /** Кол-во Пассажиров (внутри) */
    int passengerCount
    /** Кол-во зашедших и вышедших Пассажиров (для Статистики) */
    int passengerCountIn, passengerCountOut

    Ferry(int number, String name, int seatCount, String pathToImage) {
        this.number = number
        this.name = name
        this.seatCount = seatCount
        setImage(pathToImage)
    }

    Ferry() {
        this(1, 'Test', 10, "$FerryWorkConsts.FERRY_IMG_PATH/Pacan.png")
    }

    void setImage(String path) {
        URL url = Ferry.class.getResource(path)
        image = ImageIO.read(url)
    }

    /** Возвращает Длину изображения */
    int getWidth() {
        image.width
    }

    /** Возвращает Высоту изображения */
    int getHeight() {
        image.height
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
