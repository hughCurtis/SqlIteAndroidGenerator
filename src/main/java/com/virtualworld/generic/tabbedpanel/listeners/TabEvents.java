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
public interface TabEvents {

    /**
     * Ajoute un composant à la liste de ceux qui écoutent la fermeture de
     * l'onglet
     *
     * @param tabCloseListener
     */
    public void addTabCloseEventListener(TabCloseListener tabCloseListener);

    /**
     * Met à jour tous les composants observant l'évènement de fermeture du
     * panel lorsque l'action a lieu
     *
     * @param elementId
     */
    public void updateCloseEventListeners(int elementId);

    /**
     * Ajoute un composant à la liste de ceux qui écoutent l'action de renommage
     * de l'onglet
     *
     * @param tabRenameListener
     */
    public void addTabRenameEventListener(TabRenameListener tabRenameListener);

    /**
     * Met à jour tous les composants observant l'évènement de renommage du
     * panel lorsque l'action a lieu
     *
     * @param elementId
     */
    public void updateRenameEventListeners(int elementId);

}
