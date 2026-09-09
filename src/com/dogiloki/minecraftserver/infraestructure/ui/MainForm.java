package com.dogiloki.minecraftserver.infraestructure.ui;

import com.dogiloki.minecraftserver.MainLaucher;
import com.dogiloki.minecraftserver.application.dao.ClientInstance;
import com.dogiloki.minecraftserver.application.dao.Properties;
import com.dogiloki.minecraftserver.core.services.Instance;
import com.dogiloki.minecraftserver.core.services.MinecraftClient;
import com.dogiloki.minecraftserver.core.services.World;
import com.dogiloki.minecraftserver.infraestructure.ui.components.ClientPanel;
import com.dogiloki.minecraftserver.infraestructure.ui.components.ServerPanel;
import com.dogiloki.minecraftserver.infraestructure.utils.ConfirmHelper;
import com.dogiloki.minecraftserver.infraestructure.utils.LogTextPane;
import com.dogiloki.multitaks.Function;
import com.dogiloki.multitaks.Watcher;
import com.dogiloki.multitaks.database.record.RecordList;
import com.dogiloki.multitaks.datastructure.tree.TreeNodeWrapper;
import com.dogiloki.multitaks.directory.DirectoryList;
import com.dogiloki.multitaks.directory.Storage;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.dogiloki.multitaks.logger.AppLogger;
import com.dogiloki.multitaks.updater.UpdaterConfig;
import com.dogiloki.multitaks.updater.UpdaterDialog;
import java.nio.file.Path;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author dogi_
 */


public final class MainForm extends javax.swing.JFrame{
    
    private Instance selected_instance=null;
    private Watcher watcher;
    private UpdaterConfig update_cfg=new UpdaterConfig(MainLaucher.class).builder();

