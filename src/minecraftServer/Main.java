package minecraftServer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import util.Storage;
import util.Download;
import util.Function;

public class Main extends javax.swing.JFrame{
    
    public Main(){
        initComponents();
        this.setLocationRelativeTo(null);
        if(!Storage.exists("instances")){
            Storage.createFolder("instances");
        }
        this.scroll_panel.getVerticalScrollBar().setUnitIncrement(16);
        this.getInstances();
    }
    
    public void getInstances(){
        int ancho=200, alto=70;
        int ancho_total=this.scroll_panel.getWidth()-20;
        int alto_total=this.scroll_panel.getHeight();
        int total_columnas=(int)Math.floor(ancho_total/ancho)-1;
        System.out.println(total_columnas);
        int x=0, y=0, conta=0, filas=0;
        this.panel_instancias.removeAll();
        for(String ins:Storage.listDirectory("instances",true,false,null)){
            String ruta="instances/"+ins+"/instance.cfg";
            if(Storage.exists(ruta)){
                Config config=new Config(ruta);
                JPanel panel=new JPanel();
                panel.setLayout(null);
                panel.setBounds(x,y,ancho,alto);
                panel.setBackground(Color.white);
                if(x==0){
                    filas++;
                }
                if(conta<total_columnas){
                    x+=ancho;
                    conta++;
                }else{
                    y+=alto;
                    x=0;
                    conta=0;
                }
                
                JLabel version=new JLabel("Versión: "+config.getConfigData("version"));
                version.setBounds(0,0,ancho,alto/3);
                version.setFont(new Font("arial",Font.PLAIN,12));
                panel.add(version);
                
                JLabel nombre=new JLabel(config.getConfigData("name"));
                nombre.setBounds(0,alto/3,ancho,(alto/3)*2);
                nombre.setFont(new Font("arial",Font.PLAIN,16));
                panel.add(nombre);
                
                this.panel_instancias.add(panel);
                this.panel_instancias.updateUI();
            }
        }
        this.panel_instancias.setPreferredSize(Function.createDimencion(ancho_total,filas*alto));
        //this.scroll_panel_peliculas.setVerticalScrollBar(scroll_panel_peliculas.getVerticalScrollBar());
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        caja_versiones = new javax.swing.JComboBox<>();
        btn_descargar_version = new javax.swing.JButton();
        scroll_panel = new javax.swing.JScrollPane();
        panel_instancias = new javax.swing.JPanel();
        btn_crear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        caja_versiones.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                caja_versionesItemStateChanged(evt);
            }
        });

        btn_descargar_version.setText("Descargar");
        btn_descargar_version.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_descargar_versionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(caja_versiones, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_descargar_version))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_descargar_version)
                    .addComponent(caja_versiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_instanciasLayout = new javax.swing.GroupLayout(panel_instancias);
        panel_instancias.setLayout(panel_instanciasLayout);
        panel_instanciasLayout.setHorizontalGroup(
            panel_instanciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 793, Short.MAX_VALUE)
        );
        panel_instanciasLayout.setVerticalGroup(
            panel_instanciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 354, Short.MAX_VALUE)
        );

        scroll_panel.setViewportView(panel_instancias);

        btn_crear.setText("Crear instancia");
        btn_crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_crear))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_crear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_descargar_versionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_descargar_versionActionPerformed
        if(this.btn_descargar_version.getText()=="Actualizar"){
            int op=JOptionPane.showConfirmDialog(null,"Esto volvera a descargar la versión del servidor\nSu configuración y mundos se conservarán","Actualizar servidor",JOptionPane.WARNING_MESSAGE);
            if(op!=0){
                return;
            }
        }
        int index=this.caja_versiones.getSelectedIndex();
        String folder="instances/"+Config.servers[index].version;
        String name="minecraft_server_"+Config.servers[index].version+".jar";
        new Download(this,true,folder,name,Config.servers[index].url,"Conectando con los servidores de mojang...").setVisible(true);
        if(Storage.exists(folder+"/"+name)){
            this.btn_descargar_version.setText("Actualizar");
        }else{
            this.btn_descargar_version.setText("Descargar");
        }
    }//GEN-LAST:event_btn_descargar_versionActionPerformed

    private void caja_versionesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_caja_versionesItemStateChanged
        int index=this.caja_versiones.getSelectedIndex();
        String folder="server/"+Config.servers[index].version+"/minecraft_server_"+Config.servers[index].version+".jar";
        if(Storage.exists(folder)){
            this.btn_descargar_version.setText("Actualizar");
        }else{
            this.btn_descargar_version.setText("Descargar");
        }
    }//GEN-LAST:event_caja_versionesItemStateChanged

    private void btn_crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearActionPerformed
        new Instancia(this,true).setVisible(true);
        this.getInstances();
    }//GEN-LAST:event_btn_crearActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        this.getInstances();
    }//GEN-LAST:event_formComponentResized
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_crear;
    private javax.swing.JButton btn_descargar_version;
    private javax.swing.JComboBox<String> caja_versiones;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panel_instancias;
    private javax.swing.JScrollPane scroll_panel;
    // End of variables declaration//GEN-END:variables
}
