package com.phpusr.busstop.util

import java.awt.*

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
    void drawBackground(Graphics g, int width, int height) {
        g.setColor(Color.white)
        g.fillRect(0, 0, width, height)
    }
}
