package com.phpusr.busstop.entity

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
 * Автобус
 */
class Bus {
    /** Название */
    String name
    /** Маршрут */
    String route
    /** Кол-во мест */
    int seatCount
    /** Изображение автобуса */
    BufferedImage image

    /** Кол-во Пассажиров (внутри) */
    int passengerCount
    /** Кол-во зашедших и вышедших Пассажиров (для Статистики) */
    int passengerCountIn, passengerCountOut

    Bus(String name, String route, int seatCount, String pathToImage) {
        this.name = name
        this.route = route
        this.seatCount = seatCount
        setImage(pathToImage)
    }

    Bus() {
        this('Test', '777', 10, '../img/bus/Pacan.png')
    }

    void setImage(String path) {
        URL url = Bus.class.getResource(path)
        image = ImageIO.read(url)
    }

    int getWidth() {
        image.width
    }

    int getHeight() {
        image.height
    }

    void addPassenger() {
        passengerCount++
        passengerCountIn++
    }

    void delPassenger() {
        passengerCount--
        passengerCountOut--
    }

    @Override
    String toString() {
        return "Автобус: $name-$route, Пассажиров: $passengerCount/$seatCount, Всего зашло: $passengerCountIn, Всего вышло: $passengerCountOut"
    }
}
