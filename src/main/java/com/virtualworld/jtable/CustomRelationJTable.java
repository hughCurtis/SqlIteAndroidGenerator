/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.jtable;

import com.virtualworld.dao.AttributJpaController;
import com.virtualworld.entities.Attribut;
import com.virtualworld.entities.DbTypes;
import com.virtualworld.entities.Relation;
import com.virtualworld.jtable.listeners.PKSelectionChangeListener;
import com.virtualworld.jtable.listeners.PKSelectionEvent;
import com.virtualworld.jtable.models.BooleanCellRenderer;
import com.virtualworld.jtable.models.CustomTableRenderer;
import com.virtualworld.jtable.models.RelationTableModel;
import com.virtualworld.model.Database;
import com.virtualworld.model.exceptions.NonExistantValueException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 *
 * @author Ulrich
 */
public class CustomRelationJTable extends JTable implements PKSelectionChangeListener, PKSelectionEvent {

    private final AttributJpaController attributJpaController = new AttributJpaController();
    private final List<PKSelectionChangeListener> selectionChangeListeners = new ArrayList<>();
    private RelationTableModel model;
    private Relation relation;

//    public CustomRelationJTable() {
//        super();
//        try {
//            initComponent();
//        } catch (NonExistantValueException ex) {
//            Logger.getLogger(CustomRelationJTable.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public CustomRelationJTable(Relation relation) {
        super();
        this.relation = relation;
        try {
            initComponent();
        } catch (NonExistantValueException ex) {
            Logger.getLogger(CustomRelationJTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initComponent() throws NonExistantValueException {
        List<Attribut> relations = new ArrayList<>();
        Attribut at;
        for (int i = 0; i < 3; i++) {
            at = new Attribut();
            char nom = (char) (i+'a');
            at.setName(nom + "");
            relations.add(at);
            at.setRelation(relation);
            attributJpaController.create(at);
        }
        model = new RelationTableModel(relations);
        model.addPKSelectionChangeListener(this);
        setModel(model);

        JComboBox combo = new JComboBox();
        DbTypes values[] = DbTypes.values();
        for (DbTypes type : values) {
            combo.addItem(type);
        }
        getColumn("Type").setCellEditor(new DefaultCellEditor(combo));

        setRowHeight(30);
        setFont(new java.awt.Font("Segoe UI", 0, 16));

        setDefaultRenderer(Object.class, new CustomTableRenderer());
        setDefaultRenderer(Boolean.class, new BooleanCellRenderer());

        JComboBox ecombo2 = new JComboBox();
        getColumn("Clé étrangère").setCellEditor(new DefaultCellEditor(ecombo2));
    }

    @Override
    public void onPKSelectionChange(Attribut attribut) {
        updatePKSelectionChangeListeners(attribut);
    }

    @Override
    public void addPKSelectionChangeListener(PKSelectionChangeListener pKSelectionChangeListener) {
        selectionChangeListeners.add(pKSelectionChangeListener);
    }

    @Override
    public void updatePKSelectionChangeListeners(Attribut attribut) {
        selectionChangeListeners.forEach((pkl) -> {
            pkl.onPKSelectionChange(attribut);
        });
    }

    @Override
    public void removePKSelectionChangeListener(PKSelectionChangeListener pKSelectionChangeListener) {
        selectionChangeListeners.remove(pKSelectionChangeListener);
    }

    public void reset() {
        model.fireTableDataChanged();
    }
}
