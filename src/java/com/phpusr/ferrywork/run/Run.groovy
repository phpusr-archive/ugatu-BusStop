package com.phpusr.ferrywork.run

import com.phpusr.ferrywork.frame.ParamsFrame

import javax.swing.*

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 13.01.13
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */

/**
 * Запускающий программу класс
 */
class Run {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel('com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel');
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        new ParamsFrame('Ferry Work Simulation Params')
    }
}


