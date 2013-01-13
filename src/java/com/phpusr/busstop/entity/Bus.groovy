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
    BufferedImage image

    Bus(String path) {
        URL url = Bus.class.getResource(path)
        image = ImageIO.read(url)
    }

    Bus() {
        this('../img/bus/pacan_bus.gif')
    }

    int getWidth() {
        image.width
    }

    int getHeight() {
        image.height
    }
}
