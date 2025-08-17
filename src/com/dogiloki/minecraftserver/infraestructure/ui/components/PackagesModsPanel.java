package com.dogiloki.minecraftserver.infraestructure.ui.components;

import com.dogiloki.minecraftserver.application.dao.Properties;
import com.dogiloki.minecraftserver.core.services.Instance;
import com.dogiloki.minecraftserver.core.services.Mods;
import com.dogiloki.minecraftserver.core.services.PackagesMods;
import com.dogiloki.minecraftserver.infraestructure.utils.ComboItemWrapper;
import com.dogiloki.minecraftserver.infraestructure.utils.ListElementWrapper;
import com.dogiloki.multitaks.directory.DirectoryList;
import com.dogiloki.multitaks.directory.Storage;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import java.awt.event.ItemEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
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
    private final String ins_cfg_mods_old;
    private Mods selected_mods=null;
    
    public PackagesModsPanel(Instance ins){
        initComponents();
        this.ins=ins;
        this.ins_cfg_mods_old=this.ins.cfg.mods;
        this.loadPackagesMods();
    }
    
    public void loadPackagesMods(){
        DefaultListModel<ListElementWrapper<Mods>> model=new DefaultListModel();
        
        this.packages_mods_list.removeAll();
        this.selected_mods=null;
        this.packages_mods.reload();
        
        this.packages_mods_box.setLoading(true);
        this.packages_mods_box.removeAll();
        this.packages_mods_box.addItem(new ComboItemWrapper<Mods>(null,Mods.WITHOUT_MODS));
        this.packages_mods.items().iterate((name,mods)->{
            model.addElement(new ListElementWrapper(mods));
            ComboItemWrapper item=new ComboItemWrapper<Mods>(mods);
            this.packages_mods_box.addItem(item);
            if(this.ins.cfg.mods.equals(item.toString())){
                this.packages_mods_box.setSelectedItem(item);
            }
        });
        
        this.packages_mods_box.setLoading(false);
        this.packages_mods_list.setModel(model);
    }
    
    public void loadMods(){
        if(this.selected_mods==null){
            return;
        }
        DefaultTableModel model_mods=new DefaultTableModel();
        model_mods.addColumn("Activo");
        model_mods.addColumn("Nombre");
        DirectoryList mods=this.selected_mods.listFiles();
        Path folder;
        if(mods==null){
            return;
        }
        while((folder=mods.next())!=null){
            boolean status=new Storage(folder.toString()).getExtension().equals("disabled");
            Object[] data={(status?"":"Activo"),folder.getFileName().toString().replace(".disabled","")};
            model_mods.addRow(data);
        }
        this.mods_table.setModel(model_mods);
    }
    
    public Map<Integer,String> getSelecctionMods(){
        int[] rows=this.mods_table.getSelectedRows();
        Map<Integer,String> mods=new HashMap<>();
        for(int row:rows){
            mods.put(row,this.mods_table.getValueAt(row,1).toString());
        }
        return mods;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        packages_mods_list = new javax.swing.JList<>();
        new_package_mods_btn = new javax.swing.JButton();
        delete_packages_mods_btn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        mods_table = new javax.swing.JTable();
        active_mods_btn = new javax.swing.JButton();
        desactive_mods_btn = new javax.swing.JButton();
        delete_mods_btn = new javax.swing.JButton();
        add_mods_btn = new javax.swing.JButton();
        packages_mods_box = new com.dogiloki.minecraftserver.infraestructure.utils.ComboBoxWrapper<>();
        jLabel1 = new javax.swing.JLabel();

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
        delete_packages_mods_btn.setText("Eliminar Paquete");
        delete_packages_mods_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_packages_mods_btnActionPerformed(evt);
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

        delete_mods_btn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        delete_mods_btn.setText("Eliminar");
        delete_mods_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_mods_btnActionPerformed(evt);
            }
        });

        add_mods_btn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        add_mods_btn.setText("Agregar mods");
        add_mods_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_mods_btnActionPerformed(evt);
            }
        });

        packages_mods_box.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                packages_mods_boxItemStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Usar Paquete de Mods:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(new_package_mods_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delete_packages_mods_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(packages_mods_box, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(add_mods_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delete_mods_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(active_mods_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(desactive_mods_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(new_package_mods_btn)
                    .addComponent(packages_mods_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delete_packages_mods_btn))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
            .addGroup(layout.createSequentialGroup()
                .addComponent(add_mods_btn)
                .addGap(18, 18, 18)
                .addComponent(delete_mods_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(active_mods_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(desactive_mods_btn)
                .addGap(0, 0, Short.MAX_VALUE))
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
        String name=JOptionPane.showInputDialog(null,"Ingrese el nombre:","Nuvo Paquete de Mods",JOptionPane.QUESTION_MESSAGE);
        if(name==null) return;
        new Storage(Properties.folders.instances_mods+"/"+name,DirectoryType.FOLDER).notExists();
        this.loadPackagesMods();
    }//GEN-LAST:event_new_package_mods_btnActionPerformed

    private void delete_packages_mods_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_packages_mods_btnActionPerformed
        if(this.selected_mods==null){
            JOptionPane.showMessageDialog(null,"No hay un elemento seleccionado","Error al eliminar",JOptionPane.ERROR_MESSAGE);
        }else{
            this.selected_mods.delete();
            this.selected_mods=null;
            this.loadPackagesMods();
        }
    }//GEN-LAST:event_delete_packages_mods_btnActionPerformed

    private void active_mods_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_active_mods_btnActionPerformed
        this.getSelecctionMods().forEach((key,item)->{
            Storage.rename(this.selected_mods.getSrc()+"/"+item+".disabled",this.selected_mods.getSrc()+"/"+item);
        });
        this.loadMods();
    }//GEN-LAST:event_active_mods_btnActionPerformed

    private void desactive_mods_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desactive_mods_btnActionPerformed
        this.getSelecctionMods().forEach((key,item)->{
            Storage.rename(this.selected_mods.getSrc()+"/"+item,this.selected_mods.getSrc()+"/"+item+".disabled");
        });
        this.loadMods();
    }//GEN-LAST:event_desactive_mods_btnActionPerformed

    private void delete_mods_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_mods_btnActionPerformed
        this.getSelecctionMods().forEach((key,item)->{
            Storage.deleteFile(this.selected_mods.getSrc()+"/"+item);
        });
        this.loadMods();
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

    private void packages_mods_boxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_packages_mods_boxItemStateChanged
        if(evt.getStateChange()==ItemEvent.SELECTED && !this.packages_mods_box.isLoading()){
            this.ins.cfg.mods=this.packages_mods_box.getSelectedItem().toString();
            this.ins.cfg.change_mods=!this.ins_cfg_mods_old.equals(this.ins.cfg.mods);
        }
    }//GEN-LAST:event_packages_mods_boxItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton active_mods_btn;
    private javax.swing.JButton add_mods_btn;
    private javax.swing.JButton delete_mods_btn;
    private javax.swing.JButton delete_packages_mods_btn;
    private javax.swing.JButton desactive_mods_btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable mods_table;
    private javax.swing.JButton new_package_mods_btn;
    private com.dogiloki.minecraftserver.infraestructure.utils.ComboBoxWrapper<ComboItemWrapper<Mods>> packages_mods_box;
    private javax.swing.JList<ListElementWrapper<Mods>> packages_mods_list;
    // End of variables declaration//GEN-END:variables
}
