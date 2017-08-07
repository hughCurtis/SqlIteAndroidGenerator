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
import javax.swing.JButton;

/**
 *
 * @author Ulrich
 */
public class ButtonAddTabComponent extends JButton implements TabEvents {

    private final int id = -1;
    private final String title = "ADD_TITLE";
    private final List<TabCloseListener> tabCloseListeners = new ArrayList<>();

    private ButtonAddTabComponent() {
        initComponents();
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    private void initComponents() {
        setOpaque(false);
        setText("+");
        //tab button
//        setLayout(new GridLayout(1, 1));
        JButton button = new CloseTabButton();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                updateCloseEventListeners(title);
            }
        });
//        add(button);
        //add more space to the top of the component
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    @Override
    public void addTabCloseEventListener(TabCloseListener tabCloseListener) {
        tabCloseListeners.add(tabCloseListener);
    }

    @Override
    public void updateCloseEventListeners(int elementId) {
        for (TabCloseListener tcl : tabCloseListeners) {
            tcl.onTabClose(getId());
        }
    }

    @Override
    public void addTabRenameEventListener(TabRenameListener tabCloseListener) {
    }

    @Override
    public void updateRenameEventListeners(int elementId) {
    }
}
