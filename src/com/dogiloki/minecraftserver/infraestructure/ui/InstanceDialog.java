package com.dogiloki.minecraftserver.infraestructure.ui;

import com.dogiloki.minecraftserver.application.dao.Properties;
import com.dogiloki.minecraftserver.core.services.Instance;
import com.dogiloki.minecraftserver.infraestructure.ui.components.ConfigurationPanel;
import com.dogiloki.minecraftserver.infraestructure.ui.components.ServerPropertiesPanel;
import com.dogiloki.minecraftserver.infraestructure.ui.components.VersionPanel;
import com.dogiloki.multitaks.Function;
import com.dogiloki.multitaks.logger.AppLogger;

/**
 *
 * @author dogi_
 */

public final class InstanceDialog extends javax.swing.JDialog{

    private Instance ins;
    private final VersionPanel panel_version;
    private final ServerPropertiesPanel panel_server_properties;
    private final ConfigurationPanel panel_configuration;
    
    public InstanceDialog(java.awt.Frame parent, boolean modal, Instance ins) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.ins=ins;
        if(this.ins==null){
            this.ins=new Instance();
        }else{
            this.setTitle(this.ins.cfg.name+" - "+this.ins.cfg.version);
        }
        this.getConfig();
        // Agregar paneles
        this.panel_version=new VersionPanel(this.ins,this);
        this.panel_server_properties=new ServerPropertiesPanel(this.ins);
        this.panel_configuration=new ConfigurationPanel(this.ins);
        Function.setPanel(this.panel_frame,this.panel_version);
    }
    
    public void getConfig(){
        this.caja_nombre.setText(this.ins.cfg.name);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        caja_nombre = new javax.swing.JTextField();
        btn_save = new javax.swing.JButton();
        panel_menu = new javax.swing.JPanel();
        btn_version = new javax.swing.JButton();
        btn_properties = new javax.swing.JButton();
        btn_configuration = new javax.swing.JButton();
        panel_frame = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Nombre:");

        caja_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                caja_nombreKeyReleased(evt);
            }
        });

        btn_save.setText("Guardar");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        panel_menu.setLayout(new java.awt.GridLayout(0, 1));

        btn_version.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btn_version.setText("Versión");
        btn_version.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_version.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btn_version.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_versionActionPerformed(evt);
            }
        });
        panel_menu.add(btn_version);

        btn_properties.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btn_properties.setText("Propiedades");
        btn_properties.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_properties.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btn_properties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_propertiesActionPerformed(evt);
            }
        });
        panel_menu.add(btn_properties);

        btn_configuration.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btn_configuration.setText("Configuración");
        btn_configuration.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_configuration.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btn_configuration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_configurationActionPerformed(evt);
            }
        });
        panel_menu.add(btn_configuration);

        panel_frame.setMaximumSize(new java.awt.Dimension(700, 400));
        panel_frame.setMinimumSize(new java.awt.Dimension(700, 400));
        panel_frame.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(caja_nombre))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel_frame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_save)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(caja_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_menu, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                    .addComponent(panel_frame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_save)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        if(!this.ins.save(Properties.folders.instances_folder+"/"+this.ins.cfg.name)){
            AppLogger.logger().showMessage();
            AppLogger.error("Error al crear instancia");
        }
        dispose();
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_versionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_versionActionPerformed
        Function.setPanel(this.panel_frame,this.panel_version);
    }//GEN-LAST:event_btn_versionActionPerformed

    private void btn_propertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_propertiesActionPerformed
        Function.setPanel(this.panel_frame,this.panel_server_properties);
    }//GEN-LAST:event_btn_propertiesActionPerformed

    private void btn_configurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_configurationActionPerformed
        Function.setPanel(this.panel_frame,this.panel_configuration);
    }//GEN-LAST:event_btn_configurationActionPerformed

    private void caja_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_caja_nombreKeyReleased
        this.ins.cfg.name=this.caja_nombre.getText();
        this.setTitle(this.ins.cfg.name+" - "+(this.ins.cfg.version==null?"":this.ins.cfg.version));
    }//GEN-LAST:event_caja_nombreKeyReleased

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InstanceDialog dialog=new InstanceDialog(new javax.swing.JFrame(),true,null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_configuration;
    private javax.swing.JButton btn_properties;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_version;
    private javax.swing.JTextField caja_nombre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panel_frame;
    private javax.swing.JPanel panel_menu;
    // End of variables declaration//GEN-END:variables
}
