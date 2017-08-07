/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.generic.tabbedpanel.listeners;

/**
 *
 * @author Ulrich
 */
public interface TabCloseListener {

    /**
     * Action à exécuter si l'utilisateur décide de fermer l'onglet du
     * tabbedPanel
     *
     * @param elementId
     */
    public void onTabClose(int elementId);

}
