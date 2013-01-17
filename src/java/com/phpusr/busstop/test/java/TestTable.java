package com.phpusr.busstop.test.java;

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 16.01.13
 * Time: 15:00
 * To change this template use File | Settings | File Templates.
 */

import javax.swing.*;
import java.awt.*;

public class TestTable {

    public static void createGUI() {
        JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columnNames = {
                "Name",
                "Last modified",
                "Type",
                "Size"
        };

        String[][] data = {
                {"addins", "02.11.2006 19:15", "Folder", ""},
                {"AppPatch", "03.10.2006 14:10", "Folder", ""},
                {"assembly", "02.11.2006 14:20", "Folder", ""},
                {"Boot", "13.10.2007 10:46", "Folder", ""},
                {"Branding", "13.10.2007 12:10", "Folder", ""},
                {"Cursors", "23.09.2006 16:34", "Folder", ""},
                {"Debug", "07.12.2006 17:45", "Folder", ""},
                {"Fonts", "03.10.2006 14:08", "Folder", ""},
                {"Help", "08.11.2006 18:23", "Folder", ""},
                {"explorer.exe", "18.10.2006 14:13", "File", "2,93MB"},
                {"helppane.exe", "22.08.2006 11:39", "File", "4,58MB"},
                {"twunk.exe", "19.08.2007 10:37", "File", "1,08MB"},
                {"nsreg.exe", "07.08.2007 11:14", "File", "2,10MB"},
                {"avisp.exe", "17.12.2007 16:58", "File", "12,67MB"},
        };

        JTable table = new JTable(data, columnNames);

        JScrollPane scrollPane = new JScrollPane(table);

        frame.getContentPane().add(scrollPane);
        frame.setPreferredSize(new Dimension(450, 200));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
                JFrame.setDefaultLookAndFeelDecorated(true);
            }
        });
    }
}

