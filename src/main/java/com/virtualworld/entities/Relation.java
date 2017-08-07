/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.entities;

import com.virtualworld.gui.panels.DatabaseJPanel;
import com.virtualworld.model.Database;
import java.util.List;

/**
 *
 * @author Ulrich
 */
public class Relation {

    private int id;
    private String name;
    private String description = "";

    public static final String DEFAULT_RELATION_NAME = "Tab";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name.substring(0, 1).toLowerCase() + name.substring(1).toLowerCase();
        }
        if (name != null) {
            this.name = this.name.replace(' ', '_');
        } else {
            this.name = name;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toStringContract(int indentTimes) {
        List<Attribut> attributs = Database.findAttributsByRelationId(id);
        String indent = "";
        for (int i = 0; i < indentTimes; i++) {
            indent += "\t";
        }
        //entete de la relation dans le fichier *Contract.java
        String str = indent + "/* le contenu de cette classe definit les colones de la table " + name + " */\n"
                + indent + "public static final class " + name + "Entry implements BaseColumns {\n" + indent + "\t" + "//" + description + "\n" + indent + "\t" + "public static final String TABLE_NAME = \"" + name + "\";";

        for (int i = 0; i < attributs.size(); i++) {
            str = str + "\n" + attributs.get(i).toStringContract(indentTimes + 1);
        }
        return str + indent + "}\n";

    }

    public String toStringOpenHelper(int indentTimes,String dataBaseName) {
        List<Attribut> attributs = Database.findAttributsByRelationId(id);
        String indent = "";
        for (int i = 0; i < indentTimes; i++) {
            indent += "\t";
        }
        //entete de la relation dans le fichier *OpenHelper.java
        String str = "// creation de la table " + name + ".\n"
                + "        final String SQL_CREATE_" + name.toUpperCase() + "_TABLE = \"CREATE TABLE \" + " +dataBaseName  + "Contract." + name + "Entry.TABLE_NAME + \" (\" +";
        
        int i ;
        for (i=0; i < attributs.size()-1; i++) {
            str = str + "\n" + attributs.get(i).toStringOpenHelper(indentTimes + 1,name,dataBaseName) + ", \" +";
        }
        str = str + "\n" + attributs.get(i).toStringOpenHelper(indentTimes + 1,name,dataBaseName)+" \" + "+ "\n \" );\";";
        return str + indent + "}\n";
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Relation other = (Relation) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return name;
    }

}
