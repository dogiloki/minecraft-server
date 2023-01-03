package form;

/**
 *
 * @author dogi_
 */

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import dao.Instance;

import util.Config;
import util.Download;
import util.GsonManager;
import util.Storage;

public class DialogInstance extends javax.swing.JDialog {

    private Config cfg_global;
    private GsonManager versions;
    private Instance ins=null;
    
    public DialogInstance(java.awt.Frame parent, boolean modal, Config cfg_global, Instance ins) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.cfg_global=cfg_global;
        this.ins=ins;
        this.getVersion();
        if(this.ins!=null){
            this.caja_nombre.setText(this.ins.name);
            this.getConfig();
        }
    }
    
    public void getVersion(){
        if(!Storage.exists(this.cfg_global.getDic("fi_ver_mani"))){
            new Download(null,true,this.cfg_global.getDic("fi_ver_mani"),null,this.cfg_global.getDic("url_ver_mani"),null).setVisible(true);
        }
        this.versions=new Config(this.cfg_global.getDic("fi_ver_mani"),true).getJson().getJsonArray("versions");
        DefaultListModel modelo_versiones=new DefaultListModel();
        //Collections.reverse(Arrays.asList(Config.servers)); // Invertir array
        while(this.versions.nextObject()){
            if(!this.versions.getValue("type").equals("release")){
                continue;
            }
            modelo_versiones.addElement(this.versions.getValue("id"));
        }
        this.lista_versiones.setModel(modelo_versiones);
        if(this.ins!=null){
            this.content.setTitleAt(0,"Versión - "+this.ins.version);
            this.lista_versiones.setSelectedValue(this.ins.version,true);
        }
    }
    
    public void getConfig(){
        this.caja_path_java.setText(this.ins.java_path);
        this.caja_memory_min.setText(this.ins.memory_min);
        this.caja_memory_max.setText(this.ins.memory_max);
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
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        caja_path_java = new javax.swing.JTextField();
        btn_reset_path_java = new javax.swing.JButton();
        btn_path_java_explore = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        caja_memory_max = new javax.swing.JTextField();
        caja_memory_min = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btn_reset_memory = new javax.swing.JButton();
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
        );

        content.addTab("Versión", content_version);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Java"));

        jLabel2.setText("Instalación de java");

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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_reset_path_java))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(caja_path_java)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_path_java_explore)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(caja_path_java, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_path_java_explore))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_reset_path_java)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Memoria"));

        jLabel3.setText("Mínima de memoria");

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
                    .addComponent(caja_memory_min)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 503, Short.MAX_VALUE))
                    .addComponent(caja_memory_max)
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
                .addComponent(caja_memory_max, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(caja_memory_min, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_reset_memory)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout content_configLayout = new javax.swing.GroupLayout(content_config);
        content_config.setLayout(content_configLayout);
        content_configLayout.setHorizontalGroup(
            content_configLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, content_configLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(content_configLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        content_configLayout.setVerticalGroup(
            content_configLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(content_configLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
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
                    .addComponent(content)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(content)
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
        String folder=this.ins==null?this.cfg_global.getDic("fo_instances")+"/"+name:this.ins.folder_ins;
        if(name.equals("")){
            JOptionPane.showMessageDialog(null,"Ingrese un nombre para la instancia","Advertencia",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(version==null){
            JOptionPane.showMessageDialog(null,"Seleccione una versión para la instancia","Advertencia",JOptionPane.WARNING_MESSAGE);
            return;
        }
        Storage.createFolder(folder+"/"+this.cfg_global.getDic("fo_server"));
        Instance ins=this.ins==null?new Instance():this.ins;
        ins.file_path=folder+"/"+this.cfg_global.getDic("fi_instance");
        ins.name=name;
        ins.version=version;
        ins.java_path=this.caja_path_java.getText();
        ins.memory_min=this.caja_memory_min.getText();
        ins.memory_max=this.caja_memory_max.getText();
        ins.save();
        dispose();
    }//GEN-LAST:event_btn_okActionPerformed

    private void btn_reset_path_javaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reset_path_javaActionPerformed
        this.caja_path_java.setText(Instance.JAVA_PATH);
    }//GEN-LAST:event_btn_reset_path_javaActionPerformed

    private void btn_reset_memoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reset_memoryActionPerformed
        this.caja_memory_min.setText(Instance.MEMORY_MIN);
        this.caja_memory_max.setText(Instance.MEMORY_MAX);
    }//GEN-LAST:event_btn_reset_memoryActionPerformed

    private void btn_path_java_exploreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_path_java_exploreActionPerformed
        String path_java=Storage.selectFile("");
        if(path_java!=null && !path_java.trim().equals("")){
            this.caja_path_java.setText(path_java);
        }
    }//GEN-LAST:event_btn_path_java_exploreActionPerformed

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
    private javax.swing.JButton btn_ok;
    private javax.swing.JButton btn_path_java_explore;
    private javax.swing.JButton btn_reset_memory;
    private javax.swing.JButton btn_reset_path_java;
    private javax.swing.JTextField caja_memory_max;
    private javax.swing.JTextField caja_memory_min;
    private javax.swing.JTextField caja_nombre;
    private javax.swing.JTextField caja_path_java;
    private javax.swing.JTabbedPane content;
    private javax.swing.JPanel content_config;
    private javax.swing.JPanel content_version;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> lista_versiones;
    // End of variables declaration//GEN-END:variables
}
