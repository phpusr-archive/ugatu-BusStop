package com.phpusr.ferrywork.frame;

import com.phpusr.ferrywork.consts.FerryWorkConsts;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 27.01.13
 * Time: 14:16
 * To change this template use File | Settings | File Templates.
 */

/**
 * Форма ввода Параметров
 */
public class ParamsFrame extends JFrame {
    private JPanel contentPanel;
    private JTextField txtSeatCount;
    private JTextField txtParkingCount;
    private JButton OKButton;
    private JTextField txtFerryName;
    private JButton btnExit;

    public ParamsFrame(String title) {
        super(title);
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FerryWorkConsts.PF_WIDTH, FerryWorkConsts.PF_HEIGHT);
        setResizable(false);
        setVisible(true);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ControlFrame("Ferry Work Simulation Control", getParams());
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private Map getParams() {
        Map params = new HashMap();
        params.put("ferryName", txtFerryName.getText());
        params.put("seatCount", Integer.parseInt(txtSeatCount.getText()));
        params.put("parkingCount", Integer.parseInt(txtParkingCount.getText()));

        return params;
    }
}
