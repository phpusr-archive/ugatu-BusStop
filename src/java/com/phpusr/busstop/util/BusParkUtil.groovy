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
    List<Bus> busList = []

    BusParkUtil() {
        busList << new Bus('../img/bus/pacan_bus.gif')
        busList << new Bus('../img/test/image1.gif')
        busList << new Bus('../img/test/image2.gif')
        busList << new Bus('../img/test/image3.gif')
        busList << new Bus('../img/test/image4.gif')
    }

    Bus getRandomBus() {
        int num = Math.random() * busList.size()
        return busList.get(num)
    }

}
