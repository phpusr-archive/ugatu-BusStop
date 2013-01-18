package com.phpusr.busstop.consts

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 15.01.13
 * Time: 15:50
 * To change this template use File | Settings | File Templates.
 */

/**
 * Константы
 */
class BusStopConsts {
    /* Логирование */
    /** Вкл/Выкл логирование в Bus */
    static final boolean busLog = 0
    /** Вкл/Выкл логирование в BusStopFrame.paint() */
    static final boolean paintLog = 1
    /** Вкл/Выкл логирование Статистики Автобусов */
    static final boolean statBusLog = 0

    /* Локализация */
    public static final String BTN_SPEED_NAME = 'x'
    public static final String BTN_START_NAME = 'Старт'
    public static final String BTN_PAUSE_NAME = 'Пауза'

    /* Числовые константы */
    public static final int MIN_SPEED = 2
    public static final int MAX_SPEED = 32
}
