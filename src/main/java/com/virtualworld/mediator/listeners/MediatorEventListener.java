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
public interface MediatorEventListener {

    /**
     * Méthode à exécuter lorsque le nom d'une relation change ou que la liste
     * des PK est mise à jour
     */
    public void onRelationChange();

}
