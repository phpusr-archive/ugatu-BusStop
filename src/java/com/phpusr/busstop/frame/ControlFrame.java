package com.phpusr.busstop.frame;

import com.phpusr.busstop.util.ExitAction;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

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
    private JPanel pnlBottom;
    private JButton стартButton;
    private JButton стопButton;
    private JButton btnExit;
    private JTable tblStat;
    private JLabel lblImg;
    private JPanel pnlMiddle;
    private JLabel lblPassengerOut;
    private JLabel lblAllPassengerOut;
    private JLabel lblAllPassengerIn;
    private JLabel lblPassengerIn;
    private BusTableModel model;

    public ControlFrame(String title) {
        super(title);
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setVisible(true);
        btnExit.setAction(new ExitAction(btnExit.getText()));
        lblImg.setText("");

        initTable();
    }

    void initTable() {
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

    /** Установка изображения Автобуса */
    public void setLblImg(JLabel lblImg) {
        this.lblImg = lblImg;
    }

    /** Установка на форме Вышедших Пассажиров */
    void setPassengerCountOut(int cur, int from, int all) {
        lblPassengerOut.setText(cur + "/" + from);
        lblAllPassengerOut.setText(Integer.toString(all));
    }

    /** Установка на форме Севших Пассажиров */
    void setPassengerCountIn(int cur, int from, int all) {
        lblPassengerIn.setText(cur + "/" + from);
        lblAllPassengerIn.setText(Integer.toString(all));
    }

    public BusTableModel getModel() {
        return model;
    }

    public JTable getTblStat() {
        return tblStat;
    }
}
