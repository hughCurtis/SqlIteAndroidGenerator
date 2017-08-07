/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.jtable.models;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Ulrich
 */
public class CustomTableRenderer extends DefaultTableCellRenderer {

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        d.height = 32;
        return d;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.

        if (!isSelected) {
            setForeground(Color.BLACK);
            if (row % 2 == 0) {
                component.setBackground(Color.WHITE);
            } else {
                component.setBackground(new Color(230, 230, 230));
            }
        } else {
            component.setForeground(table.getSelectionForeground());
            component.setBackground(table.getSelectionBackground());
        }
        return component;
    }

}
