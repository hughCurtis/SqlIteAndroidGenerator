/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.jtable.models;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Ulrich
 */
public class BooleanCellRenderer extends JCheckBox implements TableCellRenderer {

    private final Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

    public BooleanCellRenderer() {
        super();
        setLayout(new GridBagLayout());
        setMargin(new Insets(0, 0, 0, 0));
        setHorizontalAlignment(JLabel.CENTER);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        if (value instanceof Boolean) {
            setSelected((boolean) value);
        }
        if (!isSelected) {
            if (row % 2 == 0) {
                setBackground(Color.WHITE);
            } else {
                setBackground(new Color(230, 230, 230));
            }
            setForeground(Color.BLACK);
        } else {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        }
        if (hasFocus) {
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
        } else {
            setBorder(noFocusBorder);
        }
        return this;
    }

}
