package com.dogiloki.minecraftserver.infraestructure.ui;

import com.dogiloki.minecraftserver.application.dao.Properties;
import com.dogiloki.minecraftserver.core.services.Instance;
import com.dogiloki.minecraftserver.core.services.World;
import com.dogiloki.minecraftserver.infraestructure.ui.components.ServerPanel;
import com.dogiloki.multitaks.Function;
import com.dogiloki.multitaks.Watcher;
import com.dogiloki.multitaks.datastructure.tree.TreeNodeWrapper;
import com.dogiloki.multitaks.directory.ConfigFile;
import com.dogiloki.multitaks.directory.DirectoryList;
import com.dogiloki.multitaks.directory.Storage;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author dogi_
 */


public final class FormMain extends javax.swing.JFrame {
    
    private Instance selected_instance=null;
    private Watcher watcher;

    public FormMain(){
        initComponents();
        ConfigFile.load(Properties.class);
        this.split_panel.setDividerLocation(200);
        this.setLocationRelativeTo(null);
        this.loadInstances();
    }
    
    public void loadInstances(){
        DirectoryList folders=new Storage(Properties.folders.instances_folder,DirectoryType.FOLDER).notExists().listFolders();
        DefaultMutableTreeNode root=new DefaultMutableTreeNode("Instancias");
        DefaultTreeModel model=new DefaultTreeModel(root);
        this.instances_tree.setModel(model);
        Path folder;
        while(folders.hasNext()){
            folder=folders.next();
            Instance ins=new Instance(folder.toString());
            DefaultMutableTreeNode node_ins=new TreeNodeWrapper(ins,folder.getFileName().toString());
            ins.worlds.items().iterate((name,world)->{
                DefaultMutableTreeNode node_world=new TreeNodeWrapper(world,name);
                node_ins.add(node_world);
            });
            root.add(node_ins);
        }
        model.reload();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        instances_root_popup = new javax.swing.JPopupMenu();
        new_instance_btn = new javax.swing.JMenuItem();
        instances_item_popup = new javax.swing.JPopupMenu();
        edit_instance_btn = new javax.swing.JMenuItem();
        new_world_instance_btn = new javax.swing.JMenuItem();
        delete_instance_btn = new javax.swing.JMenuItem();
        instances_world_popup = new javax.swing.JPopupMenu();
        start_world_btn = new javax.swing.JMenuItem();
        split_panel = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        instances_tree = new javax.swing.JTree();
        panel_server = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        text_serve_log = new javax.swing.JTextArea();

        new_instance_btn.setText("Nuevo");
        new_instance_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new_instance_btnActionPerformed(evt);
            }
        });
        instances_root_popup.add(new_instance_btn);

        edit_instance_btn.setText("Editar Instancia");
        edit_instance_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_instance_btnActionPerformed(evt);
            }
        });
        instances_item_popup.add(edit_instance_btn);

        new_world_instance_btn.setText("Nuevo Mundo");
        new_world_instance_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new_world_instance_btnActionPerformed(evt);
            }
        });
        instances_item_popup.add(new_world_instance_btn);

        delete_instance_btn.setText("Eliminar");
        delete_instance_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_instance_btnActionPerformed(evt);
            }
        });
        instances_item_popup.add(delete_instance_btn);

        start_world_btn.setText("Iniciar Servidor");
        start_world_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                start_world_btnActionPerformed(evt);
            }
        });
        instances_world_popup.add(start_world_btn);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        instances_tree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                instances_treeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(instances_tree);

        split_panel.setLeftComponent(jScrollPane2);

        panel_server.setLayout(new java.awt.GridLayout(1, 0));
        split_panel.setRightComponent(panel_server);

        text_serve_log.setColumns(20);
        text_serve_log.setRows(5);
        text_serve_log.setWrapStyleWord(true);
        jScrollPane1.setViewportView(text_serve_log);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
            .addComponent(split_panel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(split_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
    }//GEN-LAST:event_formWindowClosing

    private void instances_treeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_instances_treeMouseClicked
        TreePath path=this.instances_tree.getPathForLocation(evt.getX(),evt.getY());
        if(path==null){
            return;
        }
        DefaultMutableTreeNode node=(DefaultMutableTreeNode)path.getLastPathComponent();
        DefaultMutableTreeNode parent=(DefaultMutableTreeNode)node.getParent();
        this.instances_tree.setSelectionPath(path);
        if(SwingUtilities.isRightMouseButton(evt)){
            if(node==this.instances_tree.getModel().getRoot()){
                this.instances_root_popup.show(this.instances_tree,evt.getX(),evt.getY());
            }else if(parent!=null && parent==node.getRoot()){
                this.instances_item_popup.show(this.instances_tree,evt.getX(),evt.getY());
                this.selected_instance=(Instance)node.getUserObject();
            }
        }else{
            if(node.isLeaf()){
                this.selected_instance=(Instance)parent.getUserObject();
                Function.setPanel(this.panel_server,new ServerPanel(this,this.selected_instance,(World)node.getUserObject()));
            }
            
        }
    }//GEN-LAST:event_instances_treeMouseClicked

    private void new_instance_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_new_instance_btnActionPerformed
        new InstanceDialog(this,true,null).setVisible(true);
    }//GEN-LAST:event_new_instance_btnActionPerformed

    private void new_world_instance_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_new_world_instance_btnActionPerformed
        if(this.selected_instance==null) return;
        String name=JOptionPane.showInputDialog(null,"Ingresa el nombre del Mundo:","Crear mundo",JOptionPane.QUESTION_MESSAGE);
        if(name==null) return;
        this.selected_instance.worlds.createWorld(name);
    }//GEN-LAST:event_new_world_instance_btnActionPerformed

    private void delete_instance_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_instance_btnActionPerformed
        
    }//GEN-LAST:event_delete_instance_btnActionPerformed

    private void edit_instance_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_instance_btnActionPerformed
        if(this.selected_instance==null) return;
        new InstanceDialog(this,true,this.selected_instance).setVisible(true);
    }//GEN-LAST:event_edit_instance_btnActionPerformed

    private void start_world_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_world_btnActionPerformed
        
    }//GEN-LAST:event_start_world_btnActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                new FormMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem delete_instance_btn;
    private javax.swing.JMenuItem edit_instance_btn;
    private javax.swing.JPopupMenu instances_item_popup;
    private javax.swing.JPopupMenu instances_root_popup;
    private javax.swing.JTree instances_tree;
    private javax.swing.JPopupMenu instances_world_popup;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem new_instance_btn;
    private javax.swing.JMenuItem new_world_instance_btn;
    private javax.swing.JPanel panel_server;
    private javax.swing.JSplitPane split_panel;
    private javax.swing.JMenuItem start_world_btn;
    private javax.swing.JTextArea text_serve_log;
    // End of variables declaration//GEN-END:variables
}
