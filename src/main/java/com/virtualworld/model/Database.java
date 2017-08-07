/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.model;

import com.virtualworld.entities.Attribut;
import com.virtualworld.entities.Relation;
import com.virtualworld.model.exceptions.NonExistantValueException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ulrich
 */
public class Database {

    private static int relationIdGenerator = 1;
    private static final List<Relation> RELATIONS = new ArrayList<>();
    private static int attributIdGenerator = 1;
    private static final List<Attribut> ATTRIBUTS = new ArrayList<>();

    private Database() {
    }

    public static synchronized void addRelation(Relation relation) {
        relation.setId(relationIdGenerator++);
        RELATIONS.add(relation);
    }

    public static synchronized void addAttribut(Attribut attribut) throws NonExistantValueException {
        if (attribut.getRelation() != null) {
            boolean found = false;
            for (Relation r : RELATIONS) {
                if (r.getId() == attribut.getRelation().getId()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new NonExistantValueException("Aucune relation portant l'id " + attribut.getRelation().getId() + " n'existe");
            }
        }
        attribut.setId(attributIdGenerator++);
        ATTRIBUTS.add(attribut);
    }

    public static synchronized void deleteRelation(int relationId) throws NonExistantValueException {
        int taille = ATTRIBUTS.size();
        Attribut a;
        for (int i = 0; i < taille; i++) {
            a = ATTRIBUTS.get(i);
            if (a.getRelation().getId() == relationId) {
                ATTRIBUTS.remove(i);
                i--;
                taille--;
            }
        }
        for (Relation r : RELATIONS) {
            if (r.getId() == relationId) {
                RELATIONS.remove(r);
                return;
            }
        }
        throw new NonExistantValueException("Aucune relation portant cet id n'existe");
    }

    public static synchronized void deleteAttribut(int attributId) throws NonExistantValueException {
        for (Attribut a : ATTRIBUTS) {
            if (a.getId() == attributId) {
                ATTRIBUTS.remove(a);
                return;
            }
        }
        throw new NonExistantValueException("Aucun attribut portant cet id n'existe");
    }

    public static synchronized void editRelation(Relation relation) throws NonExistantValueException {
        for (Relation r : RELATIONS) {
            if (r.equals(relation)) {
                RELATIONS.remove(r);
                RELATIONS.add(relation);
                return;
            }
        }
        throw new NonExistantValueException("Cette relation n'existe pas");
    }

    public static synchronized void editAttribut(Attribut attribut) throws NonExistantValueException {
        for (Attribut a : ATTRIBUTS) {
            if (a.equals(attribut)) {
                ATTRIBUTS.remove(a);
                ATTRIBUTS.add(attribut);
                return;
            }
        }
        throw new NonExistantValueException("Cet attribut n'existe pas");
    }

    public static List<Relation> getRelations() {
        return RELATIONS;
    }

    public static List<Attribut> getAttributs() {
        return ATTRIBUTS;
    }

    public static List<Attribut> getPKAttributs() {
        List<Attribut> attributsPK = new ArrayList<>();
        for (Attribut a : ATTRIBUTS) {
            if (a.isPk()) {
                attributsPK.add(a);
            }
        }
        return attributsPK;
    }

    public static List<Attribut> findAttributsByRelationId(int relationId) {
        List<Attribut> attributs = new ArrayList<>();
        for (Attribut a : ATTRIBUTS) {
            if (a.getRelation().getId() == relationId) {
                attributs.add(a);
            }
        }
        return attributs;
    }

    public static Relation findRelation(int idRelation) {
        for (Relation r : RELATIONS) {
            if (r.getId() == idRelation) {
                return r;
            }
        }
        return null;
    }
}
