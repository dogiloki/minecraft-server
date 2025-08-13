package com.dogiloki.minecraftserver.infraestructure.ui.components;

import com.dogiloki.minecraftserver.application.dao.InstanceCfg;
import com.dogiloki.minecraftserver.core.services.Instance;
import com.dogiloki.multitaks.directory.Storage;

/**
 *
 * @author dogi_
 */

public final class ConfigurationPanel extends javax.swing.JPanel {

    private Instance ins=null;
    
    public ConfigurationPanel(Instance ins){
        initComponents();
        this.ins=ins;
        this.getConfig();
    }
    
    public void getConfig(){
        this.path_java_box.setText(this.ins.cfg.java_path);
        this.memory_min_box.setText(this.ins.cfg.memory_min);
        this.memory_max_box.setText(this.ins.cfg.memory_max);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        path_java_box = new javax.swing.JTextField();
        btn_reset_path_java = new javax.swing.JButton();
        btn_path_java_explore = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        memory_min_box = new javax.swing.JTextField();
        memory_max_box = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btn_reset_memory = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Java"));

        jLabel2.setText("Instalación de java");

        path_java_box.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                path_java_boxKeyReleased(evt);
            }
        });

        btn_reset_path_java.setText("Restaurar");
        btn_reset_path_java.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reset_path_javaActionPerformed(evt);
            }
        });

        btn_path_java_explore.setText("Examinar");
        btn_path_java_explore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_path_java_exploreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(path_java_box, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_reset_path_java, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_path_java_explore, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(path_java_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_path_java_explore))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_reset_path_java)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Memoria"));

        jLabel3.setText("Mínima de memoria");

        memory_min_box.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                memory_min_boxKeyReleased(evt);
            }
        });

        memory_max_box.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                memory_max_boxKeyReleased(evt);
            }
        });

        jLabel4.setText("Máxima de memoria");

        btn_reset_memory.setText("Restaurar");
        btn_reset_memory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reset_memoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(memory_max_box)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(memory_min_box)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_reset_memory)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(memory_min_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(memory_max_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_reset_memory)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_reset_path_javaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reset_path_javaActionPerformed
        this.path_java_box.setText(InstanceCfg.JAVA_PATH);
    }//GEN-LAST:event_btn_reset_path_javaActionPerformed

    private void btn_path_java_exploreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_path_java_exploreActionPerformed
        String path_java=Storage.selectFile();
        if(path_java!=null && !path_java.trim().equals("")){
            this.path_java_box.setText(path_java);
            this.ins.cfg.java_path=this.path_java_box.getText();
        }
    }//GEN-LAST:event_btn_path_java_exploreActionPerformed

    private void btn_reset_memoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reset_memoryActionPerformed
        this.memory_max_box.setText(InstanceCfg.MEMORY_MIN);
        this.memory_min_box.setText(InstanceCfg.MEMORY_MAX);
        this.ins.cfg.memory_min=this.memory_min_box.getText();
        this.ins.cfg.memory_max=this.memory_max_box.getText();
    }//GEN-LAST:event_btn_reset_memoryActionPerformed

    private void path_java_boxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_path_java_boxKeyReleased
        this.ins.cfg.java_path=this.path_java_box.getText();
    }//GEN-LAST:event_path_java_boxKeyReleased

    private void memory_min_boxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_memory_min_boxKeyReleased
        this.ins.cfg.memory_min=this.memory_min_box.getText();
    }//GEN-LAST:event_memory_min_boxKeyReleased

    private void memory_max_boxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_memory_max_boxKeyReleased
        this.ins.cfg.memory_max=this.memory_max_box.getText();
    }//GEN-LAST:event_memory_max_boxKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_path_java_explore;
    private javax.swing.JButton btn_reset_memory;
    private javax.swing.JButton btn_reset_path_java;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JTextField memory_max_box;
    public javax.swing.JTextField memory_min_box;
    public javax.swing.JTextField path_java_box;
    // End of variables declaration//GEN-END:variables
}
