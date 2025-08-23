package com.dogiloki.minecraftserver.infraestructure.ui.components;

import com.dogiloki.minecraftserver.application.dao.Properties;
import com.dogiloki.minecraftserver.core.entities.ListModFiles;
import com.dogiloki.minecraftserver.core.services.Instance;
import com.dogiloki.minecraftserver.core.services.ModFile;
import com.dogiloki.minecraftserver.core.services.Mods;
import com.dogiloki.minecraftserver.core.services.PackagesMods;
import com.dogiloki.minecraftserver.infraestructure.utils.ComboItemWrapper;
import com.dogiloki.minecraftserver.infraestructure.utils.ConfirmHelper;
import com.dogiloki.minecraftserver.infraestructure.utils.ListElementWrapper;
import com.dogiloki.multitaks.directory.DirectoryList;
import com.dogiloki.multitaks.directory.Storage;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.dogiloki.multitaks.logger.AppLogger;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author _dogi
 */

public final class PackagesModsPanel extends javax.swing.JPanel{

    private final Instance ins;
    private final PackagesMods packages_mods=new PackagesMods();
    private Mods selected_mods=null;
    private final Mods instance_mods;
    
    public PackagesModsPanel(Instance ins){
        initComponents();
        this.ins=ins;
        this.instance_mods=this.ins.mods;
        this.loadPackagesMods();
    }
    
    public void loadPackagesMods(){
        DefaultListModel<ListElementWrapper<Mods>> model=new DefaultListModel();
        
        this.packages_mods_list.removeAll();
        this.selected_mods=null;
        this.packages_mods.reload();
        
        this.packages_mods.items().iterate((name,mods)->{
            model.addElement(new ListElementWrapper(mods));
            ComboItemWrapper item=new ComboItemWrapper<Mods>(mods);
        });
        
        this.packages_mods_list.setModel(model);
        this.loadMods();
        this.loadInstanceMods();
    }
    
    public void loadMods(){
        DefaultTableModel model_mods=new DefaultTableModel();
        model_mods.addColumn("Nombre");
        if(this.selected_mods==null){
            this.mods_table.setModel(model_mods);
            return;
        }
        DirectoryList mods=this.selected_mods.listFiles();
        Path folder;
        if(mods==null){
            return;
        }
        while((folder=mods.next())!=null){
            Object[] data={new ModFile(folder.toString())};
            model_mods.addRow(data);
        }
        this.mods_table.setModel(model_mods);
    }
    
    public void loadInstanceMods(){
        DefaultTableModel model_mods=new DefaultTableModel();
        model_mods.addColumn("Activar");
        model_mods.addColumn("Nombre");
        DirectoryList mods=this.instance_mods.listFiles();
        Path folder;
        if(mods==null){
            return;
        }
        while((folder=mods.next())!=null){
            ModFile mod_file=new ModFile(folder.toString());
            Object[] data={mod_file.isEnable()?"Activo":"",mod_file};
            model_mods.addRow(data);
        }
        this.instance_mods_table.setModel(model_mods);
    }
    
    public ListModFiles getSelectedMods(){
        int[] rows=this.mods_table.getSelectedRows();
        ListModFiles mod_files=new ListModFiles();
        for(int row:rows){
            ModFile mod_file=(ModFile)this.mods_table.getValueAt(row,0);
            mod_files.append(mod_file.toString(),mod_file);
        }
        return mod_files;
    }
    
