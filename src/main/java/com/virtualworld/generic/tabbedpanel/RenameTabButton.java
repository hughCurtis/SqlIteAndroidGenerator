/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.generic.tabbedpanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author Ulrich
 */
public class RenameTabButton extends JButton {

    public RenameTabButton() {
        initComponents();
    }

    private void initComponents() {
        int size = 17;
        setPreferredSize(new Dimension(size, size));
        setToolTipText("Renommer");
        //Make the button looks the same for all Laf's
        setUI(new BasicButtonUI());
        //Make it transparent
        setContentAreaFilled(false);
        //No need to be focusable
        setFocusable(false);
        setBorder(BorderFactory.createEtchedBorder());
        setBorderPainted(false);
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconMODIFIER_16x16.png")));
        //Making nice rollover effect
        //we use the same listener for all buttons
        addMouseListener(BUTTON_MOUSE_LISTENER);
        setRolloverEnabled(true);
    }

    //we don't want to update UI for this button
    @Override
    public void updateUI() {
    }

    private final static MouseListener BUTTON_MOUSE_LISTENER = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}
