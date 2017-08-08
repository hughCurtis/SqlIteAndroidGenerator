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
public interface PKSelectionChangeListener {

    /**
     * Une PK est sélectionnée ou déselectionnée dans un table: action à
     * entreprendre à la suite
     *
     * @param attribut: Attribut sur lequel la propriété change
     */
    public void onPKSelectionChange(Attribut attribut);
}
