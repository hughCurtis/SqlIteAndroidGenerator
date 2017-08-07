/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.generic.tabbedpanel;

import com.virtualworld.generic.tabbedpanel.listeners.TabCloseListener;
import com.virtualworld.generic.tabbedpanel.listeners.TabEvents;
import com.virtualworld.generic.tabbedpanel.listeners.TabRenameListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Ulrich
 */
public class ButtonTabComponent extends JPanel implements TabEvents {

    private final int id;
    private String title = "";
    private final List<TabCloseListener> tabCloseListeners = new ArrayList<>();
    private final List<TabRenameListener> tabRenameListeners = new ArrayList<>();

    /**
     * s
     *
     * @param title
     * @param id
     */
    public ButtonTabComponent(String title, int id) {
        this.title = title;
        this.id = id;
        initComponents();
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    private void initComponents() {
        buttonClose = new CloseTabButton();
        buttonRename = new RenameTabButton();
        //make JLabel read titles from JTabbedPane
        label = new JLabel() {
            @Override
            public String getText() {
                return title;
            }
        };
        relationOptionsJPopupMenu = new javax.swing.JPopupMenu();
        renameJMenuItem = new javax.swing.JMenuItem();

        renameJMenuItem.setText("Renommer");
        renameJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                renameJMenuItemActionPerformed(evt);
            }
        });
        relationOptionsJPopupMenu.add(renameJMenuItem);

//        setComponentPopupMenu(relationOptionsJPopupMenu);
        setOpaque(false);

        //add more space between the label and the button
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        label.setInheritsPopupMenu(true);
        add(label);
        //tab button

        buttonRename.setInheritsPopupMenu(true);
        buttonRename.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRenameEventListeners(getId());
            }
        });
        add(buttonRename);

        buttonClose.setInheritsPopupMenu(true);
        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCloseEventListeners(getId());
            }
        });
        add(buttonClose);
        //add more space to the top of the component
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    private void renameJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        String value = JOptionPane.showInputDialog("Veuillez fournir le nouveau nom");
        if (value == null || value.equals("")) {
            return;
        }
        title = value;
        repaint();
    }

    public void rename(String newTitle) {
        this.title = newTitle;
    }

    private javax.swing.JPopupMenu relationOptionsJPopupMenu;
    private javax.swing.JMenuItem renameJMenuItem;
    private JLabel label;
    private CloseTabButton buttonClose;
    private RenameTabButton buttonRename;

    @Override
    public void addTabCloseEventListener(TabCloseListener tabCloseListener) {
        tabCloseListeners.add(tabCloseListener);
    }

    @Override
    public void updateCloseEventListeners(int elementId) {
        for (TabCloseListener tcl : tabCloseListeners) {
            tcl.onTabClose(elementId);
        }
    }

    @Override
    public void addTabRenameEventListener(TabRenameListener tabRenameListener) {
        tabRenameListeners.add(tabRenameListener);
    }

    @Override
    public void updateRenameEventListeners(int elementId) {
        for (TabRenameListener trl : tabRenameListeners) {
            trl.onTabRename(elementId);
        }
    }
}
