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
public class CloseTabButton extends JButton {

    public CloseTabButton() {
        initComponents();
    }

    private void initComponents() {
        int size = 17;
        setPreferredSize(new Dimension(size, size));
        setToolTipText("Supprimer la relation");
        //Make the button looks the same for all Laf's
        setUI(new BasicButtonUI());
        //Make it transparent
        setContentAreaFilled(false);
        //No need to be focusable
        setFocusable(false);
        setBorder(BorderFactory.createEtchedBorder());
        setBorderPainted(false);
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconSUPPRIMER_16x16.png")));
        //Making nice rollover effect
        //we use the same listener for all buttons
        addMouseListener(BUTTON_MOUSE_LISTENER);
        setRolloverEnabled(true);
    }

    //we don't want to update UI for this button
    @Override
    public void updateUI() {
    }

    //paint the cross
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g.create();
//        //shift the image for pressed buttons
//        if (getModel().isPressed()) {
//            g2.translate(1, 1);
//        }
//        g2.setStroke(new BasicStroke(2));
//        g2.setColor(Color.BLACK);
//        if (getModel().isRollover()) {
//            g2.setColor(Color.MAGENTA);
//        }
//        int delta = 6;
//        g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
//        g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
//        g2.dispose();
//    }
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
