/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.tree;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Ulrich TIAYO <tiayo.pro@gmail.com>
 */
public class DatabaseTreeModel extends DefaultTreeModel {

    public DatabaseTreeModel(TreeNode root) {
        super(root);
    }

    @Override
    public void reload() {
        TreeNode rootNode = (TreeNode) getRoot();
        RelationTreeNode relationNode;
        int nbre = getChildCount(root);
        for (int i = 0; i < nbre; i++) {
            relationNode = (RelationTreeNode) getChild(rootNode, i);
            relationNode.reload();
        }
        super.reload();
    }

}
