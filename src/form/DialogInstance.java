package form;

/**
 *
 * @author dogi_
 */

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import dto.Instance;

import util.Config;
import util.Download;
import util.GsonManager;
import util.Storage;

public class DialogInstance extends javax.swing.JDialog {

    private Config cfg_global;
    private GsonManager versions;
    
    public DialogInstance(java.awt.Frame parent, boolean modal, Config cfg_global) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.cfg_global=cfg_global;
        this.getVersion();
    }
    
    public void getVersion(){
        if(!Storage.exists(this.cfg_global.getDic("fi_ver_mani"))){
            new Download(null,true,this.cfg_global.getDic("fi_ver_mani"),null,this.cfg_global.getDic("url_ver_mani"),null).setVisible(true);
        }
        this.versions=new Config(this.cfg_global.getDic("fi_ver_mani"),true).getJson().getJsonArray("versions");
        DefaultListModel modelo_versiones=new DefaultListModel();
        //Collections.reverse(Arrays.asList(Config.servers)); // Invertir array
        while(this.versions.nextObject()){
            if(this.versions.getValue("type").equals("release")){
                modelo_versiones.addElement(this.versions.getValue("id"));
            }
        }
        this.lista_versiones.setModel(modelo_versiones);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        caja_nombre = new javax.swing.JTextField();
        content = new javax.swing.JTabbedPane();
        content_version = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista_versiones = new javax.swing.JList<>();
        content_config = new javax.swing.JPanel();
        btn_ok = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Nombre:");

        lista_versiones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lista_versiones.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lista_versionesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lista_versiones);

        javax.swing.GroupLayout content_versionLayout = new javax.swing.GroupLayout(content_version);
        content_version.setLayout(content_versionLayout);
        content_versionLayout.setHorizontalGroup(
            content_versionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
        );
        content_versionLayout.setVerticalGroup(
            content_versionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
        );

        content.addTab("Versión", content_version);

        javax.swing.GroupLayout content_configLayout = new javax.swing.GroupLayout(content_config);
        content_config.setLayout(content_configLayout);
        content_configLayout.setHorizontalGroup(
            content_configLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 649, Short.MAX_VALUE)
        );
        content_configLayout.setVerticalGroup(
            content_configLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 294, Short.MAX_VALUE)
        );

        content.addTab("Configuración", content_config);

        btn_ok.setText("OK");
        btn_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_okActionPerformed(evt);
            }
        });

        btn_cancelar.setText("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(caja_nombre))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancelar)
                    .addComponent(btn_ok))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lista_versionesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lista_versionesValueChanged
        this.content.setTitleAt(0,"Versión - "+this.lista_versiones.getSelectedValue());
    }//GEN-LAST:event_lista_versionesValueChanged

    private void btn_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_okActionPerformed
        String version=this.lista_versiones.getSelectedValue();
        String name=this.caja_nombre.getText();
        String folder=this.cfg_global.getDic("fo_instances")+"/"+name;
        if(name.equals("")){
            JOptionPane.showMessageDialog(null,"Ingrese un nombre para la instancia","Advertencia",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(version==null){
            JOptionPane.showMessageDialog(null,"Seleccione una versión para la instancia","Advertencia",JOptionPane.WARNING_MESSAGE);
            return;
        }
        Storage.createFolder(folder+"/"+this.cfg_global.getDic("fo_server"));
        Instance ins=new Instance();
        ins.file_path=folder+"/"+this.cfg_global.getDic("fi_instance");
        ins.name=name;
        ins.version=version;
        ins.java_path="java";
        ins.save();
        dispose();
    }//GEN-LAST:event_btn_okActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogInstance dialog = new DialogInstance(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JButton btn_ok;
    private javax.swing.JTextField caja_nombre;
    private javax.swing.JTabbedPane content;
    private javax.swing.JPanel content_config;
    private javax.swing.JPanel content_version;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> lista_versiones;
    // End of variables declaration//GEN-END:variables
}
