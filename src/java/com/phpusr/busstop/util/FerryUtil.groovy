package com.phpusr.busstop.util

import com.phpusr.busstop.consts.FerryWorkConsts
import com.phpusr.busstop.entity.Ferry
import com.phpusr.busstop.frame.FerryTableModel

import javax.swing.*

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 13.01.13
 * Time: 18:01
 * To change this template use File | Settings | File Templates.
 */

/**
 * Утилита для работы с Паромом
 */
class FerryUtil {
    /** Путь к папке с ихображениями Паромов */
    static final String FERRY_IMG_PATH = FerryWorkConsts.FERRY_IMG_PATH
    /** Список Паромов */
    List<Ferry> ferryList = []

    FerryUtil() {
        int i = 0
        ferryList << new Ferry(i++, 'Pacan', 20, "$FERRY_IMG_PATH/Pacan.png")
    }

    /** Возвращает рандомный Паром из списка */
    Ferry getRandomFerry() {
        int num = Math.random() * ferryList.size()
        return ferryList.get(num)
    }

    /** Возвращает кол-во всех севших в Паром Пассажиров */
    int getAllPassengerInCount() {
        int count = 0
        ferryList.each { ferry ->
            count += ferry.passengerCountIn
        }

        return count
    }

    /** Возвращает кол-во всех вышедших из Парома Пассажиров */
    int getAllPassengerOutCount() {
        int count = 0
        ferryList.each { ferry ->
            count += ferry.passengerCountOut
        }

        return count
    }

    /** Возвращает Паром, который Больше всех довез Пассажиров */
    Ferry getMaxFerry() {
        Ferry mFerry = ferryList.get(0)
        ferryList.each { ferry ->
            if (ferry.passengerCountOut > mFerry.passengerCountOut) {
                mFerry = ferry
            }
        }

        return mFerry
    }

    /** Возвращает Паром, который Меньше всех довез Пассажиров */
    Ferry getMinFerry() {
        Ferry mFerry = ferryList.get(0)
        ferryList.each { ferry ->
            if (ferry.passengerCountOut < mFerry.passengerCountOut) {
                mFerry = ferry
            }
        }

        return mFerry
    }

    /** Вывод статистики по Пассажирам в Паромах */
    void printStat() {
        println '-------------------------------------------------------------------------------------------------------------------------'
        println "Кол-во всех севших в паромы пассажиров:        $allPassengerInCount"
        println "Кол-во всех вышедших из парома пассажиров:     $allPassengerOutCount"
        println "Паром довезший БОЛЬШЕ всех пассажиров:         $maxFerry"
        println "Паром довезший МЕНЬШЕ всех пассажиров:         $minFerry"
        println '-------------------------------------------------------------------------------------------------------------------------'
    }

    /** Загрузка начальных значений в таблицу */
    void initTblStat(FerryTableModel model) {
        while (model.rowCount > 0) {
            model.removeRow(0)
        }
        ferryList.each { ferry ->
            def list = [ferry.name, ferry.passengerCount, ferry.freeSeat, ferry.passengerCountOut, ferry.passengerCountIn]
            model.addRow(list.toArray())
        }
    }

    /** Обновлении информации в Таблице Статистики */
    void updateTblStat(JTable table, FerryTableModel model, Ferry ferry) {
        int i = 0
        table.setValueAt(ferry.name, ferry.number, i++)
        table.setValueAt(ferry.passengerCount, ferry.number, i++)
        table.setValueAt(ferry.freeSeat, ferry.number, i++)
        table.setValueAt(ferry.passengerCountOut, ferry.number, i++)
        table.setValueAt(ferry.passengerCountIn, ferry.number, i++)

        model.maxRow = maxFerry.number
        model.minRow = minFerry.number
    }

}
