package com.phpusr.busstop.frame;

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
public class ControlFrame extends JFrame {
    private JPanel contentPanel;
    private JPanel pnlTop;
    private JPanel pnlMiddle;
    private JPanel pnlBottom;
    private JButton btnStart;
    private JButton btnSpeed;
    private JButton btnStop;
    private JButton btnExit;
    private JTable tblStat;
    private JLabel lblImg;
    private JLabel lblPassengerOut;
    private JLabel lblAllPassengerOut;
    private JLabel lblAllPassengerIn;
    private JLabel lblPassengerIn;

    //TODO комменты
    private BusTableModel model;
    private BusStopFrame busStopFrame;
    private boolean start = true;
    private int speed = 2;

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
                if (start) {
                    btnStart.setText("Пауза"); //TODO вынести
                    busStopFrame.start();
                } else {
                    btnStart.setText("Старт"); //TODO вынести
                    busStopFrame.pause();
                }
                start = !start;
            }
        });
        //Стоп
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStart.setText("Старт"); //TODO вынести
                busStopFrame.stop();
                start = true;
            }
        });
        //Увеличение скорости в 2 раза (Запускает еще один поток, возможно это НЕ безопасно)
        btnSpeed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speed *= 2;
                btnSpeed.setText("x" + speed); //TODO вынести
                btnStart.setText("Старт"); //TODO вынести
                busStopFrame.pause();
                start = true;
                //TODO если стоял на пазуе
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

    public void setBusStopFrame(BusStopFrame busStopFrame) {
        this.busStopFrame = busStopFrame;
    }

    public BusTableModel getModel() {
        return model;
    }

    public JTable getTblStat() {
        return tblStat;
    }
}
