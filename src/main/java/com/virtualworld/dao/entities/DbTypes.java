/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.dao.entities;

/**
 *
 * @author Ulrich
 */
public enum DbTypes {

    INTEGER("INTEGER"),
    TEXT("TEXT"),
    REAL("REAL"),
    BLOB("BLOB"),
    NULL("NULL");

    private String name = "";

    DbTypes(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
