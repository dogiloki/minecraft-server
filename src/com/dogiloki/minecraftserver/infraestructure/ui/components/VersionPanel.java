package com.dogiloki.minecraftserver.infraestructure.ui.components;

import com.dogiloki.minecraftserver.application.dao.Properties;
import com.dogiloki.minecraftserver.core.services.Instance;
import com.dogiloki.minecraftserver.core.services.MinecraftServer;
import com.dogiloki.multitaks.dataformat.JSON;
import com.dogiloki.multitaks.dataformat.contracts.DataFormat;
import com.dogiloki.multitaks.directory.Storage;
import com.dogiloki.multitaks.download.DownloadDialog;
import com.dogiloki.multitaks.validator.MapValues;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dogi_
 */
public final class VersionPanel extends javax.swing.JPanel{

    private Instance ins=null;
    private Storage version_manifest=MinecraftServer.VERSION_MANIFEST;
    private JDialog frame;
    
    public VersionPanel(Instance ins, JDialog frame){
        initComponents();
        this.frame=frame;
        this.ins=ins;
        this.table_versions.setDefaultEditor(Object.class,null);
        this.loadVersions();
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        content_version = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        caja_path_java = new javax.swing.JTextField();
        btn_path_java_explore4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_versions = new javax.swing.JTable();

        jLabel8.setText("Seleccionar versión");

        btn_path_java_explore4.setText("Examinar");
        btn_path_java_explore4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_path_java_explore4ActionPerformed(evt);
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
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(content_versionLayout.createSequentialGroup()
                        .addComponent(caja_path_java)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_path_java_explore4)))
                .addContainerGap())
        );
        content_versionLayout.setVerticalGroup(
            content_versionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(content_versionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(content_versionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(caja_path_java, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_path_java_explore4))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                    .addComponent(content_version, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(content_version, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_path_java_explore4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_path_java_explore4ActionPerformed
        
    }//GEN-LAST:event_btn_path_java_explore4ActionPerformed

    private void table_versionsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_versionsMousePressed
        int row=this.table_versions.getSelectedRow();
        this.ins.cfg.version=this.table_versions.getValueAt(row,0).toString();
        this.frame.setTitle(this.ins.cfg.name+" - "+this.ins.cfg.version);
    }//GEN-LAST:event_table_versionsMousePressed

    private void table_versionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_versionsMouseClicked
        if(evt.getClickCount()==2){
            
        }
    }//GEN-LAST:event_table_versionsMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_path_java_explore4;
    public javax.swing.JTextField caja_path_java;
    private javax.swing.JPanel content_version;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable table_versions;
    // End of variables declaration//GEN-END:variables
}