    public MainForm(){
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public void run(){
        this.split_panel.setDividerLocation(200);
        this.loadServerInstances();
        this.loadClientInstances();
        this.setTitle(this.update_cfg.project+" - "+this.update_cfg.version);
    }
    
    public LogTextPane getLogInstancePane(){
        return this.log_instance_pane;
    }
    
    public void loadServerInstances(){
        try{
            AppLogger.debug("Cargando instancias");
            DirectoryList folders=new Storage(Properties.folders.instances_folder,DirectoryType.FOLDER)
                    .notExists()
                    .listFolders()
                    .filter((path)->{
                        return new Storage(path.toString()+"/"+Properties.files.instance_cfg).exists();
                    });
            DefaultMutableTreeNode root=new DefaultMutableTreeNode("Instancias");
            DefaultTreeModel model=new DefaultTreeModel(root);
            this.server_instances_tree.setModel(model);
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
        }catch(Exception ex){
            AppLogger.debug("Error al cargar instancias").exception(ex);
        }
    }
    
    public void loadClientInstances(){
        try{
            AppLogger.debug("Cargando clientes");
            DefaultMutableTreeNode root=new DefaultMutableTreeNode("Clientes");
            DefaultTreeModel model=new DefaultTreeModel(root);
            this.client_instances_tree.setModel(model);
            RecordList<ClientInstance> clients=new ClientInstance().all();
            ClientInstance client;
            while((client=clients.first())!=null){
                MinecraftClient mc=new MinecraftClient(client);
                DefaultMutableTreeNode node_ins=new TreeNodeWrapper(mc,mc.getName());
                mc.worlds.items().iterate((name,world)->{
                    DefaultMutableTreeNode node_world=new TreeNodeWrapper(world,name);
                    node_ins.add(node_world);
                });
                root.add(node_ins);
            }
            model.reload();
        }catch(Exception ex){
            AppLogger.debug("Error al cargar clientes").exception(ex);
        }
    }
    
    public void resetSelection(){
        AppLogger.debug("Resetear selección de Instancia");
        this.loadServerInstances();
        this.selected_instance=null;
        this.server_panel.removeAll();
        this.server_panel.updateUI();
    }
    
    public void newInstance(){
        try{
            new InstanceDialog(this,true,null).setVisible(true);
            this.resetSelection();
        }catch(Exception ex){
            AppLogger.debug("Error en Nueva Instancia").exception(ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        instances_root_popup = new javax.swing.JPopupMenu();
        new_instance_btn_menu = new javax.swing.JMenuItem();
        instances_item_popup = new javax.swing.JPopupMenu();
        edit_instance_btn = new javax.swing.JMenuItem();
        new_world_instance_btn = new javax.swing.JMenuItem();
        delete_instance_btn = new javax.swing.JMenuItem();
        instances_world_popup = new javax.swing.JPopupMenu();
        start_world_btn = new javax.swing.JMenuItem();
        split_panel = new javax.swing.JSplitPane();
        server_panel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        server_instances_tree = new javax.swing.JTree();
        jScrollPane3 = new javax.swing.JScrollPane();
        client_instances_tree = new javax.swing.JTree();
        updater_btn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        log_instance_pane = new com.dogiloki.minecraftserver.infraestructure.utils.LogTextPane();
        new_instance_btn = new javax.swing.JButton();
        new_client_btn = new javax.swing.JButton();

        new_instance_btn_menu.setText("Nueva Instancia");
        new_instance_btn_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new_instance_btn_menuActionPerformed(evt);
            }
        });
        instances_root_popup.add(new_instance_btn_menu);

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
        setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        server_panel.setLayout(new java.awt.GridLayout(1, 0));
        split_panel.setRightComponent(server_panel);

        server_instances_tree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                server_instances_treeMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(server_instances_tree);

        jTabbedPane1.addTab("tab1", jScrollPane2);

        client_instances_tree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                client_instances_treeMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(client_instances_tree);

        jTabbedPane1.addTab("tab1", jScrollPane3);

        split_panel.setLeftComponent(jTabbedPane1);

        updater_btn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        updater_btn.setText("Actualizar");
        updater_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updater_btnActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(log_instance_pane);

        new_instance_btn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        new_instance_btn.setText("Nueva Instancia");
        new_instance_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new_instance_btnActionPerformed(evt);
            }
        });

        new_client_btn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        new_client_btn.setText("Agregar Cliente");
        new_client_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new_client_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(split_panel)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(new_instance_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(new_client_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 646, Short.MAX_VALUE)
                .addComponent(updater_btn)
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updater_btn)
                    .addComponent(new_instance_btn)
                    .addComponent(new_client_btn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(split_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
    }//GEN-LAST:event_formWindowClosing

    private void new_instance_btn_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_new_instance_btn_menuActionPerformed
        this.newInstance();
    }//GEN-LAST:event_new_instance_btn_menuActionPerformed

    private void new_world_instance_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_new_world_instance_btnActionPerformed
        if(this.selected_instance==null) return;
        String name=JOptionPane.showInputDialog(null,"Ingresa el nombre del Mundo:","Crear mundo",JOptionPane.QUESTION_MESSAGE);
        if(name==null) return;
        this.selected_instance.worlds.createWorld(name);
        this.resetSelection();
    }//GEN-LAST:event_new_world_instance_btnActionPerformed

    private void delete_instance_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_instance_btnActionPerformed
        ConfirmHelper.runWithConfirm("¿Eliminar instancia? No elimina la carpeta",()->{
            this.selected_instance.cfg.delete();
            this.resetSelection();
        });
    }//GEN-LAST:event_delete_instance_btnActionPerformed

    private void edit_instance_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_instance_btnActionPerformed
        if(this.selected_instance==null) return;
        new InstanceDialog(this,true,this.selected_instance).setVisible(true);
        this.resetSelection();
    }//GEN-LAST:event_edit_instance_btnActionPerformed

    private void start_world_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_world_btnActionPerformed
        this.resetSelection();
    }//GEN-LAST:event_start_world_btnActionPerformed

    private void server_instances_treeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_server_instances_treeMousePressed
        TreePath path=this.server_instances_tree.getPathForLocation(evt.getX(),evt.getY());
        if(path==null){
            return;
        }
        DefaultMutableTreeNode node=(DefaultMutableTreeNode)path.getLastPathComponent();
        DefaultMutableTreeNode parent=(DefaultMutableTreeNode)node.getParent();
        this.server_instances_tree.setSelectionPath(path);
        if(SwingUtilities.isRightMouseButton(evt)){
            if(node==this.server_instances_tree.getModel().getRoot()){
                this.instances_root_popup.show(this.server_instances_tree,evt.getX(),evt.getY());
            }else if(parent!=null && parent==node.getRoot()){
                this.instances_item_popup.show(this.server_instances_tree,evt.getX(),evt.getY());
                this.selected_instance=(Instance)node.getUserObject();
            }
        }else{
            if(node.isLeaf()){
                this.selected_instance=(Instance)parent.getUserObject();
                Function.setPanel(this.server_panel,new ServerPanel(this,this.selected_instance,(World)node.getUserObject()));
            }    
        }
    }//GEN-LAST:event_server_instances_treeMousePressed

    private void updater_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updater_btnActionPerformed
        new UpdaterDialog(this,true,".",this.update_cfg).setVisible(true);
    }//GEN-LAST:event_updater_btnActionPerformed

    private void new_instance_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_new_instance_btnActionPerformed
        this.newInstance();
    }//GEN-LAST:event_new_instance_btnActionPerformed

    private void client_instances_treeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_client_instances_treeMousePressed
        TreePath path=this.client_instances_tree.getPathForLocation(evt.getX(),evt.getY());
        if(path==null){
            return;
        }
        DefaultMutableTreeNode node=(DefaultMutableTreeNode)path.getLastPathComponent();
        DefaultMutableTreeNode parent=(DefaultMutableTreeNode)node.getParent();
        this.client_instances_tree.setSelectionPath(path);
        if(SwingUtilities.isRightMouseButton(evt)){
            if(node==this.client_instances_tree.getModel().getRoot()){
                //this.instances_root_popup.show(this.server_instances_tree,evt.getX(),evt.getY());
            }else if(parent!=null && parent==node.getRoot()){
                //this.instances_item_popup.show(this.server_instances_tree,evt.getX(),evt.getY());
                //this.selected_instance=(Instance)node.getUserObject();
            }
        }else{
            if(node.isLeaf()){
                //this.selected_instance=(Instance)parent.getUserObject();
                Function.setPanel(this.server_panel,new ClientPanel(this,(MinecraftClient)parent.getUserObject(),(World)node.getUserObject()));
            }    
        }
    }//GEN-LAST:event_client_instances_treeMousePressed

    private void new_client_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_new_client_btnActionPerformed
        String path=Storage.selectFolder();
        if(path!=null){
            ClientInstance client_instance=new ClientInstance();
            client_instance.path=path;
            client_instance.save();
            this.loadClientInstances();
        }
        
    }//GEN-LAST:event_new_client_btnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree client_instances_tree;
    private javax.swing.JMenuItem delete_instance_btn;
    private javax.swing.JMenuItem edit_instance_btn;
    private javax.swing.JPopupMenu instances_item_popup;
    private javax.swing.JPopupMenu instances_root_popup;
    private javax.swing.JPopupMenu instances_world_popup;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.dogiloki.minecraftserver.infraestructure.utils.LogTextPane log_instance_pane;
    private javax.swing.JButton new_client_btn;
    private javax.swing.JButton new_instance_btn;
    private javax.swing.JMenuItem new_instance_btn_menu;
    private javax.swing.JMenuItem new_world_instance_btn;
    private javax.swing.JTree server_instances_tree;
    private javax.swing.JPanel server_panel;
    private javax.swing.JSplitPane split_panel;
    private javax.swing.JMenuItem start_world_btn;
    private javax.swing.JButton updater_btn;
    // End of variables declaration//GEN-END:variables
}
