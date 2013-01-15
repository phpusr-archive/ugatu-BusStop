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
    static final String imgPath = '../img/bus'
    List<Bus> busList = []

    BusParkUtil() {
        busList << new Bus("$imgPath/Pacan.png")
        busList << new Bus("$imgPath/Boxer.png")
        busList << new Bus("$imgPath/Dirty.png")
        busList << new Bus("$imgPath/Feniks.png")
        busList << new Bus("$imgPath/Tn.png")
    }

    Bus getRandomBus() {
        int num = Math.random() * busList.size()
        return busList.get(num)
    }

}
