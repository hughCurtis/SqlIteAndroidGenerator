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
public interface PKCheckListener {

    /**
     * Losque l'utilisateur coche ou décoche la case marquant une clé primaire
     *
     * @param obj
     */
    public void onPKChechChange(Object obj);

}