    public ListModFiles getSelectedInstanceMods(){
        int[] rows=this.instance_mods_table.getSelectedRows();
        ListModFiles mod_files=new ListModFiles();
        for(int row:rows){
            ModFile mod_file=(ModFile)this.instance_mods_table.getValueAt(row,1);
            mod_files.append(mod_file.toString(),mod_file);
        }
        return mod_files;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        packages_mods_list = new javax.swing.JList<>();
        new_package_mods_btn = new javax.swing.JButton();
        delete_packages_mods_btn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        add_mods_btn = new javax.swing.JButton();
        delete_mods_btn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        mods_table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        instance_mods_table = new javax.swing.JTable();
        active_mods_btn = new javax.swing.JButton();
        desactive_mods_btn = new javax.swing.JButton();
        add_mod_btn = new javax.swing.JButton();
        remove_mod_btn = new javax.swing.JButton();

        packages_mods_list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        packages_mods_list.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                packages_mods_listValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(packages_mods_list);

        new_package_mods_btn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        new_package_mods_btn.setText("+ Nuevo paquete");
        new_package_mods_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new_package_mods_btnActionPerformed(evt);
            }
        });

        delete_packages_mods_btn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        delete_packages_mods_btn.setText("- Eliminar Paquete");
        delete_packages_mods_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_packages_mods_btnActionPerformed(evt);
            }
        });

        add_mods_btn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        add_mods_btn.setText("+ Agregar mods");
        add_mods_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_mods_btnActionPerformed(evt);
            }
        });

        delete_mods_btn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        delete_mods_btn.setText("- Eliminar");
        delete_mods_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_mods_btnActionPerformed(evt);
            }
        });

        mods_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(mods_table);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
            .addComponent(add_mods_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(delete_mods_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(add_mods_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(delete_mods_btn))
        );

        instance_mods_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(instance_mods_table);

        active_mods_btn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        active_mods_btn.setText("Activar");
        active_mods_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                active_mods_btnActionPerformed(evt);
            }
        });

        desactive_mods_btn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        desactive_mods_btn.setText("Desactivar");
        desactive_mods_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desactive_mods_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(desactive_mods_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(active_mods_btn))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(active_mods_btn)
                    .addComponent(desactive_mods_btn)))
        );

        add_mod_btn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        add_mod_btn.setText(">");
        add_mod_btn.setToolTipText("Agregar");
        add_mod_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_mod_btnActionPerformed(evt);
            }
        });

        remove_mod_btn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        remove_mod_btn.setText("<");
        remove_mod_btn.setToolTipText("Eliminar");
        remove_mod_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remove_mod_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(delete_packages_mods_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(new_package_mods_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(add_mod_btn)
                    .addComponent(remove_mod_btn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(new_package_mods_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(delete_packages_mods_btn))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(add_mod_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(remove_mod_btn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void packages_mods_listValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_packages_mods_listValueChanged
        if(this.packages_mods_list.getSelectedValue()==null){
            return;
        }
        this.selected_mods=this.packages_mods_list.getSelectedValue().getValue();
        this.loadMods();
    }//GEN-LAST:event_packages_mods_listValueChanged

    private void new_package_mods_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_new_package_mods_btnActionPerformed
        String name=JOptionPane.showInputDialog(null,"Ingrese el nombre:","Nuevo Paquete de Mods",JOptionPane.QUESTION_MESSAGE);
        if(name==null) return;
        new Storage(Properties.folders.instances_mods+"/"+name,DirectoryType.FOLDER).notExists();
        this.loadPackagesMods();
    }//GEN-LAST:event_new_package_mods_btnActionPerformed

    private void delete_packages_mods_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_packages_mods_btnActionPerformed
        if(this.selected_mods==null){
            JOptionPane.showMessageDialog(null,"No hay un elemento seleccionado","Error al eliminar",JOptionPane.ERROR_MESSAGE);
        }else{
            ConfirmHelper.runWithConfirm("¿Eliminar paquete de Mods? Elimina la carpeta y los archivos",()->{
                this.selected_mods.delete();
                this.selected_mods=null;
                this.loadPackagesMods();
            });
        }
    }//GEN-LAST:event_delete_packages_mods_btnActionPerformed

    private void active_mods_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_active_mods_btnActionPerformed
        this.getSelectedInstanceMods().forEach((key,item)->{
            item.enable();
        });
        this.loadInstanceMods();
    }//GEN-LAST:event_active_mods_btnActionPerformed

    private void desactive_mods_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desactive_mods_btnActionPerformed
        this.getSelectedInstanceMods().forEach((key,item)->{
            item.disable();
        });
        this.loadInstanceMods();
    }//GEN-LAST:event_desactive_mods_btnActionPerformed

    private void delete_mods_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_mods_btnActionPerformed
        ConfirmHelper.runWithConfirm("¿Eliminar mod? Elimina el archivo",()->{
            this.getSelectedMods().forEach((key,item)->{
                item.delete();
            });
            this.loadMods();
            this.loadInstanceMods();
        });
    }//GEN-LAST:event_delete_mods_btnActionPerformed

    private void add_mods_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_mods_btnActionPerformed
        if(this.selected_mods==null){
            JOptionPane.showMessageDialog(null,"No hay un elemento seleccionado","Error al eliminar",JOptionPane.ERROR_MESSAGE);
            return;
        }
        File[] mods=Storage.selectFiles("");
        if(mods==null){
            return;
        }
        for(File file:mods){
            try{
                String[] name_temp=file.getPath().replace("\\","/").split("/");
                    String name=name_temp[name_temp.length-1];
                    Storage.copyFile(file.getPath(),this.selected_mods.getSrc()+"/"+name);
                }catch(Exception ex){
                    ex.printStackTrace();
                    return;
                }
            }
            this.loadMods();
    }//GEN-LAST:event_add_mods_btnActionPerformed

    private void add_mod_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_mod_btnActionPerformed
        this.getSelectedMods().forEach((name,existing)->{
            try{
                Storage link=new Storage(this.instance_mods.getSrc()+"/"+existing.getName());
                if(!existing.exists()){
                    AppLogger.debug("No existe "+existing.getSrc());
                }else{
                    if(!existing.hashing().equals(link.hashing())){
                        Files.createLink(link.asPath(),existing.asPath());
                    }
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
        this.loadInstanceMods();
    }//GEN-LAST:event_add_mod_btnActionPerformed

    private void remove_mod_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remove_mod_btnActionPerformed
        this.getSelectedInstanceMods().forEach((name,mod_file)->{
            mod_file.delete();
        });
        this.loadInstanceMods();
    }//GEN-LAST:event_remove_mod_btnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton active_mods_btn;
    private javax.swing.JButton add_mod_btn;
    private javax.swing.JButton add_mods_btn;
    private javax.swing.JButton delete_mods_btn;
    private javax.swing.JButton delete_packages_mods_btn;
    private javax.swing.JButton desactive_mods_btn;
    private javax.swing.JTable instance_mods_table;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable mods_table;
    private javax.swing.JButton new_package_mods_btn;
    private javax.swing.JList<ListElementWrapper<Mods>> packages_mods_list;
    private javax.swing.JButton remove_mod_btn;
    // End of variables declaration//GEN-END:variables
}
