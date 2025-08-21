package com.dogiloki.minecraftserver.infraestructure.ui.components;

import com.dogiloki.minecraftserver.application.dao.Properties;
import com.dogiloki.minecraftserver.core.services.Instance;
import com.dogiloki.minecraftserver.core.services.MinecraftServer;
import com.dogiloki.minecraftserver.core.services.Snapshot;
import com.dogiloki.minecraftserver.core.services.World;
import com.dogiloki.minecraftserver.core.services.Worlds;
import com.dogiloki.minecraftserver.infraestructure.utils.ListElementWrapper;
import com.dogiloki.multitaks.directory.DirectoryList;
import com.dogiloki.multitaks.directory.Storage;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.dogiloki.multitaks.download.DownloadDialog;
import com.dogiloki.multitaks.logger.AppLogger;
import com.dogiloki.multitaks.persistent.ExecutionObserver;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author _dogi
 */

public final class ServerPanel extends javax.swing.JPanel{

    private final Frame parent;
    private final Instance ins;
    private final World world;
    private MinecraftServer minecraft_server;
    private Storage file_run;
    private Storage file_eula;
    private ExecutionObserver execution;
    
    public ServerPanel(Frame parent, Instance ins, World world){
        initComponents();
        this.parent=parent;
        this.minecraft_server=new MinecraftServer(ins.cfg);
        this.ins=ins;
        this.world=world;
        this.name_instance_label.setText(this.ins.cfg.name+" - "+this.world.getName());
        this.version_label.setText(this.ins.cfg.version);
        this.forge_version_label.setText(this.ins.cfg.forge_version);
        this.world_label.setText(this.world.toString());
        /*
        // Verificar si ya está iniciado el servidor
        RuntimeMXBean runtime=ManagementFactory.getRuntimeMXBean();
        String current_jvm=runtime.getName();
        for(VirtualMachineDescriptor vm:VirtualMachine.list()){
            if(vm.displayName().contains(this.minecraft_server.getNameServerJar())){
                if(!vm.id().equals(current_jvm.split("@")[0])){
                    this.start_server_btn.setEnabled(false);
                    break;
                }
            }
        }
        */
        this.canStart(this.isStarted());
        this.loadSnapshots();
    }
    
    public String getId(){
        return this.ins.cfg.id;
    }
    
    public void canStart(boolean can){
        if(can){
            this.start_server_btn.setEnabled(false);
            this.packages_mods_btn.setEnabled(false);
            this.create_backup_system_btn.setEnabled(false);
            this.create_snapshot_btn.setEnabled(false);
            this.discard_changes_btn.setEnabled(false);
            this.restore_snapshot_btn.setEnabled(false);
        }else{
            this.start_server_btn.setEnabled(true);
            this.packages_mods_btn.setEnabled(true);
            this.create_backup_system_btn.setEnabled(true);
            this.create_snapshot_btn.setEnabled(true);
            this.discard_changes_btn.setEnabled(true);
            this.restore_snapshot_btn.setEnabled(true);
        }
    }
    
