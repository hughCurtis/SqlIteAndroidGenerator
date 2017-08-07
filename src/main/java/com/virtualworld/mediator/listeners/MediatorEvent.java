/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.mediator.listeners;

/**
 *
 * @author Ulrich
 */
public interface MediatorEvent {

    /**
     * Ajoute un objet à la liste de ceux à notifier du changement d'etat.
     *
     * @param mediatorEventListener
     */
    public void addMediatorEventListener(MediatorEventListener mediatorEventListener);

    /**
     * Notifie tous les listeners du changement d'état d'un des composants
     */
    public void updateMediatorListeners();

    /**
     * Supprimer un listener de la liste
     *
     * @param mediatorEventListener
     */
    public void removeMediatorEventListener(MediatorEventListener mediatorEventListener);

}
