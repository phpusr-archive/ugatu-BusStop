package com.phpusr.busstop.util

import com.phpusr.ferrywork.consts.FerryWorkConsts
import com.phpusr.ferrywork.entity.Ferry

import javax.imageio.ImageIO
import java.awt.*
import java.awt.image.ImageObserver

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 13.01.13
 * Time: 18:41
 * To change this template use File | Settings | File Templates.
 */

/**
 * Утилита для рисования
 */
class DrawUtil {

    /** Рисует задний фон */
    void drawBackground(Graphics g, int width, int height, ImageObserver ioServer) {
        //Заливка
        g.setColor(Color.LIGHT_GRAY)
        g.fillRect(0, 0, width, height)
        g.setColor(Color.BLACK)

        //Картинка
        URL url = DrawUtil.class.getResource("$FerryWorkConsts.BG_IMG_PATH/BG.png")
        Image image = ImageIO.read(url)
        g.drawImage(image, 0, 0, ioServer)
    }

    /** Рисует Паром */
    void drawFerry(Graphics g, Ferry ferry, int x, int y, ImageObserver ioServer) {
        g.drawImage(ferry.image, x, y, ioServer)
    }

    /** Рисует Пассажира */
    void drawPassenger(Graphics g, int x, int y) {
        g.fillOval(x, y, 10, 10)
    }

}
