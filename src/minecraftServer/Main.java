package minecraftServer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import util.*;

public class Main extends javax.swing.JFrame{
    
    public Main(){
        initComponents();
        this.setLocationRelativeTo(null);
        if(!Storage.exists(Config.folder_servidores)){
            Storage.createFolder(Config.folder_servidores);
        }
        this.scroll_servidores.getVerticalScrollBar().setUnitIncrement(16);
        this.getServidores();
    }
    
    public void getServidores(){
        int ancho=210, alto=80;
        int ancho_total=this.scroll_servidores.getWidth()-20;
        int total_columnas=(int)Math.floor(ancho_total/ancho)-1;
        int x=0, y=0, conta=0, filas=0;
        this.panel_servidores.removeAll();
        for(String folder:Storage.listDirectory(Config.folder_servidores,true,false,null)){
            String ruta=Config.folder_servidores+"/"+folder+"/"+Config.file_config;
            if(Storage.exists(ruta)){
                Config config=new Config(ruta);
                JPanel panel=new JPanel();
                panel.setLayout(null);
                panel.setBounds(x,y,ancho,alto);
                panel.setOpaque(false);
                panel.setToolTipText(config.getConfigData("name")+" ( "+config.getConfigData("version")+" )");
                panel.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
                panel.addMouseListener(new MouseListener(){
                    @Override
                    public void mouseClicked(MouseEvent evt){}
                    @Override
                    public void mousePressed(MouseEvent evt){
                        Seleccion.panel_mundo=null;
                        if(Seleccion.panel_servidor!=null){
                            Seleccion.panel_servidor.setBackground(null);
                            Seleccion.panel_servidor.setOpaque(false);
                        }
                        Seleccion.panel_servidor=panel;
                        Seleccion.config=config;
                        Seleccion.folder=folder;
                        panel.setBackground(Color.decode("#b2cff0"));
                        panel.setOpaque(true);
                        String version=config.getConfigData("version");
                        String name_file=Config.folder_servidores+"/"+folder+"/"+Config.folder_servidor+version+"/"+Config.file_server+version+".jar";
                        btn_descargar_version.setEnabled(true);
                        if(Storage.exists(name_file)){
                            btn_descargar_version.setText("Actualizar");
                        }else{
                            btn_descargar_version.setText("Descargar");
                        }
                        getMundos();
                    }
                    @Override
                    public void mouseReleased(MouseEvent evt){}
                    @Override
                    public void mouseEntered(MouseEvent evt){
                        if(Seleccion.panel_servidor!=panel){
                            panel.setBackground(new Color(200,200,200));
                            panel.setOpaque(true);
                        }
                    }
                    @Override
                    public void mouseExited(MouseEvent evt){
                        if(Seleccion.panel_servidor!=panel){
                            panel.setBackground(null);
                            panel.setOpaque(false);
                        }
                    }
                });
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
                
                // Componentes
                JLabel version=new JLabel("Versión: "+config.getConfigData("version"));
                version.setBounds(0,0,ancho,alto/3);
                version.setFont(new Font("arial",Font.PLAIN,12));
                version.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                panel.add(version);
                
                JLabel nombre=new JLabel(config.getConfigData("name"));
                nombre.setBounds(0,alto/3,ancho,(alto/3)*2);
                nombre.setFont(new Font("arial",Font.PLAIN,16));
                nombre.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                panel.add(nombre);
                
                // Diseño
                Design.margen(panel,10);
                
                this.panel_servidores.add(panel);
            }
        }
        this.panel_servidores.updateUI();
        this.panel_servidores.setPreferredSize(Function.createDimencion(ancho_total,filas*alto));
        //this.scroll_panel_peliculas.setVerticalScrollBar(scroll_panel_peliculas.getVerticalScrollBar());
    }
    
