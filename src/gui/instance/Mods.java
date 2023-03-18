package gui.instance;

import dao.Instance;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import multitaks.Config;
import multitaks.dataformat.GsonManager;
import multitaks.StorageOld;
import multitaks.directory.Storage;
import multitaks.enums.DirectoryType;

/**
 *
 * @author dogi_
 */
public class Mods extends javax.swing.JPanel {
    
    private Config cfg_global;
    private GsonManager versions;
    private Instance ins=null;
    private String folder_mods="";
    
    public Mods(Config cfg_global, Instance ins){
        initComponents();
        this.cfg_global=cfg_global;
        this.ins=ins;
        this.folder_mods+=this.ins.folder_ins+"/"+this.cfg_global.getDic("fo_server")+"/mods";
        Storage.createFolder(this.folder_mods);
        this.getMods();
    }
    
    public void getMods(){
        DefaultTableModel model_mods=new DefaultTableModel();
        model_mods.addColumn("Activo");
        model_mods.addColumn("Nombre");
        String[] mods=Storage.listDirectory(this.folder_mods,DirectoryType.FILE);
        if(mods==null){
            return;
        }
        for(String folder:mods){
            boolean status=Storage.getExtension(folder).equals("disabled");
            Object[] data={(status?"":"Activo"),(folder).replace(".disabled","")};
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
                StorageOld.copyFile(file.getPath(),this.folder_mods+"/"+name);
            }catch(Exception ex){
                ex.printStackTrace();
                return;
            }
        }
        this.getMods();
    }//GEN-LAST:event_btn_add_modsActionPerformed

    private void btn_delete_modsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_modsActionPerformed
        this.getSelecctionMods().forEach((key,item)->{
            StorageOld.deleteFile(this.folder_mods+"/"+item);
        });
        this.getMods();
    }//GEN-LAST:event_btn_delete_modsActionPerformed

    private void btn_active_modsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_active_modsActionPerformed
        this.getSelecctionMods().forEach((key,item)->{
            StorageOld.rename(this.folder_mods+"/"+item+".disabled",this.folder_mods+"/"+item);
        });
        this.getMods();
    }//GEN-LAST:event_btn_active_modsActionPerformed

    private void btn_desactive_modsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_desactive_modsActionPerformed
        this.getSelecctionMods().forEach((key,item)->{
            StorageOld.rename(this.folder_mods+"/"+item,this.folder_mods+"/"+item+".disabled");
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
