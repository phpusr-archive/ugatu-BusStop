package com.phpusr.busstop.frame;

import com.phpusr.busstop.consts.BusStopConsts;
import com.phpusr.busstop.util.ExitAction;

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
    /** TODO */
    private JPanel pnlBus;

    /** Модель для таблицы Статистики */
    private BusTableModel model;
    /** Форма для Автобусов */
    private BusStopPanel busStopPanel;
    /** Показывать Старт или Пауза */
    private boolean showStart = true;
    /** Скорость работы */
    private int speed = BusStopConsts.MIN_SPEED;

    public ControlFrame(String title) {
        super(title);
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setVisible(true);
        lblImg.setText("");

        initTable();
        initListeners();
    }

    /** Инициализация таблицы */
    private void initTable() {
        model = new BusTableModel();
        model.addColumn("Автобус");
        model.addColumn("Пассажиров");
        model.addColumn("Свободных мест");
        model.addColumn("Всего вышло");
        model.addColumn("Всего село");
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
            @Override
            public void actionPerformed(ActionEvent e) {
                showStart = !showStart;
                if (showStart) {
                    speed = BusStopConsts.MIN_SPEED;
                    btnSpeed.setText(BusStopConsts.BTN_SPEED_NAME + speed);
                    btnSpeed.setEnabled(true);
                    btnStart.setText(BusStopConsts.BTN_START_NAME);
                    busStopPanel.pause();
                } else {
                    btnStart.setText(BusStopConsts.BTN_PAUSE_NAME);
                    busStopPanel.start();
                }
            }
        });
        //Стоп
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStart.setText(BusStopConsts.BTN_START_NAME);
                busStopPanel.stop();
                showStart = true;
            }
        });
        //Увеличение скорости в 2 раза (Запускает еще один поток, возможно это НЕ безопасно)
        btnSpeed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speed *= 2;
                btnSpeed.setText(BusStopConsts.BTN_SPEED_NAME + speed);
                btnStart.setText(BusStopConsts.BTN_PAUSE_NAME);
                if (speed >= BusStopConsts.MAX_SPEED) btnSpeed.setEnabled(false);
                busStopPanel.start();
                showStart = false;
            }
        });
        //Выход
        btnExit.setAction(new ExitAction(btnExit.getText()));
    }

    /** Установка изображения Автобуса */
    public void setLblImgIcon(Icon icon) {
        this.lblImg.setIcon(icon);
    }

    /** Установка на форме Вышедших Пассажиров */
    public void setPassengerCountOut(int cur, int from, int all) {
        lblPassengerOut.setText(cur + "/" + from);
        lblAllPassengerOut.setText(Integer.toString(all));
    }

    /** Установка на форме Севших Пассажиров */
    public void setPassengerCountIn(int cur, int from, int all) {
        lblPassengerIn.setText(cur + "/" + from);
        lblAllPassengerIn.setText(Integer.toString(all));
    }

    public void setBusStopPanel(BusStopPanel busStopPanel) {
        this.busStopPanel = busStopPanel;
    }

    public BusTableModel getModel() {
        return model;
    }

    public JTable getTblStat() {
        return tblStat;
    }

    private void createUIComponents() {
        pnlBus = new BusStopPanel(this);
        busStopPanel = (BusStopPanel) pnlBus;
    }
}