    public void getMundos(){
        int ancho=170, alto=170;
        int ancho_total=this.scroll_mundos.getWidth()-20;
        int total_columnas=(int)Math.floor(ancho_total/ancho)-1;
        int x=0, y=0, conta=0, filas=0;
        this.panel_mundos.removeAll();
        String dir=Config.folder_servidores+"/"+Seleccion.folder+"/"+Config.folder_servidor+Seleccion.config.getConfigData("version")+"/"+Config.folder_mundos;
        if(!Storage.exists(dir)){
            Storage.createFolder(dir);
        }
        for(String folder:Storage.listDirectory(dir,true,false,null)){
                JPanel panel=new JPanel();
                panel.setLayout(null);
                panel.setBounds(x,y,ancho,alto);
                panel.setOpaque(false);
                panel.setToolTipText(folder);
                panel.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
                panel.addMouseListener(new MouseListener(){
                    @Override
                    public void mouseClicked(MouseEvent evt){}
                    @Override
                    public void mousePressed(MouseEvent evt){
                        if(Seleccion.panel_mundo!=null){
                            Seleccion.panel_mundo.setBackground(null);
                            Seleccion.panel_mundo.setOpaque(false);
                        }
                        Seleccion.panel_mundo=panel;
                        panel.setBackground(Color.decode("#b2cff0"));
                        panel.setOpaque(true);
                    }
                    @Override
                    public void mouseReleased(MouseEvent evt){}
                    @Override
                    public void mouseEntered(MouseEvent evt){
                        if(Seleccion.panel_mundo!=panel){
                            panel.setBackground(new Color(200,200,200));
                            panel.setOpaque(true);
                        }
                    }
                    @Override
                    public void mouseExited(MouseEvent evt){
                        if(Seleccion.panel_mundo!=panel){
                            panel.setBackground(null);
                            panel.setOpaque(false);
                        }
                    }
                });
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
                
                // Componentes
                JLabel icon=new JLabel();
                icon.setBounds(0,0,ancho,(alto/7)*5);
                icon.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                icon.setIcon(new ImageIcon(
                        new ImageIcon(dir+"/"+folder+"/icon.png").getImage().getScaledInstance(icon.getWidth()-30,icon.getHeight()-10,Image.SCALE_DEFAULT)
                ));
                panel.add(icon);
                
                JLabel nombre=new JLabel(folder);
                nombre.setBounds(0,(alto/7)*5,ancho,(alto/7));
                nombre.setFont(new Font("arial",Font.PLAIN,16));
                nombre.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                panel.add(nombre);
                
                // Diseño
                Design.margen(panel,10);
                
                this.panel_mundos.add(panel);
        }
        this.panel_mundos.updateUI();
        this.panel_mundos.setPreferredSize(Function.createDimencion(ancho_total,filas*alto));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll_servidores = new javax.swing.JScrollPane();
        panel_servidores = new javax.swing.JPanel();
        btn_crear = new javax.swing.JButton();
        scroll_mundos = new javax.swing.JScrollPane();
        panel_mundos = new javax.swing.JPanel();
        btn_descargar_version = new javax.swing.JButton();
        btn_inciar = new javax.swing.JButton();
        btn_crear_mundo = new javax.swing.JButton();
        btn_propiedades = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        javax.swing.GroupLayout panel_servidoresLayout = new javax.swing.GroupLayout(panel_servidores);
        panel_servidores.setLayout(panel_servidoresLayout);
        panel_servidoresLayout.setHorizontalGroup(
            panel_servidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 935, Short.MAX_VALUE)
        );
        panel_servidoresLayout.setVerticalGroup(
            panel_servidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 719, Short.MAX_VALUE)
        );

        scroll_servidores.setViewportView(panel_servidores);

        btn_crear.setText("Crear servidor");
        btn_crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_mundosLayout = new javax.swing.GroupLayout(panel_mundos);
        panel_mundos.setLayout(panel_mundosLayout);
        panel_mundosLayout.setHorizontalGroup(
            panel_mundosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 441, Short.MAX_VALUE)
        );
        panel_mundosLayout.setVerticalGroup(
            panel_mundosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 704, Short.MAX_VALUE)
        );

        scroll_mundos.setViewportView(panel_mundos);

        btn_descargar_version.setText("Descargar");
        btn_descargar_version.setEnabled(false);
        btn_descargar_version.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_descargar_versionActionPerformed(evt);
            }
        });

        btn_inciar.setText("Iniciar");
        btn_inciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inciarActionPerformed(evt);
            }
        });

        btn_crear_mundo.setText("Crear mundo");
        btn_crear_mundo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crear_mundoActionPerformed(evt);
            }
        });

        btn_propiedades.setText("Propiedades");
        btn_propiedades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_propiedadesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_crear)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scroll_servidores, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scroll_mundos, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_descargar_version)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_crear_mundo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_propiedades)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_inciar)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_crear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_descargar_version)
                            .addComponent(btn_inciar)
                            .addComponent(btn_crear_mundo)
                            .addComponent(btn_propiedades))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scroll_mundos, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))
                    .addComponent(scroll_servidores, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_descargar_versionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_descargar_versionActionPerformed
        if(this.btn_descargar_version.getText().equals("Actualizar")){
            int op=JOptionPane.showConfirmDialog(null,"Esto volvera a descargar la versión del servidor\nSu configuración y mundos se conservarán","Actualizar servidor",JOptionPane.WARNING_MESSAGE);
            if(op!=0){
                return;
            }
        }
        String version=Seleccion.config.getConfigData("version");
        String nombre=Seleccion.folder;
        String folder=Config.folder_servidores+"/"+nombre+"/"+Config.folder_servidor+version;
        String name=Config.file_server+version+".jar";
        new Download(this,true,folder,name,Config.getUrlVersion(version),"Conectando con los servidores de mojang...").setVisible(true);
        if(Storage.exists(folder+"/"+name)){
            this.btn_descargar_version.setText("Actualizar");
        }else{
            this.btn_descargar_version.setText("Descargar");
        }
    }//GEN-LAST:event_btn_descargar_versionActionPerformed

    private void btn_crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearActionPerformed
        new Servidor(this,true).setVisible(true);
        this.getServidores();
    }//GEN-LAST:event_btn_crearActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        //this.getServidores();
    }//GEN-LAST:event_formComponentResized

    private void btn_inciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inciarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_inciarActionPerformed

    private void btn_crear_mundoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crear_mundoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_crear_mundoActionPerformed

    private void btn_propiedadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_propiedadesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_propiedadesActionPerformed
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_crear;
    private javax.swing.JButton btn_crear_mundo;
    private javax.swing.JButton btn_descargar_version;
    private javax.swing.JButton btn_inciar;
    private javax.swing.JButton btn_propiedades;
    private javax.swing.JPanel panel_mundos;
    private javax.swing.JPanel panel_servidores;
    private javax.swing.JScrollPane scroll_mundos;
    private javax.swing.JScrollPane scroll_servidores;
    // End of variables declaration//GEN-END:variables
}

class Seleccion{
    public static JPanel panel_servidor=null;
    public static JPanel panel_mundo=null;
    public static String folder="";
    public static Config config=null;
}