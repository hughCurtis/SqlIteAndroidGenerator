/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 *
 * @author Ulrich
 */
public class FrameUsr extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JMenuItem jMenuItem1;
    private JPopupMenu jPopupMenu1;
    private JTabbedPane jTabbedPane1;

    public FrameUsr() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        jPopupMenu1 = new JPopupMenu();
        jMenuItem1 = new JMenuItem("jMenuItem1");
        jTabbedPane1 = new JTabbedPane();
        jLabel1 = new JLabel("jLabel1");
        jLabel2 = new JLabel("jLabel2");
        jPopupMenu1.add(jMenuItem1);
        jTabbedPane1.addTab(null, jLabel1);
        jTabbedPane1.addTab(null, jLabel2);
        getContentPane().add(jTabbedPane1, BorderLayout.CENTER);
        int tabCount = jTabbedPane1.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            JLabel jLabel = new JLabel("Testing the tab" + (i + 1));
            jTabbedPane1.setTabComponentAt(i, jLabel);
            jLabel.setName(String.valueOf(i));
            jLabel.setComponentPopupMenu(jPopupMenu1);
        }
        jPopupMenu1.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuCanceled(final PopupMenuEvent evt) {
            }

            @Override
            public void popupMenuWillBecomeInvisible(final PopupMenuEvent evt) {
            }

            @Override
            public void popupMenuWillBecomeVisible(final PopupMenuEvent evt) {
                JPopupMenu source = (JPopupMenu) evt.getSource();
                JLabel invoker = (JLabel) source.getInvoker();
                JLabel component = (JLabel) jTabbedPane1.getComponentAt(Integer.parseInt(invoker.getName()));
                jMenuItem1.setText(invoker.getText() + ":  " + component.getText());
            }
        });
    }

    public static void main(final String args[]) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new FrameUsr().setVisible(true);
            }
        });
    }
    
    
    
    
    
    
    
}
