/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.jtable.models;

import com.virtualworld.dao.entities.DbTypes;
import com.virtualworld.dao.entities.Attribut;
import com.virtualworld.jtable.listeners.PKSelectionChangeListener;
import com.virtualworld.jtable.listeners.PKSelectionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ulrich
 */
public class RelationTableModel extends AbstractTableModel implements PKSelectionEvent {

    private final List<PKSelectionChangeListener> selectionChangeListeners = new ArrayList<>();

    private final String[] titles = {"Nom", "Type", "Nullable", "Clé primaire", "Clé étrangère", "Commentaire"};
    private final Class[] classes = {String.class, DbTypes.class, Boolean.class, Boolean.class, Attribut.class, String.class};
    private final int NOM_COL = 0;
    private final int TYPE_COL = 1;
    private final int NULLABLE_COL = 2;
    private final int PK_COL = 3;
    private final int FK_COL = 4;
    private final int COMMENT_COL = 5;
    private final List<Attribut> attributs;

    private int pkRow = -1;

    public RelationTableModel() {
        attributs = new ArrayList<>();
    }

    public RelationTableModel(List<Attribut> relations) {
        this.attributs = relations;
    }

    @Override
    public int getRowCount() {
        return attributs.size();
    }

    @Override
    public int getColumnCount() {
        return titles.length;
    }

    @Override
    public String getColumnName(int column) {
        return titles[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return classes[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case NOM_COL:
                return attributs.get(rowIndex).getName();
            case TYPE_COL:
                return attributs.get(rowIndex).getType();
            case NULLABLE_COL:
                return attributs.get(rowIndex).isNullable();
            case PK_COL:
                return attributs.get(rowIndex).isPk();
            case FK_COL:
                if (attributs.get(rowIndex).getFkOn() != null && !attributs.get(rowIndex).getFkOn().isPk()) {
                    attributs.get(rowIndex).setFkOn(null);
                }
                return attributs.get(rowIndex).getFkOn();
            case COMMENT_COL:
                return attributs.get(rowIndex).getCommentaire();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case NOM_COL:
                attributs.get(rowIndex).setName((String) aValue);
//                if (attributs.get(rowIndex).isPk()) {
                updatePKSelectionChangeListeners(attributs.get(rowIndex));
//                }
                break;
            case TYPE_COL:
                attributs.get(rowIndex).setType((DbTypes) aValue);
                break;
            case NULLABLE_COL:
                attributs.get(rowIndex).setNullable((Boolean) aValue);
                break;
            case PK_COL:
                Boolean value = (Boolean) aValue;
                if (value) {
                    pkRow = rowIndex;
                    attributs.get(rowIndex).setNullable(false);
                } else {
                    pkRow = -1;
                }
                attributs.get(rowIndex).setPk(value);
                updatePKSelectionChangeListeners(attributs.get(rowIndex));
                break;
            case FK_COL:
                attributs.get(rowIndex).setFkOn((Attribut) aValue);
                break;
            case COMMENT_COL:
                attributs.get(rowIndex).setCommentaire((String) aValue);
                break;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (pkRow == -1 || (columnIndex != PK_COL && columnIndex != NULLABLE_COL)) {
            return true;
        }
        if (columnIndex == NULLABLE_COL) {
            return rowIndex != pkRow;
        }
        return rowIndex == pkRow;
    }

    public List<Attribut> getAttributs() {
        return attributs;
    }

    public void addAttribute(Attribut attribut) {
        attributs.add(attribut);
        fireTableDataChanged();
    }

    public void removeAttribute(int index) {
        attributs.remove(index);
        fireTableDataChanged();
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

}
