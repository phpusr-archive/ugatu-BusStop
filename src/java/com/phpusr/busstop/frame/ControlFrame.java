package com.phpusr.busstop.frame;

import com.phpusr.busstop.consts.FerryWorkConsts;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 16.01.13
 * Time: 12:24
 * To change this template use File | Settings | File Templates.
 */

/**
 * Форма для управления работой Эмулятора
 */
public class ControlFrame extends JFrame {
    /** Главная панель */
    private JPanel contentPanel;
    /** Верхняя панель */
    private JPanel pnlTop;
    /** Средняя панель */
    private JPanel pnlMiddle;
    /** Нижняя панель */
    private JPanel pnlBottom;
    /** Кнопка Старт/Пауза */
    private JButton btnStart;
    /** Кнопка увеличения скорости */
    private JButton btnSpeed;
    /** Кнопка Стоп */
    private JButton btnStop;
    /** Кнопка Выход */
    private JButton btnExit;
    /** Таблица со Статистикой */
    private JTable tblStat;
    /** Лейбл для изображения Автобуса */
    private JLabel lblImg;
    /** Кол-во выходящих Пассажиров */
    private JLabel lblPassengerOut;
    /** Кол-во заходящих Пассажиров */
    private JLabel lblPassengerIn;
    /** Кол-во всего вышедших Пассажиров */
    private JLabel lblAllPassengerOut;
    /** Кол-во всего зашедших Пассажиров */
    private JLabel lblAllPassengerIn;
    /** Кол-во всего вышедших Пассажиров (все Автобусы) */
    private JLabel lblAllBusPassengerOut;
    /** Кол-во всего зашедших Пассажиров (все Автобусы) */
    private JLabel lblAllBusPassengerIn;
    /** Панель для Автобусов */
    private JPanel pnlBus;

    /** Модель для таблицы Статистики */
    private FerryTableModel model;
    /** Кастомная Панель для Автобусов */
    private FerryWorkPanel ferryWorkPanel;
    /** Показывать Старт или Пауза */
    private boolean showStart = true;
    /** Скорость работы */
    private int speed = FerryWorkConsts.MIN_SPEED;

    public ControlFrame(String title) {
        super(title);
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FerryWorkConsts.CF_WIDTH, FerryWorkConsts.CF_HEIGHT);
        setVisible(true);
        setResizable(false);

        initTable();
        initListeners();
        ferryWorkPanel.setControlFrame(this);
        ferryWorkPanel.getBusParkUtil().initTblStat(model);
    }

    /** Инициализация таблицы */
    private void initTable() {
        model = new FerryTableModel();
        model.addColumn("Автобус");
        model.addColumn("Пассажиров");
        model.addColumn("Свободных мест");
        model.addColumn("Всего вышло");
        model.addColumn("Всего зашло");
        tblStat.setModel(model);

        //Установка для таблицы кастомного Рисовальщика ячеек
        tblStat.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(model.getRowColor(row));
                return c;
            }
        });
    }

    /** Инициализация слушателей */
    private void initListeners() {
        //Старт / Пауза
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showStart = !showStart;
                if (showStart) {
                    speed = FerryWorkConsts.MIN_SPEED;
                    btnSpeed.setText(FerryWorkConsts.BTN_SPEED_NAME + speed);
                    btnSpeed.setEnabled(true);
                    btnStart.setText(FerryWorkConsts.BTN_START_NAME);
                    ferryWorkPanel.pause();
                } else {
                    btnStart.setText(FerryWorkConsts.BTN_PAUSE_NAME);
                    ferryWorkPanel.start();
                }
            }
        });
        //Стоп
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnStart.setText(FerryWorkConsts.BTN_START_NAME);
                ferryWorkPanel.stop();
                showStart = true;
                ferryWorkPanel.getBusParkUtil().initTblStat(model);
            }
        });
        //Увеличение скорости в 2 раза (Запускает еще один поток, возможно это НЕ безопасно)
        btnSpeed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                speed *= 2;
                btnSpeed.setText(FerryWorkConsts.BTN_SPEED_NAME + speed);
                btnStart.setText(FerryWorkConsts.BTN_PAUSE_NAME);
                if (speed >= FerryWorkConsts.MAX_SPEED) btnSpeed.setEnabled(false);
                ferryWorkPanel.start();
                showStart = false;
            }
        });
        //Выход
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //dispose(); //TODO не закрывает процесс
            }
        });
    }

    /** Установка на форме Вышедших Пассажиров */
    public void setPassengerCountOut(int cur, int from, int all, int allBus) {
        lblPassengerOut.setText(cur + "/" + from);
        lblAllPassengerOut.setText(Integer.toString(all));
        lblAllBusPassengerOut.setText(Integer.toString(allBus));
    }

    /** Установка на форме Севших Пассажиров */
    public void setPassengerCountIn(int cur, int from, int all, int allBus) {
        lblPassengerIn.setText(cur + "/" + from);
        lblAllPassengerIn.setText(Integer.toString(all));
        lblAllBusPassengerIn.setText(Integer.toString(allBus));
    }

    public FerryTableModel getModel() {
        return model;
    }

    public JTable getTblStat() {
        return tblStat;
    }

    private void createUIComponents() {
        pnlBus = new FerryWorkPanel();
        ferryWorkPanel = (FerryWorkPanel) pnlBus;
    }
}
