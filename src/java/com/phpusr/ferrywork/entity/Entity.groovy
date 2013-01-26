package com.phpusr.ferrywork.entity

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 26.01.13
 * Time: 14:36
 * To change this template use File | Settings | File Templates.
 */

/**
 * Сущность для создания классов: Пассажир, Авто, Паром
 */
abstract class Entity {
    /** Название */
    String name
    /** Изображение */
    BufferedImage image

    Entity(String name, String pathToImage) {
        this.name = name
        setImage(pathToImage)
    }

    /** Загрузка изображения по пути к файлу */
    void setImage(String pathToImage) {
        URL url = Entity.class.getResource(pathToImage)
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

    @Override
    String toString() {
        return "${getClass().name}: $name"
    }
}
