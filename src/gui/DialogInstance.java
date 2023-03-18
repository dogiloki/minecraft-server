package gui;

/**
 *
 * @author dogi_
 */

import javax.swing.JOptionPane;

import dao.Instance;
import gui.instance.Configuration;
import gui.instance.Properties;
import gui.instance.Mods;

import util.Config;
import util.dataformat.GsonManager;
import util.StorageOld;
import gui.instance.Version;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import util.directory.Storage;

public class DialogInstance extends javax.swing.JDialog {

    private Config cfg_global;
    private GsonManager versions;
    private Instance ins=null;
    private Version panel_version;
    private Properties panel_properties;
    private JPanel panel_mods;
    private Configuration panel_configuration;
    
    public DialogInstance(java.awt.Frame parent, boolean modal, Config cfg_global, Instance ins) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.cfg_global=cfg_global;
        this.ins=ins;
        if(this.ins==null){
            this.ins=new Instance();
            this.btn_mods.setEnabled(false);
        }else{
            this.setTitle(this.ins.name+" - "+this.ins.version);
        }
        this.getConfig();
        // Agregar paneles
        this.panel_version=(Version)this.resizablePanel(new Version(this.cfg_global,this.ins,this));
        this.panel_properties=(Properties)this.resizablePanel(new Properties(this.cfg_global,this.ins));
        this.panel_configuration=(Configuration)this.resizablePanel(new Configuration(this.cfg_global,this.ins));
        this.panel_mods=(Mods)this.resizablePanel(new Mods(this.cfg_global,this.ins));
        this.setPanel(this.panel_version);
    }
    
    public void getConfig(){
        this.caja_nombre.setText(this.ins.name);
    }
    
    public JPanel resizablePanel(JPanel panel){
        panel.setSize(this.panel_frame.getWidth(),this.panel_frame.getHeight());
        panel.setLocation(0,0);
        return panel;
    }
    
    public void setPanel(JPanel panel){
        this.panel_frame.removeAll();
        this.panel_frame.add(panel,BorderLayout.CENTER);
        this.panel_frame.revalidate();
        this.panel_frame.repaint();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        caja_nombre = new javax.swing.JTextField();
        btn_ok = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        panel_menu = new javax.swing.JPanel();
        btn_version = new javax.swing.JButton();
        btn_properties = new javax.swing.JButton();
        btn_mods = new javax.swing.JButton();
        btn_configuration = new javax.swing.JButton();
        panel_frame = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Nombre:");

        caja_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                caja_nombreKeyReleased(evt);
            }
        });

        btn_ok.setText("OK");
        btn_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_okActionPerformed(evt);
            }
        });

        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        panel_menu.setLayout(new java.awt.GridLayout(0, 1));

        btn_version.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btn_version.setText("Vesión");
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

        btn_mods.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btn_mods.setText("Mods");
        btn_mods.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_mods.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btn_mods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modsActionPerformed(evt);
            }
        });
        panel_menu.add(btn_mods);

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

        javax.swing.GroupLayout panel_frameLayout = new javax.swing.GroupLayout(panel_frame);
        panel_frame.setLayout(panel_frameLayout);
        panel_frameLayout.setHorizontalGroup(
            panel_frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 761, Short.MAX_VALUE)
        );
        panel_frameLayout.setVerticalGroup(
            panel_frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

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
                        .addComponent(btn_ok)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cancelar)))
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
                    .addComponent(panel_menu, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(panel_frame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancelar)
                    .addComponent(btn_ok))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_okActionPerformed
        String name=this.caja_nombre.getText();
        String folder=this.ins.file_path==null?this.cfg_global.getDic("fo_instances")+"/"+name:this.ins.folder_ins;
        if(name.equals("")){
            JOptionPane.showMessageDialog(null,"Ingrese un nombre para la instancia","Advertencia",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(this.ins.version==null){
            JOptionPane.showMessageDialog(null,"Seleccione una versión para la instancia","Advertencia",JOptionPane.WARNING_MESSAGE);
            return;
        }
        Storage.createFolder(folder+"/"+this.cfg_global.getDic("fo_server"));
        Instance ins=this.ins==null?new Instance():this.ins;
        ins.file_path=folder+"/"+this.cfg_global.getDic("fi_instance");
        ins.name=name;
        ins.file_server=this.panel_version.caja_path_java.getText();
        ins.java_path=this.panel_configuration.caja_path_java.getText();
        ins.memory_min=this.panel_configuration.caja_memory_min.getText();
        ins.memory_max=this.panel_configuration.caja_memory_max.getText();
        ins.properties.file_path=folder+"/"+cfg_global.getDic("fo_server")+"/server.properties";
        ins.save();
        dispose();
    }//GEN-LAST:event_btn_okActionPerformed

    private void btn_versionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_versionActionPerformed
        this.setPanel(this.panel_version);
    }//GEN-LAST:event_btn_versionActionPerformed

    private void btn_propertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_propertiesActionPerformed
        this.setPanel(this.panel_properties);
    }//GEN-LAST:event_btn_propertiesActionPerformed

    private void btn_modsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modsActionPerformed
        this.setPanel(this.panel_mods);
    }//GEN-LAST:event_btn_modsActionPerformed

    private void btn_configurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_configurationActionPerformed
        this.setPanel(this.panel_configuration);
    }//GEN-LAST:event_btn_configurationActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void caja_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_caja_nombreKeyReleased
        this.ins.name=this.caja_nombre.getText();
        this.setTitle(this.ins.name+" - "+(this.ins.version==null?"":this.ins.version));
    }//GEN-LAST:event_caja_nombreKeyReleased

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogInstance dialog = new DialogInstance(new javax.swing.JFrame(), true, null, null);
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
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_configuration;
    private javax.swing.JButton btn_mods;
    private javax.swing.JButton btn_ok;
    private javax.swing.JButton btn_properties;
    private javax.swing.JButton btn_version;
    private javax.swing.JTextField caja_nombre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panel_frame;
    private javax.swing.JPanel panel_menu;
    // End of variables declaration//GEN-END:variables
}
