/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.tree;

import com.virtualworld.dao.ctrl.AttributJpaController;
import com.virtualworld.dao.entities.Attribut;
import com.virtualworld.gui.panels.RelationJPanel;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Ulrich TIAYO <tiayo.pro@gmail.com>
 */
public class RelationTreeNode extends DefaultMutableTreeNode {

    private final AttributJpaController attributJpaController = new AttributJpaController();
    private final RelationJPanel relation;

    /**
     *
     * @param relation
     */
    public RelationTreeNode(RelationJPanel relation) {
        super(relation);
        this.relation = relation;
        initComponents();
    }

    private void initComponents() {
        List<Attribut> attributs = attributJpaController.findAttributsByRelationId(relation.getId());
        attributs.stream().map((at) -> new DefaultMutableTreeNode(at.getName())).forEachOrdered((node) -> {
            add(node);
        });
    }

    public void reload() {
        removeAllChildren();
        List<Attribut> attributs = attributJpaController.findAttributsByRelationId(relation.getId());
        attributs.stream().map((at) -> new DefaultMutableTreeNode(at.getName())).forEachOrdered((node) -> {
            add(node);
        });
    }

}
