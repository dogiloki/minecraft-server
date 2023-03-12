package gui.instance;

import dao.Instance;
import java.text.MessageFormat;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;
import util.Config;
import util.Download;
import util.Function;
import util.GsonManager;
import util.Storage;

/**
 *
 * @author dogi_
 */
public class Version extends javax.swing.JPanel {

    private Config cfg_global;
    private GsonManager versions;
    private Instance ins=null;
    private JDialog frame;
    
    public Version(Config cfg_global, Instance ins, JDialog frame){
        initComponents();
        this.frame=frame;
        this.cfg_global=cfg_global;
        this.ins=ins;
        this.table_versions.setDefaultEditor(Object.class,null);
        this.getVersion();
    }
    
    public void getVersion(){
        if(!Storage.exists(this.cfg_global.getDic("fi_ver_mani"))){
            new Download(null,true,this.cfg_global.getDic("fi_ver_mani"),null,this.cfg_global.getDic("url_ver_mani"),null).setVisible(true);
        }
        this.versions=new Config(this.cfg_global.getDic("fi_ver_mani"),true).getJson().getJsonArray("versions");
        DefaultTableModel modelo_versions=new DefaultTableModel();
        modelo_versions.addColumn("Versión");
        modelo_versions.addColumn("Estado");
        //Collections.reverse(Arrays.asList(Config.servers)); // Invertir array
        Integer row_selection=null;
        int count=0;
        while(this.versions.nextObject()){
            if(!this.versions.getValue("type").equals("release")){
                continue;
            }
            String status="";
            String fo_minecraft=this.cfg_global.getDic("fo_mc")+"/"+this.versions.getValue("id");
            String fi_server=Function.assign(this.ins.file_server,MessageFormat.format(this.cfg_global.getDic("fi_server"),this.versions.getValue("id")));
            if(Storage.exists(fo_minecraft+"/"+fi_server)){
                status="Descargado";
            }
            Object[] data={this.versions.getValue("id"),status};
            modelo_versions.addRow(data);
            if(row_selection==null && this.versions.getValue("id").equals(this.ins.version)){
                row_selection=count;
            }
            count++;
        }
        this.table_versions.setModel(modelo_versions);
        if(row_selection==null){
            this.table_versions.setRowSelectionInterval(0,0);
        }else{
            this.table_versions.setRowSelectionInterval(row_selection, row_selection);
        }
        int row=this.table_versions.getSelectedRow();
        this.ins.version=this.table_versions.getValueAt(row,0).toString();
        this.frame.setTitle(this.ins.name+" - "+this.ins.version);
        this.caja_path_java.setText(this.ins.file_server);
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
        String fi_server=Storage.selectFile(this.cfg_global.getDic("fo_mc"));
        if(fi_server==null){
            return;
        }
        String fi_server_temp[]=fi_server.replace("\\","/").split("/");
        this.ins.file_server=fi_server_temp[fi_server_temp.length-1];
        this.caja_path_java.setText(this.ins.file_server);
    }//GEN-LAST:event_btn_path_java_explore4ActionPerformed

    private void table_versionsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_versionsMousePressed
        int row=this.table_versions.getSelectedRow();
        this.ins.version=this.table_versions.getValueAt(row,0).toString();
        this.frame.setTitle(this.ins.name+" - "+this.ins.version);
    }//GEN-LAST:event_table_versionsMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_path_java_explore4;
    public javax.swing.JTextField caja_path_java;
    private javax.swing.JPanel content_version;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable table_versions;
    // End of variables declaration//GEN-END:variables
}
