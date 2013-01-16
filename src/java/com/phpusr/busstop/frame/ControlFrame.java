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

    public ControlFrame(String title) {
        super(title);
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setVisible(true);
        btnExit.setAction(new ExitAction(btnExit.getText()));
        lblImg.setText("");
    }

    public void setLblImg(JLabel lblImg) {
        this.lblImg = lblImg;
    }
}
