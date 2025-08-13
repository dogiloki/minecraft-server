package com.dogiloki.minecraftserver.infraestructure.ui.components;

import com.dogiloki.minecraftserver.application.dao.Properties;
import com.dogiloki.minecraftserver.core.services.Instance;
import com.dogiloki.minecraftserver.core.services.MinecraftServer;
import com.dogiloki.multitaks.dataformat.JSON;
import com.dogiloki.multitaks.dataformat.contracts.DataFormat;
import com.dogiloki.multitaks.directory.DirectoryList;
import com.dogiloki.multitaks.directory.Storage;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.dogiloki.multitaks.download.DownloadDialog;
import com.dogiloki.multitaks.persistent.ExecutionObserver;
import com.dogiloki.multitaks.validator.MapValues;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Desktop;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.regex.Pattern;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dogi_
 */
public final class VersionPanel extends javax.swing.JPanel{

    private Instance ins=null;
    private Storage version_manifest=MinecraftServer.VERSION_MANIFEST;
    private Storage file_forge_installer=null;
    private JDialog frame;
    
    public VersionPanel(Instance ins, JDialog frame){
        initComponents();
        this.frame=frame;
        this.ins=ins;
        this.table_versions.setDefaultEditor(Object.class,null);
        this.hasForgeInstaller(false);
        this.loadVersions();
        this.loadForgeVersions();
    }
    
