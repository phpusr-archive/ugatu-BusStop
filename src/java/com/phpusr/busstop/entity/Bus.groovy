package com.phpusr.busstop.entity

import java.awt.*

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
    Image image

    Bus() {
        URL url = Bus.class.getResource('../img/bus/pacan_bus.gif')
        image = Toolkit.getDefaultToolkit().getImage(url);
        //image = Toolkit.getDefaultToolkit().getImage('d:/Java/OOP/kursach/img/bus/pacan_bus.gif')
    }
}
