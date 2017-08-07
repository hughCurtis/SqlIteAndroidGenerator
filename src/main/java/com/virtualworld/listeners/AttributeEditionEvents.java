/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.listeners;

/**
 *
 * @author Ulrich
 */
public interface AttributeEditionEvents {

    /**
     * Ajoute un observateur à la liste de ceux qui observent le choix du type
     *
     * @param typeChooserListener
     */
    public void addTypeChooserListener(TypeChooserListener typeChooserListener);

    /**
     * Ajoute un observateur à la liste de ceux qui observent le check box PK,
     * sélection et déselection
     *
     * @param pKCheckListener
     */
    public void addPKCheckListener(PKCheckListener pKCheckListener);

    /**
     * Notifie tous les observateurs du choix du type que le choix a changé
     *
     * @param obj
     */
    public void updateTypeChooserListeners(Object obj);

    /**
     * Notifie tous les observateurs de la case PK que la valeur a changé
     *
     * @param obj
     */
    public void updatePKCheckListener(Object obj);

}