    public void loadVersions(){
        try{
            if(!this.version_manifest.exists()){
                DownloadDialog download=new DownloadDialog(null,true,Properties.downloads.version_manifest_url,this.version_manifest.getSrc());
                download.setVisible(true);
                this.loadVersions();
                return;
            }
            DefaultTableModel model=new DefaultTableModel();
            model.addColumn("Versión");
            model.addColumn("Descargado");
            JsonObject json=JSON.gson().fromJson(this.version_manifest.read(),JsonObject.class);
            JsonArray versions=json.getAsJsonArray("versions");
            int default_selected_row=-1;
            int current_row=0;
            for(JsonElement element:versions){
                JsonObject version=element.getAsJsonObject();
                if(!version.get("type").getAsString().equals("release")) continue;
                String download_date="";
                String name=version.get("id").getAsString();
                String fo_minecraft=Properties.folders.metadata_mc+"/"+name;
                String fi_minecraft=DataFormat.messageFormat(Properties.files.minecraft_server_jar,new MapValues().append("0",name));
                Storage jar=new Storage(fo_minecraft+"/"+fi_minecraft);
                if(jar.exists()){
                    download_date=new Date(Files.getLastModifiedTime(Paths.get(jar.getSrc())).toMillis()).toString();
                }
                Object[] data={name,download_date};
                model.addRow(data);
                // Verificar si se va a seleccionar
                if(name.equals(this.ins.cfg.version)){
                    default_selected_row=current_row;
                }
                current_row++;
            }
            this.table_versions.setModel(model);
            
            // Selecionar la fila por defecto si se encontró
            if(default_selected_row>=0 && default_selected_row<model.getRowCount()){
                this.table_versions.setRowSelectionInterval(default_selected_row,default_selected_row);
                this.table_versions.scrollRectToVisible(this.table_versions.getCellRect(default_selected_row,0,true));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void loadForgeVersions(){
        this.used_forge_checkbox.setSelected(this.ins.cfg.usedForge());
        DefaultTableModel model=new DefaultTableModel();
        model.addColumn("Versión");
        model.addColumn("Instalado");
        DirectoryList forge_versions=new Storage(Properties.folders.libraries_folder+"/"+Properties.folders.libraries_forge,DirectoryType.FOLDER).notExists().listFolders();
        int default_selected_row=-1;
        int current_row=0;
        while(forge_versions.hasNext()){
            Path path=forge_versions.next();
            if(path==null) continue;
            Storage version_folder=new Storage(path.toString(),DirectoryType.FOLDER);
            String version=version_folder.getName();
            Storage jar_file=new Storage(path.toString()+"/run.bat",DirectoryType.FILE).notExists();
            try{
                String install_date=new Date(Files.getLastModifiedTime(Paths.get(jar_file.getSrc())).toMillis()).toString();
                Object[] data={version,install_date};
                model.addRow(data);
                // Verificar si se va a seleccionar
                if(version.equals(this.ins.cfg.forge_version)){
                    default_selected_row=current_row;
                }
                current_row++;
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        this.table_forge_versions.setModel(model);
        // Selecionar la fila por defecto si se encontró
        if(default_selected_row>=0 && default_selected_row<model.getRowCount()){
            this.table_forge_versions.setRowSelectionInterval(default_selected_row,default_selected_row);
            this.table_forge_versions.scrollRectToVisible(this.table_forge_versions.getCellRect(default_selected_row,0,true));
        }
    }
    
    public void hasForgeInstaller(boolean value){
        if(value){
            this.path_java_explore_btn.setEnabled(true);
            this.forge_installer_btn.setEnabled(true);
        }else{
            this.path_java_explore_btn.setEnabled(true);
            this.forge_installer_btn.setEnabled(false);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        content_version = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        forger_installer_box = new javax.swing.JTextField();
        path_java_explore_btn = new javax.swing.JButton();
        forge_installer_btn = new javax.swing.JButton();
        forge_installer_progress = new javax.swing.JProgressBar();
        link_forge_install_btn = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_versions = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_forge_versions = new javax.swing.JTable();
        used_forge_checkbox = new javax.swing.JCheckBox();

        jLabel8.setText("Seleccionar instalador de forge");

        forger_installer_box.setEnabled(false);

        path_java_explore_btn.setText("Examinar");
        path_java_explore_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                path_java_explore_btnActionPerformed(evt);
            }
        });

        forge_installer_btn.setText("Instalar");
        forge_installer_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forge_installer_btnActionPerformed(evt);
            }
        });

        link_forge_install_btn.setForeground(new java.awt.Color(0, 102, 255));
        link_forge_install_btn.setText("Descarga Instalador de Forge");
        link_forge_install_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        link_forge_install_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                link_forge_install_btnMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout content_versionLayout = new javax.swing.GroupLayout(content_version);
        content_version.setLayout(content_versionLayout);
        content_versionLayout.setHorizontalGroup(
            content_versionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(content_versionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(content_versionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(content_versionLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(link_forge_install_btn))
                    .addGroup(content_versionLayout.createSequentialGroup()
                        .addGroup(content_versionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(forge_installer_progress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(forger_installer_box))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(content_versionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(path_java_explore_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(forge_installer_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        content_versionLayout.setVerticalGroup(
            content_versionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(content_versionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(content_versionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(link_forge_install_btn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(content_versionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(forger_installer_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(path_java_explore_btn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(content_versionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(forge_installer_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(forge_installer_progress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        table_versions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        table_versions.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table_versions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_versionsMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                table_versionsMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(table_versions);

        table_forge_versions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        table_forge_versions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_forge_versionsMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                table_forge_versionsMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(table_forge_versions);

        used_forge_checkbox.setText("Usar Forge");
        used_forge_checkbox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                used_forge_checkboxStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(content_version, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(used_forge_checkbox)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(used_forge_checkbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(content_version, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void path_java_explore_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_path_java_explore_btnActionPerformed
        String file_forge_installer=Storage.selectFile(this);
        if(file_forge_installer!=null){
            this.file_forge_installer=new Storage(file_forge_installer,DirectoryType.FILE);
            this.forger_installer_box.setText(file_forge_installer);
            this.hasForgeInstaller(true);
        }
    }//GEN-LAST:event_path_java_explore_btnActionPerformed

    private void table_versionsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_versionsMousePressed
        int row=this.table_versions.getSelectedRow();
        this.ins.cfg.version=this.table_versions.getValueAt(row,0).toString();
        this.frame.setTitle(this.ins.cfg.name+" - "+this.ins.cfg.version);
    }//GEN-LAST:event_table_versionsMousePressed

    private void table_versionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_versionsMouseClicked
        if(evt.getClickCount()==2){
            
        }
    }//GEN-LAST:event_table_versionsMouseClicked

    private void table_forge_versionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_forge_versionsMouseClicked
        if(evt.getClickCount()==2){
            
        }
    }//GEN-LAST:event_table_forge_versionsMouseClicked

    private void table_forge_versionsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_forge_versionsMousePressed
        int row=this.table_forge_versions.getSelectedRow();
        this.ins.cfg.forge_version=this.table_forge_versions.getValueAt(row,0).toString();
        this.frame.setTitle(this.ins.cfg.name+" - "+this.ins.cfg.forge_version);
        this.used_forge_checkbox.setSelected(true);
    }//GEN-LAST:event_table_forge_versionsMousePressed

    private void used_forge_checkboxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_used_forge_checkboxStateChanged
        if(!this.used_forge_checkbox.isSelected()){
            this.ins.cfg.forge_version=null;
            this.table_forge_versions.clearSelection();
        }
    }//GEN-LAST:event_used_forge_checkboxStateChanged

    private void forge_installer_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forge_installer_btnActionPerformed
        try{
            if(this.file_forge_installer==null){
                JOptionPane.showMessageDialog(null,"Seleccione un instaldor de forge","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            String name=this.file_forge_installer.getName();
            String regex="^forge-[\\w\\.-]*\\d[\\w\\.-]*-installer\\.jar$";
            Pattern pattern=Pattern.compile(regex);
            if(!pattern.matcher(name).matches()){
                JOptionPane.showMessageDialog(null,"El nombre no es válido (forge-x.x-x.x.-installer)","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.hasForgeInstaller(false);
            this.forge_installer_progress.setIndeterminate(true);
            MinecraftServer minecraft_server=new MinecraftServer(this.ins.cfg).forge(name);
            Storage.deleteFolder(minecraft_server.forge_jar.getFolder());
            Storage.createFolder(minecraft_server.forge_jar.getFolder());
            ExecutionObserver execution=new ExecutionObserver(
                    "\""+this.ins.cfg.java_path+"\" -jar \""+this.file_forge_installer.getSrc()+"\" --installServer",
                    minecraft_server.forge_jar.getFolder()
            );
            execution.start((line,posi)->{
                System.out.println(line);
            });
            execution.onFinalized=(output,code)->{
                this.forge_installer_progress.setIndeterminate(false);
                JOptionPane.showMessageDialog(null,"Instalación finalizada ("+code+")","Instalador",JOptionPane.INFORMATION_MESSAGE);
                this.hasForgeInstaller(true);
                this.loadForgeVersions();
            };
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_forge_installer_btnActionPerformed

    private void link_forge_install_btnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_link_forge_install_btnMouseReleased
        try{
            Desktop.getDesktop().browse(new URI("https://files.minecraftforge.net"));
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error al abrir enlace\nhttps://files.minecraftforge.net","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_link_forge_install_btnMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel content_version;
    private javax.swing.JButton forge_installer_btn;
    private javax.swing.JProgressBar forge_installer_progress;
    public javax.swing.JTextField forger_installer_box;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel link_forge_install_btn;
    private javax.swing.JButton path_java_explore_btn;
    public javax.swing.JTable table_forge_versions;
    public javax.swing.JTable table_versions;
    private javax.swing.JCheckBox used_forge_checkbox;
    // End of variables declaration//GEN-END:variables
}
