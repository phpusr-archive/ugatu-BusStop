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

    Bus() {
        URL url = Bus.class.getResource('../img/bus/pacan_bus.gif')
        image = ImageIO.read(url)
    }

    int getWidth() {
        image.width
    }

    int getHeight() {
        image.height
    }
}
