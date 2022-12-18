package test.forms;

import util.Config;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import util.*;

public class FormMain extends javax.swing.JFrame{
    
    private Watcher watcher;
    private Config config_global;
    private GsonManager versions;
    
    public FormMain(){
        initComponents();
        this.setLocationRelativeTo(null);
        this.config_global=new Config(this.getClass(),"minecraftServer.cfg",Config.JSON);
        this.config_global.setDic("folder_instances",this.config_global.getConfigJson("folders").getJson("instances").getValue("folder"));
        this.config_global.setDic("folder_server",this.config_global.getConfigJson("folders").getJson("instances").getValue("server"));
        this.config_global.setDic("folder_worlds",this.config_global.getConfigJson("folders").getJson("instances").getValue("worlds"));
        this.config_global.setDic("folder_meta",this.config_global.getConfigJson("folders").getJson("metadata").getValue("folder"));
        this.config_global.setDic("folder_meta_mc",this.config_global.getDic("folder_meta")+"/"+this.config_global.getConfigJson("folders").getJson("metadata").getValue("mc"));
        this.config_global.setDic("folder_meta_fg",this.config_global.getDic("folder_meta")+"/"+this.config_global.getConfigJson("folders").getJson("metadata").getValue("fg"));
        this.config_global.setDic("folder_minecraft_server",this.config_global.getConfigJson("folders").getJson("minecraft-server").getValue("folder"));
        this.config_global.setDic("file_instance",this.config_global.getConfigJson("files").getValue("instance"));
        this.config_global.setDic("file_server",this.config_global.getConfigJson("files").getValue("server"));
        Storage.exists(this.config_global.getDic("folder_instances"),Storage.CREATED,Storage.FOLDER);
        Storage.exists(this.config_global.getDic("folder_meta"),Storage.CREATED,Storage.FOLDER);
        Storage.exists(this.config_global.getDic("folder_meta_mc"),Storage.CREATED,Storage.FOLDER);
        Storage.exists(this.config_global.getDic("folder_meta_fg"),Storage.CREATED,Storage.FOLDER);
        try{
            this.watcher=new Watcher(this.config_global.getDic("folder_instances"),FormMain.class,FormMain.class.getMethod("getInstances"),FormMain.class.getMethod("getMundos"));
        }catch(NoSuchMethodException ex){
            System.out.println(ex);
        }
        String manifest_file=this.config_global.getDic("folder_meta_mc")+"/"+this.config_global.getConfigJson("downloads").getJson("version_manifest").getValue("name");
        String manifest_url=this.config_global.getConfigJson("downloads").getJson("version_manifest").getValue("url");
        this.config_global.setDic("manifest_file",manifest_file);
        if(!Storage.exists(manifest_file)){
            new Download(this,true,manifest_file,null,manifest_url,null).setVisible(true);
        }
        this.versions=new Config(this.config_global.getDic("manifest_file"),true).getJson().getJsonArray("versions");
        this.scroll_servidores.getVerticalScrollBar().setUnitIncrement(16);
        this.getInstances();
    }
    