    public boolean isStarted(){
        try{
            Worlds worlds=this.ins.worlds;
            for(World world:worlds.items().values()){
                if(world.isWorldLocked()) return true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            AppLogger.error(ex.getMessage());
        }
        return false;
    }
    
    // Verificar que la versión del servidor este descargada
    public void createMinecraftServer(){
        Storage manifest_version=MinecraftServer.VERSION_MANIFEST;
        Storage server_json=this.minecraft_server.server_json;
        Storage server_jar=this.minecraft_server.server_jar;
        if(!manifest_version.exists()){
            DownloadDialog download=new DownloadDialog(null,true,Properties.downloads.version_manifest_url,manifest_version.getSrc());
            download.setVisible(true);
        }
        if(!server_json.exists()){
            DownloadDialog download=new DownloadDialog(null,true,this.minecraft_server.getUrlMetaJson(),server_json.getSrc());
            download.setVisible(true);
        }
        if(!server_jar.exists()){
            DownloadDialog download=new DownloadDialog(null,true,this.minecraft_server.getUrlServerJar(),server_jar.getSrc());
            download.setVisible(true);
        }
        this.createFiles();
    }
    
    public void createFiles(){
        this.file_run=new Storage(this.ins.getSrc()+"/"+Properties.folders.instances_server+"/start.bat");
        this.file_eula=new Storage(this.file_run.getFolder()+"/eula.txt");
        String game_dir=this.file_run.getFile().getParentFile().getAbsolutePath();
        StringBuilder user_jvm_args=new StringBuilder();
        user_jvm_args.append("-Xms").append(this.ins.cfg.memory_min)
                .append(" -Xmx").append(this.ins.cfg.memory_max)
                .append(" -XX:+UseG1GC")
                .append(" -XX:+UseG1GC")
                .append(" -XX:+UnlockExperimentalVMOptions")
                .append(" -XX:G1NewSizePercent=20")
                .append(" -XX:G1MaxNewSizePercent=40")
                .append(" -XX:G1HeapRegionSize=8M")
                .append(" -XX:G1ReservePercent=20")
                .append(" -XX:MaxGCPauseMillis=50")
                .append(" -XX:+DisableExplicitGC");
        String command="\""+this.ins.cfg.java_path+"\""+
                " -jar "+user_jvm_args.toString()+
                " ../../../"+this.minecraft_server.server_jar.getSrc()+
                " nogui";
        if(this.ins.cfg.usedForge()){
            Storage file_args=new Storage(this.minecraft_server.forge_jar.getFolder()+"/user_jvm_args.txt",DirectoryType.FILE).notExists();
            file_args.write(user_jvm_args.toString());
            file_args.flush();
            file_args.close();
            command=new Storage(this.minecraft_server.forge_jar.getSrc()).read();
            command=command.replace("java -jar ","\""+this.ins.cfg.java_path+"\" -jar ");
            command=command.replace("java","\""+this.ins.cfg.java_path+"\"");
            if(command==null || command.equals("")){
                DirectoryList jar_files=new Storage(minecraft_server.forge_jar.getFolder()).listFiles();
                Path jar_file;
                while((jar_file=jar_files.next())!=null){
                    String name=jar_file.getFileName().toString();
                    if(!name.contains("universal") && !name.contains("forge")) continue;
                    command="\""+this.ins.cfg.java_path+"\""+
                        " -jar "+user_jvm_args.toString()+
                        " ../../../"+jar_file.toString()+
                        " nogui";
                }
            }else{
                AppLogger.logger().showMessage();
                AppLogger.debug("No compatible la versión de forge");
                return;
            }
            try{
                /*
                // Librerias para forge
                Storage.deleteFile(Storage.getDir()+"/"+this.ins.getSrc()+"/"+Properties.folders.instances_server+"/libraries");
                Files.createSymbolicLink(
                    Paths.get(Storage.getDir()+"/"+this.ins.getSrc()+"/"+Properties.folders.instances_server+"/libraries"),
                    Paths.get(Storage.getDir()+"/"+this.minecraft_server.forge_jar.getFolder()+"/libraries")
                );
                */
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        // -Djava.awt.headless=true
        this.file_run.write(command);
        this.file_eula.write("#https://account.mojang.com/documents/minecraft_eula \neula=true");
        this.ins.server_properties.level_name=Properties.folders.instances_worlds+"/"+this.world.getName();
        this.ins.server_properties.save();
        this.file_eula.flush();
        this.file_run.flush();
        this.start();
    }
    
    public void start(){
        try{
            this.execution=new ExecutionObserver(
                    "cmd /c start cmd /k \"title "+this.getId()+" && call \""+this.file_run.getFile().getAbsolutePath()+"\"",
                    //this.minecraft_server.forge_jar.getFolder()
                    this.file_run.getFolder()
            );
            this.execution.start();
            this.canStart(true);
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error al iniciar servidor\n"+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Snapshot getSelectedSnapshot(){
        ListElementWrapper<Snapshot> selected=this.snapshot_list.getSelectedValue();
        if(selected==null){
            AppLogger.logger().showMessage();
            AppLogger.alert("Seleccione un respaldo");
            return null;
        }
        return selected.getValue();
    }
    
    public void loadSnapshots(){
        this.snapshot_list.removeAll();
        this.snapshot_message_box.setText("");
        if(this.world.hasGitRepository()){
            this.create_backup_system_btn.setEnabled(false);
        }else{
            this.create_backup_system_btn.setEnabled(true);
            return;
        }
        DefaultListModel model=new DefaultListModel();
        for(Snapshot snapshot:this.world.getSnapshots()){
            ListElementWrapper<Snapshot> element=new ListElementWrapper(snapshot);
            model.addElement(element);
        }
        this.snapshot_list.setModel(model);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        name_instance_label = new javax.swing.JLabel();
        start_server_btn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        version_label = new javax.swing.JLabel();
        forge_version_label = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        world_label = new javax.swing.JLabel();
        packages_mods_btn = new javax.swing.JButton();
        create_backup_system_btn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        snapshot_list = new javax.swing.JList<>();
        snapshot_message_box = new javax.swing.JTextField();
        create_snapshot_btn = new javax.swing.JButton();
        restore_snapshot_btn = new javax.swing.JButton();
        discard_changes_btn = new javax.swing.JButton();

        name_instance_label.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        name_instance_label.setText("jLabel1");

        start_server_btn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        start_server_btn.setText("Iniciar servidor");
        start_server_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                start_server_btnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Versión Minecraft");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Versión Forge");

        version_label.setText("jLabel4");

        forge_version_label.setText("jLabel4");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Mundo");

        world_label.setText("jLabel4");

        packages_mods_btn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        packages_mods_btn.setText("Mods");
        packages_mods_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                packages_mods_btnActionPerformed(evt);
            }
        });

        create_backup_system_btn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        create_backup_system_btn.setText("Crear repositorio de respaldos");
        create_backup_system_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                create_backup_system_btnActionPerformed(evt);
            }
        });

        snapshot_list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(snapshot_list);

        snapshot_message_box.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        snapshot_message_box.setToolTipText("Mensaje del respaldo");

        create_snapshot_btn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        create_snapshot_btn.setText("Crear respaldo");
        create_snapshot_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                create_snapshot_btnActionPerformed(evt);
            }
        });

        restore_snapshot_btn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        restore_snapshot_btn.setText("Cargar respaldo");
        restore_snapshot_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restore_snapshot_btnActionPerformed(evt);
            }
        });

        discard_changes_btn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        discard_changes_btn.setText("Descartar cambios");
        discard_changes_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discard_changes_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(name_instance_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(create_backup_system_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 287, Short.MAX_VALUE)
                        .addComponent(packages_mods_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(start_server_btn))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(snapshot_message_box)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(create_snapshot_btn))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(discard_changes_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(restore_snapshot_btn))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(version_label)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(forge_version_label)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(world_label)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(name_instance_label)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(version_label)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(world_label)
                    .addComponent(forge_version_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(start_server_btn)
                    .addComponent(packages_mods_btn)
                    .addComponent(create_backup_system_btn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(snapshot_message_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(create_snapshot_btn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(discard_changes_btn)
                    .addComponent(restore_snapshot_btn))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void start_server_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_server_btnActionPerformed
        this.createMinecraftServer();
    }//GEN-LAST:event_start_server_btnActionPerformed

    private void packages_mods_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_packages_mods_btnActionPerformed
        new PackagesModsDialog(null,true,this.ins).setVisible(true);
    }//GEN-LAST:event_packages_mods_btnActionPerformed

    private void create_backup_system_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_backup_system_btnActionPerformed
        this.world.initializeRepository();
        this.loadSnapshots();
    }//GEN-LAST:event_create_backup_system_btnActionPerformed

    private void create_snapshot_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_snapshot_btnActionPerformed
        String message=this.snapshot_message_box.getText();
        if(message.trim().equals("")){
            AppLogger.logger().showMessage();
            AppLogger.warning("No hay mensaje para hacer el respaldo: "+this.world.getSrc());
        }else{
            this.world.createSnapshot(message);
            this.loadSnapshots();
        }
    }//GEN-LAST:event_create_snapshot_btnActionPerformed

    private void restore_snapshot_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restore_snapshot_btnActionPerformed
        Snapshot snapshot=this.getSelectedSnapshot();
        if(snapshot==null) return;
        this.world.restoreSnapshot(snapshot);
        this.loadSnapshots();
    }//GEN-LAST:event_restore_snapshot_btnActionPerformed

    private void discard_changes_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discard_changes_btnActionPerformed
        this.world.discardChanges();
    }//GEN-LAST:event_discard_changes_btnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton create_backup_system_btn;
    private javax.swing.JButton create_snapshot_btn;
    private javax.swing.JButton discard_changes_btn;
    private javax.swing.JLabel forge_version_label;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel name_instance_label;
    private javax.swing.JButton packages_mods_btn;
    private javax.swing.JButton restore_snapshot_btn;
    private javax.swing.JList<ListElementWrapper<Snapshot>> snapshot_list;
    private javax.swing.JTextField snapshot_message_box;
    private javax.swing.JButton start_server_btn;
    private javax.swing.JLabel version_label;
    private javax.swing.JLabel world_label;
    // End of variables declaration//GEN-END:variables
}
