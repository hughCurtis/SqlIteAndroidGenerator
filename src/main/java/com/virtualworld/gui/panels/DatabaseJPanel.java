/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.gui.panels;

import com.virtualworld.dao.RelationJpaController;
import com.virtualworld.entities.Relation;
import com.virtualworld.generic.tabbedpanel.ButtonTabComponent;
import com.virtualworld.generic.tabbedpanel.listeners.TabCloseListener;
import com.virtualworld.generic.tabbedpanel.listeners.TabRenameListener;
import com.virtualworld.gui.TestJFrame;
import com.virtualworld.mediator.RelationFkMediator;
import com.virtualworld.mediator.listeners.MediatorEventListener;
import com.virtualworld.model.exceptions.NonExistantValueException;
import com.virtualworld.tree.DatabaseTreeModel;
import com.virtualworld.tree.RelationTreeNode;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

/**
 *
 * @author Ulrich TIAYO <tiayo.pro@gmail.com>
 */
public class DatabaseJPanel extends javax.swing.JPanel
        implements TabCloseListener, TabRenameListener, MediatorEventListener {

    private final RelationJpaController relationJpaController = new RelationJpaController();
    private final RelationFkMediator mediator = RelationFkMediator.getInstance();
    private String dataBaseName = "UlrichDb";
    private final String licence = "/*\n"
            + " * Ce document intitulé «  " + dataBaseName + "Contract.java" + "  » du package sqlitegenerator proposé par HCurti$ et Ulrich TIAYO\n"
            + " * issus du SEED(www.seed-innov.com) est mis à disposition sous les termes de la licence Creative Commons. \n"
            + " * Vous pouvez copier, modifier des copies de cette page, dans les conditions fixées par la licence, \n"
            + " * tant que cette note apparaît clairement.\n"
            + " */\n\n";
    
    /**
     * Creates new form DatabaseJPanel
     */
    public DatabaseJPanel() {
        initComponents();
        postInit();
    }

    /**
     * Creates new form DatabaseJPanel
     * @param dataBaseName
     */
    public DatabaseJPanel(String dataBaseName) {
        initComponents();
        this.dataBaseName = dataBaseName;
        postInit();
    }

    public  String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    private void postInit() {
        mediator.addMediatorEventListener(this);
        dbNameJLabel.setText(dataBaseName);

        paneJTabbedPane.removeAll();
//        for (int i = 0; i < 4; i++) {
        RelationJPanel relation = new RelationJPanel();
        String title = Relation.DEFAULT_RELATION_NAME + " " + relation.getId();
        paneJTabbedPane.add((String) null, relation);
        initTabComponent(0, title, relation.getId());
//        }
        paneJTabbedPane.add("", new AddDetailsJPanel());
        JButton bouton = new JButton("+");
        bouton.addActionListener((ActionEvent e) -> {
            addTabAddAction();
        });
        bouton.setBorderPainted(false);
        bouton.setContentAreaFilled(false);
        bouton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        paneJTabbedPane.setTabComponentAt(1, bouton);
        paneJTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    private void initTabComponent(int index, String tabTitle, int id) {
        ButtonTabComponent btc = new ButtonTabComponent(tabTitle, id);
        btc.addTabCloseEventListener(this);
        btc.addTabRenameEventListener(this);
//        btc.setComponentPopupMenu(jPopupMenu1);
        paneJTabbedPane.setTabComponentAt(index, btc);
    }

    public void genererContract() throws Exception {
        FileWriter file;
        file = new FileWriter(new File(dataBaseName + "Contract.java"));
        //entête du fichier Contract
        file.write(licence);
        file.write(generateContractContent());
        file.close();
    }
    public void genererOpenHelper() throws Exception {
        FileWriter file;
        file = new FileWriter(new File(dataBaseName + "DBOpenHelper.java"));
        //entête du fichier Contract
        file.write(licence);
        file.write(generateOpenHelperContent());
        file.close();
    }

    public String generateContractContent() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        String str = "\npackage "
                + "Ajouter votre package ici"
                + "\nimport android.provider.BaseColumns;\n"
                + "import android.text.format.Time;\n"
                + "\n"
                + "/**\n"
                + " * Created by SqlIteCodeGenerator for Android on " + sdf.format(Calendar.getInstance().getTime()) + ".\n"
                + " */\n"
                + "public class " + dataBaseName + "Contract {\n"
                + "\n"
                + "    // To make it easy to query for the exact date, we normalize all dates that go into\n"
                + "    // the database to the start of the the Julian day at UTC.\n"
                + "    public static long normalizeDate(long startDate) {\n"
                + "        // normalize the start date to the beginning of the (UTC) day\n"
                + "        Time time = new Time();\n"
                + "        time.setToNow();\n"
                + "        int julianDay = Time.getJulianDay(startDate, time.gmtoff);\n"
                + "        return time.setJulianDay(julianDay);\n"
                + "    }\n\n";

        List<Relation> relations = relationJpaController.findRelationEntities();
        for (int i = 0; i < relations.size(); i++) {
            str += relations.get(i).toStringContract(1);
        }

        return str + "\n}";
    }

    public String generateOpenHelperContent(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        String str = "\npackage "
                + "Ajouter votre package ici"
                + "import android.content.Context;\n" +
"import android.database.sqlite.SQLiteDatabase;\n" +
"import android.database.sqlite.SQLiteOpenHelper;\n" +
"\n" +
"/**\n" +
" * Created by SqlIteCodeGenerator for Android on " + sdf.format(Calendar.getInstance().getTime()) + ".\n"+
" */\n" +
"public class "+dataBaseName+"DBOpenHelper extends SQLiteOpenHelper {\n" +
"\n" +
"\n" +
"    // If you change the database schema, you must increment the database version.\n" +
"    public static final int DATABASE_VERSION = 1;\n" +
"\n" +
"    public static final String DATABASE_NAME = \""+dataBaseName+"\";\n" +
"\n" +
"    public "+dataBaseName+"DBOpenHelper(Context context) {\n" +
"        super(context, DATABASE_NAME, null, DATABASE_VERSION);\n" +
"    }\n" +
"\n" +
"    @Override\n" +
"    public void onCreate(SQLiteDatabase sqLiteDatabase) {";
        
        
        List<Relation> relations = relationJpaController.findRelationEntities();
        for (int i = 0; i < relations.size(); i++) {
            str += relations.get(i).toStringOpenHelper(1,dataBaseName);
        }
        
        
        return str;
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        paneJTabbedPane = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        attributesJTree = new javax.swing.JTree();
        genererJButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        dbNameJLabel = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));

        paneJTabbedPane.setBackground(new java.awt.Color(250, 250, 250));
        paneJTabbedPane.setAutoscrolls(true);
        paneJTabbedPane.setPreferredSize(new java.awt.Dimension(700, 441));
        paneJTabbedPane.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                paneJTabbedPaneComponentAdded(evt);
            }
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                paneJTabbedPaneComponentRemoved(evt);
            }
        });

        attributesJTree.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        attributesJTree.setModel(getRootTreeModel());
        jScrollPane1.setViewportView(attributesJTree);

        genererJButton.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        genererJButton.setText("Générer");
        genererJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genererJButtonActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        dbNameJLabel.setFont(new java.awt.Font("DialogInput", 0, 24)); // NOI18N
        dbNameJLabel.setForeground(new java.awt.Color(255, 255, 255));
        dbNameJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dbNameJLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dbNameJLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(genererJButton, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(paneJTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(genererJButton))
                    .addComponent(paneJTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void paneJTabbedPaneComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_paneJTabbedPaneComponentAdded
        if (evt.getChild() instanceof RelationJPanel) {
            RelationJPanel pan = (RelationJPanel) evt.getChild();
            RelationTreeNode node = new RelationTreeNode(pan);
            DatabaseTreeModel model = (DatabaseTreeModel) attributesJTree.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
            root.add(node);
            model.reload();
        }
    }//GEN-LAST:event_paneJTabbedPaneComponentAdded

    private void paneJTabbedPaneComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_paneJTabbedPaneComponentRemoved
        // TODO add your handling code here:
        if (evt.getChild() instanceof RelationJPanel) {
            RelationJPanel pan = (RelationJPanel) evt.getChild();
            DatabaseTreeModel model = (DatabaseTreeModel) attributesJTree.getModel();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) model.getRoot();
            node = (DefaultMutableTreeNode) node.getFirstChild();
            while (node != null) {
                if (node.getUserObject().equals(pan)) {
                    model.removeNodeFromParent(node);
                    return;
                }
                node = node.getNextSibling();
            }
        }
    }//GEN-LAST:event_paneJTabbedPaneComponentRemoved

    private void genererJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genererJButtonActionPerformed
        try {
            genererContract();
            genererOpenHelper();
        } catch (Exception ex) {
            Logger.getLogger(DatabaseJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_genererJButtonActionPerformed

    private TreeModel getRootTreeModel() {
        return new DatabaseTreeModel(new DefaultMutableTreeNode(dataBaseName));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree attributesJTree;
    private javax.swing.JLabel dbNameJLabel;
    private javax.swing.JButton genererJButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane paneJTabbedPane;
    // End of variables declaration//GEN-END:variables

    public void addTabAddAction() {
        int nbre = paneJTabbedPane.getTabCount();
        RelationJPanel relation = new RelationJPanel();
        paneJTabbedPane.insertTab("Titre", null, relation, null, nbre - 1);
        String relationName = Relation.DEFAULT_RELATION_NAME + " " + relation.getId();
        initTabComponent(nbre - 1, relationName, relation.getId());
    }
    
    @Override
    public void onTabClose(int elementId) {
        int confirm = JOptionPane.showConfirmDialog(this, "Voulez vous vraiment supprimer la relation?");
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        int taille = paneJTabbedPane.getTabCount();
//        ButtonTabComponent buttonTabComponent;
        RelationJPanel panel;
        for (int i = 0; i < taille; i++) {
            panel = (RelationJPanel) paneJTabbedPane.getComponentAt(i);
            if (panel == null) {
                continue;
            }
            if (panel.getId() == elementId) {
                try {
                    panel.delete();
                } catch (NonExistantValueException ex) {
                    Logger.getLogger(TestJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                paneJTabbedPane.remove(i);
                break;
            }
        }
    }

    @Override
    public void onTabRename(int elementId) {
        Relation relation = relationJpaController.findRelation(elementId);
        String nom = JOptionPane.showInputDialog(this, "Veuillez fournir le nouveau nom", relation.getName());
        if (nom == null) {
            return;
        }
        for (Relation r : relationJpaController.findRelationEntities()) {
            if (r.getName().equals(nom) && r.getId() != elementId) {
                JOptionPane.showMessageDialog(this, "Une autre relation porte déjà ce nom", "Duplication", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        relation.setName(nom);

        int taille = paneJTabbedPane.getTabCount();
        RelationJPanel panel;
        for (int i = 0; i < taille; i++) {
            panel = (RelationJPanel) paneJTabbedPane.getComponentAt(i);
            if (panel == null) {
                continue;
            }
            if (panel.getId() == elementId) {
//                paneJTabbedPane.setTitleAt(i, nom);
                ButtonTabComponent btc = (ButtonTabComponent) paneJTabbedPane.getTabComponentAt(i);
                btc.rename(nom);
                panel.onRelationChange();
                break;
            }
        }
        DatabaseTreeModel model = (DatabaseTreeModel) attributesJTree.getModel();
        model.reload();
    }

    @Override
    public void onRelationChange() {
        DatabaseTreeModel model = (DatabaseTreeModel) attributesJTree.getModel();
        model.reload();
        int nbre = attributesJTree.getRowCount();
        for (int i = 0; i < nbre; i++) {
            if (!attributesJTree.isExpanded(i)) {
                attributesJTree.expandRow(i);
            }
        }
        attributesJTree.repaint();
    }
    
}