    public void getInstances(){
        this.panel_mundos.removeAll();
        this.panel_mundos.updateUI();
        Seleccion.panel_servidor=null;
        Seleccion.panel_mundo=null;
        int ancho=210, alto=80;
        int ancho_total=this.scroll_servidores.getWidth()-25;
        int total_columnas=(int)Math.floor(ancho_total/ancho)-1;
        int x=0, y=0, conta=0, filas=0;
        this.panel_servidores.removeAll();
        for(String folder:Storage.listDirectory(this.config_global.getDic("folder_instances"),Storage.FOLDER)){
            String ruta=this.config_global.getDic("folder_instances")+"/"+folder+"/"+this.config_global.getDic("file_instance");
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
                        btn_iniciar_server.setEnabled(true);
                        btn_crear_mundo.setEnabled(true);
                        btn_editar_mundo.setEnabled(false);
                        btn_iniciar.setEnabled(false);
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
        this.panel_mundos.removeAll();
        this.panel_mundos.updateUI();
        if(Seleccion.panel_servidor==null){
            return;
        }
        int ancho=170, alto=170;
        int ancho_total=this.scroll_mundos.getWidth()-25;
        int total_columnas=(int)Math.floor(ancho_total/ancho)-1;
        int x=0, y=0, conta=0, filas=0;
        String dir=this.config_global.getDic("folder_instances")+"/"+Seleccion.folder+"/"+this.config_global.getDic("folder_server")+"/"+this.config_global.getDic("folder_worlds");
        if(!Storage.exists(dir)){
            Storage.createFolder(dir);
        }
        for(String folder:Storage.listDirectory(dir,Storage.FOLDER)){
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
                        btn_editar_mundo.setEnabled(true);
                        btn_iniciar.setEnabled(true);
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
        btn_iniciar_server = new javax.swing.JButton();
        btn_iniciar = new javax.swing.JButton();
        btn_crear_mundo = new javax.swing.JButton();
        btn_editar_mundo = new javax.swing.JButton();
        btn_crear1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        panel_servidores.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout panel_servidoresLayout = new javax.swing.GroupLayout(panel_servidores);
        panel_servidores.setLayout(panel_servidoresLayout);
        panel_servidoresLayout.setHorizontalGroup(
            panel_servidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 473, Short.MAX_VALUE)
        );
        panel_servidoresLayout.setVerticalGroup(
            panel_servidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );

        scroll_servidores.setViewportView(panel_servidores);

        btn_crear.setText("Crear servidor");
        btn_crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crearActionPerformed(evt);
            }
        });

        panel_mundos.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout panel_mundosLayout = new javax.swing.GroupLayout(panel_mundos);
        panel_mundos.setLayout(panel_mundosLayout);
        panel_mundosLayout.setHorizontalGroup(
            panel_mundosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 379, Short.MAX_VALUE)
        );
        panel_mundosLayout.setVerticalGroup(
            panel_mundosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 364, Short.MAX_VALUE)
        );

        scroll_mundos.setViewportView(panel_mundos);

        btn_iniciar_server.setText("Iniciar");
        btn_iniciar_server.setEnabled(false);
        btn_iniciar_server.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_iniciar_serverActionPerformed(evt);
            }
        });

        btn_iniciar.setText("Iniciar");
        btn_iniciar.setEnabled(false);
        btn_iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_iniciarActionPerformed(evt);
            }
        });

        btn_crear_mundo.setText("Crear mundo");
        btn_crear_mundo.setEnabled(false);
        btn_crear_mundo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crear_mundoActionPerformed(evt);
            }
        });

        btn_editar_mundo.setText("Editar mundo");
        btn_editar_mundo.setEnabled(false);
        btn_editar_mundo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editar_mundoActionPerformed(evt);
            }
        });

        btn_crear1.setText("Editar servidor");
        btn_crear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crear1ActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_crear1))
                    .addComponent(scroll_servidores, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll_mundos, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_iniciar_server)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_crear_mundo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_editar_mundo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_iniciar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_crear)
                    .addComponent(btn_crear1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_iniciar_server)
                            .addComponent(btn_iniciar)
                            .addComponent(btn_crear_mundo)
                            .addComponent(btn_editar_mundo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scroll_mundos, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))
                    .addComponent(scroll_servidores, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_iniciar_serverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_iniciar_serverActionPerformed
        String version=Seleccion.config.getConfigData("version");
        String java_path=Seleccion.config.getConfigData("JavaPath");
        String folder_meta_mc=this.config_global.getDic("folder_meta_mc");
        String folder_start=this.config_global.getDic("folder_instances")+"/"+Seleccion.folder+"/"+this.config_global.getDic("folder_server");
        String file_start=folder_start+"/start.bat";
        String name_json=version+".json";
        if(!Storage.exists(folder_meta_mc+"/"+name_json)){
            new Download(this,true,folder_meta_mc,name_json,this.versions.searchArray(this.versions,"id",version).getValue("url"),null).setVisible(true);
        }
        String url=new GsonManager(folder_meta_mc+"/"+name_json,GsonManager.FILE).getJson("downloads").getJson("server").getValue("url");
        String folder_mc_server=this.config_global.getDic("folder_minecraft_server")+"/"+version;
        String name_jar=MessageFormat.format(this.config_global.getDic("file_server"),version);
        String file=folder_mc_server+"/"+name_jar;
        if(Storage.exists(folder_mc_server+"/"+name_jar)){
            String[] lineas={
                "cd "+folder_start,
                java_path+" -jar ../../../"+file+" nogui"
            };
            if(!Storage.exists(file_start)){
                Storage.writeFile(lineas,file_start);
            }
            Storage.exists(folder_start+"/server.properties",Storage.CREATED,Storage.FILE);
            Config config_properties=new Config(folder_start+"/server.properties");
            config_properties.setConfigData("level-name","saves/world");
            //Storage.execute(file_start,false);
            Config config_eula=new Config(folder_start+"/eula.txt");
            if(config_eula.getConfigData("eula").equals("false")){
                int op=JOptionPane.showInternalConfirmDialog(null,"By changing the setting below to TRUE\nyou are indicating your agreement to our EULA\n( https://account.mojang.com/documents/minecraft_eula ).","EULA",JOptionPane.WARNING_MESSAGE);
                if(op==JOptionPane.YES_OPTION){
                    config_eula.setConfigData("eula","true");
                }
            }
        }else{
            new Download(this,true,folder_mc_server,name_jar,url,null).setVisible(true);
        }
    }//GEN-LAST:event_btn_iniciar_serverActionPerformed

    private void btn_crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearActionPerformed
        new FormInstance(this,true,this.config_global).setVisible(true);
    }//GEN-LAST:event_btn_crearActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        //this.getServidores();
    }//GEN-LAST:event_formComponentResized

    private void btn_iniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_iniciarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_iniciarActionPerformed

    private void btn_crear_mundoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crear_mundoActionPerformed
        this.watcher.terminar=true;
    }//GEN-LAST:event_btn_crear_mundoActionPerformed

    private void btn_editar_mundoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editar_mundoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_editar_mundoActionPerformed

    private void btn_crear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crear1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_crear1ActionPerformed
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                new FormMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_crear;
    private javax.swing.JButton btn_crear1;
    private javax.swing.JButton btn_crear_mundo;
    private javax.swing.JButton btn_editar_mundo;
    private javax.swing.JButton btn_iniciar;
    private javax.swing.JButton btn_iniciar_server;
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