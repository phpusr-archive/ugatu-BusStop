package com.phpusr.ferrywork.frame;

import com.phpusr.ferrywork.consts.FerryWorkConsts;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

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
    /** Панель для таблицы статистики */
    private JPanel pnlTable;
    /** Панель для Рисования */
    private JPanel pnlPaint;

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
    /** Кол-во выходящих Пассажиров */
    private JLabel lblPassengerOut;
    /** Кол-во заходящих Пассажиров */
    private JLabel lblPassengerIn;
    /** Кол-во выезжающих Автомобилей */
    private JLabel lblCarOut;
    /** Кол-во заезжающих Автомобилей */
    private JLabel lblCarIn;

    /** Модель для таблицы Статистики */
    private FerryTableModel model;
    /** Кастомная Панель для Паромов */
    private FerryWorkPanel ferryWorkPanel;
    /** Показывать Старт или Пауза */
    private boolean showStart = true;
    /** Скорость работы */
    private int speed = FerryWorkConsts.MIN_SPEED;

    public ControlFrame(String title, Map params) {
        super(title);
        System.out.println("ControlFrame()");
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(FerryWorkConsts.CF_WIDTH, FerryWorkConsts.CF_HEIGHT);
        centerFrame();
        setResizable(false);
        setVisible(true);

        initTable();
        initListeners();
        ferryWorkPanel.init(params);
        ferryWorkPanel.setControlFrame(this);
        ferryWorkPanel.getFerryUtil().initTblStat(model);
    }

    /** Выровнить окно по Центру */
    private void centerFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int)(screenSize.getWidth()/2 - getSize().getWidth()/2);
        int y = (int)(screenSize.getHeight()/2 - getSize().getHeight()/2);
        setLocation(x, y);
    }

    /** Инициализация таблицы */
    private void initTable() {
        model = new FerryTableModel();
        model.addColumn("Паром");
        model.addColumn("Пассажиров");
        model.addColumn("Свободных мест");
        model.addColumn("Всего вышло");
        model.addColumn("Всего зашло");

        model.addColumn("Авто");
        model.addColumn("Свободных авто-мест");
        model.addColumn("Всего выехало");
        model.addColumn("Всего заехало");
        tblStat.setModel(model);

        //Установка для таблицы кастомного Рисовальщика ячеек
        tblStat.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (FerryWorkConsts.TABLE_HIGHLIGHT) c.setBackground(model.getRowColor(row));
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
                speed = FerryWorkConsts.MIN_SPEED;
                btnSpeed.setText(FerryWorkConsts.BTN_SPEED_NAME + speed);
                btnSpeed.setEnabled(true);

                btnStart.setText(FerryWorkConsts.BTN_START_NAME);
                ferryWorkPanel.stop();
                showStart = true;
                ferryWorkPanel.getFerryUtil().initTblStat(model);
            }
        });
        //Увеличение скорости в 2 раза (Уменьшается время между кадрами)
        btnSpeed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                speed *= 2;
                btnSpeed.setText(FerryWorkConsts.BTN_SPEED_NAME + speed);
                if (speed >= FerryWorkConsts.MAX_SPEED) btnSpeed.setEnabled(false);
                ferryWorkPanel.speedUp();
            }
        });
        //Выход
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ferryWorkPanel.stop();
                dispose();
            }
        });
    }

    /** Установка на форме Вышедших Пассажиров */
    public void setPassengerCountOut(int passengerCountOut, int passengerCountOutConst, int carCountOut, int carCountOutConst) {
        lblPassengerOut.setText(passengerCountOutConst-passengerCountOut + "/" + passengerCountOutConst);
        lblCarOut.setText(carCountOutConst-carCountOut + "/" + carCountOutConst);
    }

    /** Установка на форме Севших Пассажиров */
    public void setPassengerCountIn(int passengerCountIn, int passengerCountInConst, int carCountIn, int carCountInConst) {
        lblPassengerIn.setText(passengerCountInConst-passengerCountIn + "/" + passengerCountInConst);
        lblCarIn.setText(carCountInConst-carCountIn + "/" + carCountInConst);
    }

    public FerryTableModel getModel() {
        return model;
    }

    public JTable getTblStat() {
        return tblStat;
    }

    private void createUIComponents() {
        System.out.println("createUIComponents()");
        pnlPaint = new FerryWorkPanel();
        ferryWorkPanel = (FerryWorkPanel) pnlPaint;
    }
}
