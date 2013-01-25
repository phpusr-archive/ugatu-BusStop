package com.phpusr.busstop.util

import com.phpusr.busstop.consts.BusStopConsts
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
 * Утилита для работы с Автобусами
 */
class BusParkUtil {
    /** Путь к папке с ихображениями Автобусов */
    static final String BUS_IMG_PATH = BusStopConsts.BUS_IMG_PATH
    /** Список автобусов */
    List<Ferry> busList = []

    BusParkUtil() {
        int i = 0
        busList << new Ferry(i++, 'Pacan',     '57',   20, "$BUS_IMG_PATH/Pacan.png")
        busList << new Ferry(i++, 'Boxer',     '218',  10, "$BUS_IMG_PATH/Boxer.png")
        busList << new Ferry(i++, 'Dirty',     '54',   20, "$BUS_IMG_PATH/Dirty.png")
        busList << new Ferry(i++, 'Feniks',    '214',  10, "$BUS_IMG_PATH/Feniks.png")
        busList << new Ferry(i++, 'Tn',        '54',   20, "$BUS_IMG_PATH/Tn.png")
        busList << new Ferry(i++, 'Yellow',    '39',   15, "$BUS_IMG_PATH/Yellow.png")
        busList << new Ferry(i++, 'White',     '101',  15, "$BUS_IMG_PATH/White.png")
        busList << new Ferry(i++, 'Van',       '269',  10, "$BUS_IMG_PATH/Van.png")
        busList << new Ferry(i++, 'Merc',      '266',  10, "$BUS_IMG_PATH/Merc.png")
        busList << new Ferry(i++, 'TwoFloor',  '110',  40, "$BUS_IMG_PATH/TwoFloor.png")
        busList << new Ferry(i++, 'Green',     '130',  20, "$BUS_IMG_PATH/Green.png")
        busList << new Ferry(i++, 'GAZ',       '243',   15, "$BUS_IMG_PATH/GAZ.png")
    }

    /** Возвращает рандомный автобус из списка */
    Ferry getRandomBus() {
        int num = Math.random() * busList.size()
        return busList.get(num)
    }

    /** Возвращает кол-во всех севших в Автобусы Пассажиров */
    int getAllPassengerInCount() {
        int count = 0
        busList.each { bus ->
            count += bus.passengerCountIn
        }

        return count
    }

    /** Возвращает кол-во всех вышедших из Автобусов Пассажиров */
    int getAllPassengerOutCount() {
        int count = 0
        busList.each { bus ->
            count += bus.passengerCountOut
        }

        return count
    }

    /** Возвращает Автобус, который Больше всех довез Пассажиров */
    Ferry getMaxBus() {
        Ferry mBus = busList.get(0)
        busList.each { bus ->
            if (bus.passengerCountOut > mBus.passengerCountOut) {
                mBus = bus
            }
        }

        return mBus
    }

    /** Возвращает Автобус, который Меньше всех довез Пассажиров */
    Ferry getMinBus() {
        Ferry mBus = busList.get(0)
        busList.each { bus ->
            if (bus.passengerCountOut < mBus.passengerCountOut) {
                mBus = bus
            }
        }

        return mBus
    }

    /** Вывод статистики по Пассажирам в Автобусах */
    void printStat() {
        println '-------------------------------------------------------------------------------------------------------------------------'
        println "Кол-во всех севших в автобусы пассажиров:       $allPassengerInCount"
        println "Кол-во всех вышедших из автобусов пассажиров:   $allPassengerOutCount"
        println "Автобус довезший БОЛЬШЕ всех пассажиров:        $maxBus"
        println "Автобус довезший МЕНЬШЕ всех пассажиров:        $minBus"
        println '-------------------------------------------------------------------------------------------------------------------------'
    }

    /** Загрузка начальных значений в таблицу */
    void initTblStat(FerryTableModel model) {
        while (model.rowCount > 0) {
            model.removeRow(0)
        }
        busList.each { bus ->
            def list = [bus.name, bus.passengerCount, bus.freeSeat, bus.passengerCountOut, bus.passengerCountIn]
            model.addRow(list.toArray())
        }
    }

    /** Обновлении информации в Таблице Статистики */
    void updateTblStat(JTable table, FerryTableModel model, Ferry bus) {
        int i = 0
        table.setValueAt(bus.name, bus.number, i++)
        table.setValueAt(bus.passengerCount, bus.number, i++)
        table.setValueAt(bus.freeSeat, bus.number, i++)
        table.setValueAt(bus.passengerCountOut, bus.number, i++)
        table.setValueAt(bus.passengerCountIn, bus.number, i++)

        model.maxRow = maxBus.number
        model.minRow = minBus.number
    }

}
