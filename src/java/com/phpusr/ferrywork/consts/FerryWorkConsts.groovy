package com.phpusr.ferrywork.consts

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
class FerryWorkConsts {
    /* Логирование */
    /** Вкл/Выкл логирование в Ferry */
    static final boolean ferryLog = 1
    /** Вкл/Выкл логирование в FerryWorkPanel.paint() */
    static final boolean paintLog = 1
    /** Вкл/Выкл логирование Статистики Паромов */
    static final boolean statFerryLog = 0

    /* Локализация */
    public static final String BTN_SPEED_NAME = 'x'
    public static final String BTN_START_NAME = 'Старт'
    public static final String BTN_PAUSE_NAME = 'Пауза'

    /* Числовые константы */
    public static final int MIN_SPEED = 2
    public static final int MAX_SPEED = 32
    public static final int CF_WIDTH = 1200
    public static final int CF_HEIGHT = 700
    public static final int PF_WIDTH = 400
    public static final int PF_HEIGHT = 200

    /** Выделение строк в таблице Статистики */
    public static final boolean TABLE_HIGHLIGHT = false

    /* FerryWorkPanel */
    /** Ширина окна */
    static final int WIDTH = 1200
    /** Высота окна */
    static final int HEIGHT = 500
    /** Y позиция для рисования движения */
    static final int Y_POS = 150
    /** Кол-во увеличения пикселей для показа движения */
    static final int PIXEL_INC = 10
    /** Пауза между кадрами (мс) */
    static final int PAUSE_MILIS = 50
    /** Ширина причала */
    static final int BERTH_WIDTH = 100
    /** Для аниации посадки Пассажиров */
    static final int DELTA = 50

    /* Пути */
    /** Относительный путь к папке с ихображениями */
    static final String IMG_PATH = '/com/phpusr/ferrywork/img'
    /** Путь к папке с ихображениями Паромов */
    static final String FERRY_IMG_PATH = "$IMG_PATH/ferry"
    /** Путь к папке с ихображениями Пассажиров */
    static final String PAS_IMG_PATH = "$IMG_PATH/passenger"
    /** Путь к папке с ихображениями Автомобилей */
    static final String CAR_IMG_PATH = "$IMG_PATH/car"
    /** Путь к папке с ихображениями Фонов */
    static final String BG_IMG_PATH = "$IMG_PATH/bg"

}
