/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.jtable.listeners;

import com.virtualworld.dao.entities.Attribut;

/**
 *
 * @author Ulrich
 */
public interface PKSelectionEvent {

    public void addPKSelectionChangeListener(PKSelectionChangeListener pKSelectionChangeListener);

    public void updatePKSelectionChangeListeners(Attribut attribut);

    public void removePKSelectionChangeListener(PKSelectionChangeListener pKSelectionChangeListener);
}
