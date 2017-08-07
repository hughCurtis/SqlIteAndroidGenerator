/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.entities;

import com.virtualworld.gui.panels.DatabaseJPanel;
import com.virtualworld.model.Database;

/**
 *
 * @author Ulrich
 */
public class Attribut {

    private int id;
    private String name;
    private DbTypes type;
    private String commentaire = "";
    private boolean nullable = true;
    private boolean pk = false;
    private Attribut fkOn;
    private Relation relation;

    public Attribut() {
    }

    public Attribut(String nom, DbTypes type) {
        this.name = nom;
        this.type = type;
    }

    public Attribut(String nom, DbTypes type, String commentaire, boolean nullable, Attribut cleEtrangereSur) {
        this.name = nom;
        this.type = type;
        this.commentaire = commentaire;
        this.nullable = nullable;
        this.fkOn = cleEtrangereSur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DbTypes getType() {
        return type;
    }

    public void setType(DbTypes type) {
        this.type = type;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isPk() {
        return pk;
    }

    public void setPk(boolean pk) {
        this.pk = pk;
    }

    public Attribut getFkOn() {
        return fkOn;
    }

    public void setFkOn(Attribut fkOn) {
        this.fkOn = fkOn;
    }

    //methodes
    public String toStringContract(int indentTimes) {
        String indent = "";
        for (int i = 0; i < indentTimes; i++) {
            indent += "\t";
        }
        //dans le fichier *Contract.java

        String str = indent + "// " + commentaire + "\n" + indent + "public static final String COLUMN_" + name.toUpperCase() + " = \"";
        str = str + name + "\";\n";
        return str;

    }

    public String toStringOpenHelper(int indentTimes, String relationName, String dataBaseName) {
        String indent = "";
        for (int i = 0; i < indentTimes; i++) {
            indent += "\t";
        }
        //dans le fichier *OpenHelper.java

        String str = "";
        if (pk == true) {
            str = indent + dataBaseName + "Contract."
                    + relationName + "Entry.COLUMN_" + name.toUpperCase() + " +\" " + type +" PRIMARY KEY AUTOINCREMENT";
        } else if (fkOn != null) {
             str = indent + ""+dataBaseName+"Contract."+relation.getName()+"Entry.COLUMN_"+fkOn.name.toUpperCase()+"_KEY +\" "+type+", \" +\n" +
"                \"    FOREIGN KEY (\" + "+dataBaseName+"Contract."+relationName+"Entry.COLUMN_"+fkOn.getName().toUpperCase()+"_KEY + \") REFERENCES \" + "+dataBaseName+"Contract."+relationName+"Entry.TABLE_NAME + \"(\" + "+dataBaseName+"Contract."+fkOn.relation.getName()+"Entry._ID + \")";
        } else {

            str = indent + dataBaseName + "Contract."
                    + relationName + "Entry.COLUMN_" + name.toUpperCase() + " " + " +\" "+type;
        }

        return str;
    }
//    String toStringDBHelper() {
//        String str;
//        if (contrainte.isEmpty()) {
//            str = "COLUMN_" + name.toUpperCase() + " + \" " + type.toUpperCase() + " , \" +";
//
//        } else {
//            str = "COLUMN_" + name.toUpperCase() + " + \" " + type.toUpperCase() + " " + contrainte.toUpperCase() + ", \" +";
//
//        }
//        return str;
//    }

    @Override
    public String toString() {
        return relation + "(" + name + ")";
    }

}
