package com.dogiloki.minecraftserver.infraestructure.ui.components;

import com.dogiloki.minecraftserver.core.services.Instance;
import com.dogiloki.multitaks.directory.DirectoryList;
import com.dogiloki.multitaks.directory.Storage;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dogi_
 */
public final class ModsPanel extends javax.swing.JPanel {
    
    private Instance ins=null;
    private String folder_mods="";
    
    public ModsPanel(Instance ins){
        initComponents();
        this.ins=ins;
    }
    
    public void getMods(){
        DefaultTableModel model_mods=new DefaultTableModel();
        model_mods.addColumn("Activo");
        model_mods.addColumn("Nombre");
        DirectoryList mods=new Storage(this.folder_mods).listFiles();
        Path folder;
        if(mods==null){
            return;
        }
        while((folder=mods.next())!=null){
            boolean status=new Storage(folder.toString()).getExtension().equals("disabled");
            Object[] data={(status?"":"Activo"),folder.toString().replace(".disabled","")};
            model_mods.addRow(data);
        }
        this.table_mods.setModel(model_mods);
    }
    
    public Map<Integer,String> getSelecctionMods(){
        int[] rows=this.table_mods.getSelectedRows();
        Map<Integer,String> mods=new HashMap<>();
        for(int row:rows){
            mods.put(row,this.table_mods.getValueAt(row,1).toString());
        }
        return mods;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table_mods = new javax.swing.JTable();
        btn_add_mods = new javax.swing.JButton();
        btn_delete_mods = new javax.swing.JButton();
        btn_active_mods = new javax.swing.JButton();
        btn_desactive_mods = new javax.swing.JButton();

        table_mods.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(table_mods);

        btn_add_mods.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btn_add_mods.setText("Agregar mods");
        btn_add_mods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_modsActionPerformed(evt);
            }
        });

        btn_delete_mods.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btn_delete_mods.setText("Eliminar");
        btn_delete_mods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_modsActionPerformed(evt);
            }
        });

        btn_active_mods.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btn_active_mods.setText("Activar");
        btn_active_mods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_active_modsActionPerformed(evt);
            }
        });

        btn_desactive_mods.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btn_desactive_mods.setText("Desactivar");
        btn_desactive_mods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_desactive_modsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_add_mods, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_delete_mods, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_active_mods, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btn_desactive_mods, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btn_add_mods)
                .addGap(18, 18, 18)
                .addComponent(btn_delete_mods)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_active_mods)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_desactive_mods)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_add_modsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_modsActionPerformed
        File[] mods=Storage.selectFiles("mods");
        if(mods==null){
            return;
        }
        for(File file:mods){
            try{
                String[] name_temp=file.getPath().replace("\\","/").split("/");
                String name=name_temp[name_temp.length-1];
                Storage.copyFile(file.getPath(),this.folder_mods+"/"+name);
            }catch(Exception ex){
                ex.printStackTrace();
                return;
            }
        }
        this.getMods();
    }//GEN-LAST:event_btn_add_modsActionPerformed

    private void btn_delete_modsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_modsActionPerformed
        this.getSelecctionMods().forEach((key,item)->{
            Storage.deleteFile(this.folder_mods+"/"+item);
        });
        this.getMods();
    }//GEN-LAST:event_btn_delete_modsActionPerformed

    private void btn_active_modsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_active_modsActionPerformed
        this.getSelecctionMods().forEach((key,item)->{
            Storage.rename(this.folder_mods+"/"+item+".disabled",this.folder_mods+"/"+item);
        });
        this.getMods();
    }//GEN-LAST:event_btn_active_modsActionPerformed

    private void btn_desactive_modsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_desactive_modsActionPerformed
        this.getSelecctionMods().forEach((key,item)->{
            Storage.rename(this.folder_mods+"/"+item,this.folder_mods+"/"+item+".disabled");
        });
        this.getMods();
    }//GEN-LAST:event_btn_desactive_modsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_active_mods;
    private javax.swing.JButton btn_add_mods;
    private javax.swing.JButton btn_delete_mods;
    private javax.swing.JButton btn_desactive_mods;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_mods;
    // End of variables declaration//GEN-END:variables
}
