package com.phpusr.busstop.frame;

import com.phpusr.busstop.util.ExitAction;

import javax.swing.*;

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
    private JTable table1;
    private JLabel lblImg;
    private JPanel pnlMiddle;
    private JLabel lblPassengerOut;
    private JLabel lblAllPassengerOut;
    private JLabel lblAllPassengerIn;
    private JLabel lblPassengerIn;

    public ControlFrame(String title) {
        super(title);
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setVisible(true);
        btnExit.setAction(new ExitAction(btnExit.getText()));
        lblImg.setText("");
    }

    /** Установка изображения Автобуса */
    public void setLblImg(JLabel lblImg) {
        this.lblImg = lblImg;
    }

    /** Установка Вышедших Пассажиров */
    void setPassengerCountOut(int cur, int from, int all) {
        lblPassengerOut.setText(cur + "/" + from);
        lblAllPassengerOut.setText(Integer.toString(all));
    }

    /** Установка Севших Пассажиров */
    void setPassengerCountIn(int cur, int from, int all) {
        lblPassengerIn.setText(cur + "/" + from);
        lblAllPassengerIn.setText(Integer.toString(all));
    }
}
