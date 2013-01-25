package com.phpusr.busstop.frame

import javax.swing.table.DefaultTableModel
import java.awt.*

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 16.01.13
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */

/**
 * Кастомная модель для Выделения Максимального по перевозке и Минимального Паромов
 */
class FerryTableModel extends DefaultTableModel {
    /** Строка с Максимальным Паромом */
    int maxRow
    /** Строка с Минимальным Паромом */
    int minRow

    public Color getRowColor(int row) {
        if (row == maxRow) return Color.GREEN
        if (row == minRow) return Color.RED
        return row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE
    }

    /** Установка строки с Максимальным Паромом */
    void setMaxRow(int maxRow) {
        this.maxRow = maxRow
        if (rowCount > 0) {
            fireTableRowsUpdated(0, rowCount-1)
        }
    }

    /** Установка строки с Минимальным Паромом */
    void setMinRow(int minRow) {
        this.minRow = minRow
        if (rowCount > 0) {
            fireTableRowsUpdated(0, rowCount-1)
        }
    }
}
