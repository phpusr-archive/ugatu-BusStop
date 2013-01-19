package com.phpusr.busstop.util

import com.phpusr.busstop.entity.Bus
import com.phpusr.busstop.frame.BusTableModel

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
    /** Относительный путь к папке с ихображениями Автобусов */
    static final String imgPath = '../img/bus'
    /** Список автобусов */
    List<Bus> busList = []

    BusParkUtil() {
        int i = 0
        busList << new Bus(i++, 'Pacan',     '57',   20, "$imgPath/Pacan.png")
        busList << new Bus(i++, 'Boxer',     '218',  10, "$imgPath/Boxer.png")
        busList << new Bus(i++, 'Dirty',     '54',   20, "$imgPath/Dirty.png")
        busList << new Bus(i++, 'Feniks',    '214',  10, "$imgPath/Feniks.png")
        busList << new Bus(i++, 'Tn',        '54',   20, "$imgPath/Tn.png")
        busList << new Bus(i++, 'Yellow',    '39',   15, "$imgPath/Yellow.png")
        busList << new Bus(i++, 'White',     '101',  15, "$imgPath/White.png")
        busList << new Bus(i++, 'Van',       '269',  10, "$imgPath/Van.png")
        busList << new Bus(i++, 'Merc',      '266',  10, "$imgPath/Merc.png")
        busList << new Bus(i++, 'TwoFloor',  '110',  40, "$imgPath/TwoFloor.png")
        busList << new Bus(i++, 'Green',     '130',  20, "$imgPath/Green.png")
        busList << new Bus(i++, 'GAZ',       '243',   15, "$imgPath/GAZ.png")
    }

    /** Возвращает рандомный автобус из списка */
    Bus getRandomBus() {
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
    Bus getMaxBus() {
        Bus mBus = busList.get(0)
        busList.each { bus ->
            if (bus.passengerCountOut > mBus.passengerCountOut) {
                mBus = bus
            }
        }

        return mBus
    }

    /** Возвращает Автобус, который Меньше всех довез Пассажиров */
    Bus getMinBus() {
        Bus mBus = busList.get(0)
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
    void initTblStat(BusTableModel model) {
        while (model.rowCount > 0) {
            model.removeRow(0)
        }
        busList.each { bus ->
            def list = [bus.name, bus.passengerCount, bus.freeSeat, bus.passengerCountOut, bus.passengerCountIn]
            model.addRow(list.toArray())
        }
    }

    /** Обновлении информации в Таблице Статистики */
    void updateTblStat(JTable table, BusTableModel model, Bus bus) {
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
