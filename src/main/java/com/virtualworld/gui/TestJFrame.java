/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.gui;

import com.virtualworld.dao.ctrl.DataBaseJpaController;
import com.virtualworld.dao.entities.DataBase;
import com.virtualworld.gui.panels.DatabaseJPanel;
import java.util.List;
import javax.swing.JMenuItem;

/**
 *
 * @author Ulrich
 */
public class TestJFrame extends javax.swing.JFrame {

    private final DataBaseJpaController dataBaseJpaController = new DataBaseJpaController();

    /**
     * Creates new form TestJFrame
     */
    public TestJFrame() {
        initComponents();
        List<DataBase> dbs = dataBaseJpaController.findDataBaseEntities();
        if (dbs.isEmpty()) {
            DataBase dbe = new DataBase();
            dbe.setDatabaseName("DataBaseName");
            dataBaseJpaController.create(dbe);
            DatabaseJPanel db = new DatabaseJPanel(dbe);
            addDatabase(db);
        } else {
            dbs.forEach((db) -> {
                addDatabase(new DatabaseJPanel(db));
            });
        }
    }

    private void addDatabase(DatabaseJPanel dbPanel) {
        databaseLayoutJPanel.add(dbPanel);
        JMenuItem item = new JMenuItem(dbPanel.getDataBaseName());
        item.setFont(new java.awt.Font("Segoe UI", 0, 16));
        bdJMenu.add(item);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        databaseLayoutJPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fichierJMenu = new javax.swing.JMenu();
        bdJMenu = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");
        jPopupMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));

        databaseLayoutJPanel.setLayout(new java.awt.GridLayout(1, 1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(databaseLayoutJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(databaseLayoutJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addContainerGap())
        );

        fichierJMenu.setMnemonic('f');
        fichierJMenu.setText("Fichier");
        fichierJMenu.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuBar1.add(fichierJMenu);

        bdJMenu.setMnemonic('b');
        bdJMenu.setText("Bases de données");
        bdJMenu.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuBar1.add(bdJMenu);

        jMenu1.setMnemonic('a');
        jMenu1.setText("Aide");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            TestJFrame tf = new TestJFrame();
            tf.setLocationRelativeTo(null);
            tf.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu bdJMenu;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel databaseLayoutJPanel;
    private javax.swing.JMenu fichierJMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    // End of variables declaration//GEN-END:variables

}
