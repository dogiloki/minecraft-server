package form;

/**
 *
 * @author dogi_
 */

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import dto.Instance;
import dto.World;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.MessageFormat;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import util.Storage;
import util.Config;
import util.Design;
import util.Download;
import util.Function;
import util.GsonManager;
import util.Watcher;

public class FormMain extends javax.swing.JFrame {
    
    private Watcher watcher;
    private ArrayList<Instance> instances=new ArrayList<>();
    private Instance sele_instance=null;
    private Config cfg_global;

    public FormMain() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.dic();
        this.getInstances();
        // Detectar cambios en carpetas
        this.runWath();
    }
    
    // Agregar valores a un diccionario
    public void dic(){
        // Obtener configuración del archivo central .cfg
        this.cfg_global=new Config(this.getClass(),"minecraftServer.cfg",Config.JSON);
        // Carpetas
        this.cfg_global.setDic("fo_instances",this.cfg_global.getConfigJson("folders").getJson("instances").getValue("folder"));
        this.cfg_global.setDic("fo_server",this.cfg_global.getConfigJson("folders").getJson("instances").getValue("server"));
        this.cfg_global.setDic("fo_worlds",this.cfg_global.getConfigJson("folders").getJson("instances").getValue("worlds"));
        this.cfg_global.setDic("fo_metadata",this.cfg_global.getConfigJson("folders").getJson("metadata").getValue("folder"));
        this.cfg_global.setDic("fo_meta_mc",this.cfg_global.getDic("fo_metadata")+"/"+this.cfg_global.getConfigJson("folders").getJson("metadata").getValue("mc"));
        this.cfg_global.setDic("fo_meta_fg",this.cfg_global.getDic("fo_metadata")+"/"+this.cfg_global.getConfigJson("folders").getJson("metadata").getValue("fg"));
        this.cfg_global.setDic("fo_mc",this.cfg_global.getConfigJson("folders").getJson("minecraft-server").getValue("folder"));
        // Archivos
        this.cfg_global.setDic("fi_instance",this.cfg_global.getConfigJson("files").getValue("instance"));
        this.cfg_global.setDic("fi_server",this.cfg_global.getConfigJson("files").getValue("server"));
        this.cfg_global.setDic("fi_ver_mani",this.cfg_global.getDic("fo_meta_mc")+"/"+this.cfg_global.getConfigJson("downloads").getJson("version_manifest").getValue("name"));
        this.cfg_global.setDic("url_ver_mani",this.cfg_global.getConfigJson("downloads").getJson("version_manifest").getValue("url"));
    }
    
    public void runWath(){
        this.watcher=new Watcher(this.cfg_global.getDic("fo_instances"),this,FormMain.class,"getInstances","getWorlds");
    }
    
    public void getInstances(){
        this.panel_instances.removeAll();
        this.scroll_instances.updateUI();
        String path_instances=this.cfg_global.getDic("fo_instances");
        String file_instance=this.cfg_global.getDic("fi_instance");
        String[] folders=Storage.listDirectory(path_instances,true,true,null);
        // Valores del panel en cada instancia
        int ancho=210, alto=80;
        int ancho_total=this.scroll_instances.getWidth()-25;
        int total_columnas=(int)(Math.floorDiv(ancho_total,ancho))-1;
        int x=0, y=0, count=0, filas=0;
        for(String folder:folders){
            folder=path_instances+"/"+folder;
            if(!Storage.exists(folder+"/"+file_instance)){
                continue;
            }
            // Obtener datos de la instancia
            Instance ins=new Instance(folder+"/"+file_instance);
            
            // Panel principal
            JPanel panel=new JPanel();
            panel.setLayout(null);
            panel.setBounds(x,y,ancho,alto);
            panel.setOpaque(false);
            panel.setToolTipText(ins.name+" ( "+ins.version+" ) ");
            panel.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
            panel.addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent evt){}
                @Override
                public void mousePressed(MouseEvent evt){
                    if(sele_instance!=null){
                        sele_instance.panel_ins.setBackground(null);
                        sele_instance.panel_ins.setOpaque(false);
                    }
                    sele_instance=ins;
                    sele_instance.panel_ins.setBackground(Color.decode("#b2cff0"));
                    sele_instance.panel_ins.setOpaque(true);
                    getWorlds();
                }
                @Override
                public void mouseReleased(MouseEvent evt){ }
                @Override
                public void mouseEntered(MouseEvent evt){
                    if(sele_instance!=null && sele_instance.panel_ins!=panel){
                        panel.setBackground(new Color(200,200,200));
                        panel.setOpaque(true);
                    }
                }
                @Override
                public void mouseExited(MouseEvent evt){
                    if(sele_instance!=null && sele_instance.panel_ins!=panel){
                        panel.setBackground(null);
                        panel.setOpaque(false);
                    }
                }
            });
            
            // Almacena instancias
            ins.panel_ins=panel;
            ins.panel_world=null;
            ins.folder_ins=folder;
            ins.folder_world="";
            ins.world=null;
            this.instances.add(ins);
            
            // Calculo para posicionar componentes
            if(x==0){
                filas++;
            }
            if(count<total_columnas){
                x+=ancho;
                count++;
            }else{
                y+=alto;
                x=0;
                count=0;
            }
            
            // Compoente Versión
            JLabel label_vesion=new JLabel("Versión: "+ins.version);
            label_vesion.setBounds(0,0,ancho,alto/3);
            label_vesion.setFont(new Font("arial",Font.PLAIN,12));
            label_vesion.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            panel.add(label_vesion);
            
            // Compoente Nombre
            JLabel label_nombre=new JLabel(ins.name);
            label_nombre.setBounds(0,alto/3,ancho,(alto/3)*2);
            label_nombre.setFont(new Font("arial",Font.PLAIN,16));
            label_nombre.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            panel.add(label_nombre);
            
            // Diseño
            Design.margen(panel,10);
            
            // Agregar a la GUI
            this.panel_instances.add(panel);
        }
        // Adaptar panel de las instancias
        this.panel_instances.updateUI();
        this.panel_instances.setPreferredSize(Function.createDimencion(ancho_total,filas*alto));
    }
    
    public void getWorlds(){
        this.panel_worlds.removeAll();
        this.panel_worlds.updateUI();
        if(this.sele_instance==null){
            return;
        }
        String path_worlds=this.sele_instance.folder_ins+"/"+this.cfg_global.getDic("fo_server")+"/"+this.cfg_global.getDic("fo_worlds");
        Storage.exists("path_worlds",Storage.CREATED,Storage.FOLDER);
        String[] folders=Storage.listDirectory(path_worlds,true,true,null);
        // Valores del panel en cada instancia
        int ancho=170, alto=170;
        int ancho_total=this.scroll_mundos.getWidth()-25;
        int total_columnas=(int)Math.floorDiv(ancho_total, ancho)-1;
        int x=0, y=0, count=0, filas=0;
        if(folders==null || folders.length<=0){
            return;
        }
        for(String folder:folders){
            // Obtener datos de la World
            World world=new World(folder);
            
            // Panel principal
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
                    btn_iniciar_server.setEnabled(true);
                    btn_crear_mundo.setEnabled(true);
                    btn_editar_mundo.setEnabled(true);
                    btn_eliminar_mundo.setEnabled(true);
                    if(sele_instance.panel_world!=null){
                        sele_instance.panel_world.setBackground(null);
                        sele_instance.panel_world.setOpaque(false);
                    }
                    sele_instance.world=world;
                    sele_instance.panel_world=panel;
                    sele_instance.panel_world.setBackground(Color.decode("#b2cff0"));
                    sele_instance.panel_world.setOpaque(true);
                }
                @Override
                public void mouseReleased(MouseEvent evt){ }
                @Override
                public void mouseEntered(MouseEvent evt){
                    if(sele_instance!=null && sele_instance.panel_world!=panel){
                        panel.setBackground(new Color(200,200,200));
                        panel.setOpaque(true);
                    }
                }
                @Override
                public void mouseExited(MouseEvent evt){
                    if(sele_instance!=null && sele_instance.panel_world!=panel){
                        panel.setBackground(null);
                        panel.setOpaque(false);
                    }
                }
            });
            
            // Calculo para posicionar componentes
            if(x==0){
                filas++;
            }
            if(count<total_columnas){
                x+=ancho;
                count++;
            }else{
                y+=alto;
                x=0;
                count=0;
            }
            
            // Compoente Versión
            JLabel label_icon=new JLabel();
            label_icon.setBounds(0,0,ancho,(alto/7)*5);
            label_icon.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            // Datos del World
            world.icon=new ImageIcon(new ImageIcon(path_worlds+"/"+folder+"/icon.png").getImage().getScaledInstance(label_icon.getWidth()-30, label_icon.getHeight()-10,Image.SCALE_DEFAULT));
            // Agregar icon al panel
            label_icon.setIcon(world.icon);
            panel.add(label_icon);
            
            // Compoente Nombre
            JLabel label_nombre=new JLabel(folder);
            label_nombre.setBounds(0,(alto/7)*5,ancho,alto/7);
            label_nombre.setFont(new Font("arial",Font.PLAIN,16));
            label_nombre.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            panel.add(label_nombre);
            
            // Diseño
            Design.margen(panel,10);
            
            // Agregar a la GUI
            this.panel_worlds.add(panel);
        }
        // Adaptar panel de las instancias
        this.panel_worlds.updateUI();
        this.panel_worlds.setPreferredSize(Function.createDimencion(ancho_total,filas*alto));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll_instances = new javax.swing.JScrollPane();
        panel_instances = new javax.swing.JPanel();
        btn_crear = new javax.swing.JButton();
        btn_iniciar_server = new javax.swing.JButton();
        btn_eliminar_mundo = new javax.swing.JButton();
        btn_crear_mundo = new javax.swing.JButton();
        btn_editar_mundo = new javax.swing.JButton();
        scroll_mundos = new javax.swing.JScrollPane();
        panel_worlds = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        scroll_instances.setPreferredSize(new java.awt.Dimension(918, 445));

        javax.swing.GroupLayout panel_instancesLayout = new javax.swing.GroupLayout(panel_instances);
        panel_instances.setLayout(panel_instancesLayout);
        panel_instancesLayout.setHorizontalGroup(
            panel_instancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 542, Short.MAX_VALUE)
        );
        panel_instancesLayout.setVerticalGroup(
            panel_instancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 444, Short.MAX_VALUE)
        );

        scroll_instances.setViewportView(panel_instances);

        btn_crear.setText("Crear instancia");
        btn_crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crearActionPerformed(evt);
            }
        });

        btn_iniciar_server.setText("Iniciar");
        btn_iniciar_server.setEnabled(false);
        btn_iniciar_server.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_iniciar_serverActionPerformed(evt);
            }
        });

        btn_eliminar_mundo.setText("Eliminar mundo");
        btn_eliminar_mundo.setEnabled(false);
        btn_eliminar_mundo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_mundoActionPerformed(evt);
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

        javax.swing.GroupLayout panel_worldsLayout = new javax.swing.GroupLayout(panel_worlds);
        panel_worlds.setLayout(panel_worldsLayout);
        panel_worldsLayout.setHorizontalGroup(
            panel_worldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 353, Short.MAX_VALUE)
        );
        panel_worldsLayout.setVerticalGroup(
            panel_worldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 414, Short.MAX_VALUE)
        );

        scroll_mundos.setViewportView(panel_worlds);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_crear)
                        .addGap(28, 28, 28))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scroll_instances, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_iniciar_server)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addComponent(btn_crear_mundo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_editar_mundo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_eliminar_mundo))
                            .addComponent(scroll_mundos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_crear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll_instances, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_iniciar_server)
                            .addComponent(btn_eliminar_mundo)
                            .addComponent(btn_crear_mundo)
                            .addComponent(btn_editar_mundo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scroll_mundos)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearActionPerformed
        new DialogInstance(this,true,this.cfg_global).setVisible(true);
    }//GEN-LAST:event_btn_crearActionPerformed

    private void btn_iniciar_serverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_iniciar_serverActionPerformed
        Instance ins=this.sele_instance;
        if(!Storage.exists(this.cfg_global.getDic("fi_ver_mani"))){
            new Download(null,true,this.cfg_global.getDic("fi_ver_mani"),null,this.cfg_global.getDic("url_ver_mani"),null).setVisible(true);
        }
        GsonManager versions=new Config(this.cfg_global.getDic("fi_ver_mani"),true).getJson().getJsonArray("versions");
        String name_json=ins.version+".json";
        String fo_meta_mc=this.cfg_global.getDic("fo_meta_mc");
        if(!Storage.exists(fo_meta_mc+"/"+name_json)){
            new Download(this,true,fo_meta_mc,name_json,versions.searchArray(versions,"id",ins.version).getValue("url"),null).setVisible(true);
        }
        String url=new GsonManager(fo_meta_mc+"/"+name_json,GsonManager.FILE).getJson("downloads").getJson("server").getValue("url");
        String fo_minecraft=this.cfg_global.getDic("fo_mc")+"/"+ins.version;
        String fi_minecraft=MessageFormat.format(this.cfg_global.getDic("fi_server"),ins.version);
        String fo_server=ins.folder_ins+"/"+this.cfg_global.getDic("fo_server");
        String fi_start=fo_server+"/start.bat";
        if(Storage.exists(fo_minecraft+"/"+fi_minecraft)){
            // Archivo start.bat para iniciar servidor
            if(!Storage.exists(fi_start)){
                String[] l={
                    "cd "+fo_server,
                    ins.java_path+" -jar ../../../"+fi_minecraft+" nogui"
                };
                Storage.writeFile(l, fi_start);
            }
            // Archivo eula del servidor
            Config cfg_eula=new Config(fo_server+"/eula.txt");
            if(cfg_eula.getConfigData("eula").equals("false")){
                int op=JOptionPane.showInternalConfirmDialog(null,"By changing the setting below to TRUE\nyou are indicating your agreement to our EULA\n( https://account.mojang.com/documents/minecraft_eula ).","EULA",JOptionPane.WARNING_MESSAGE);
                if(op==0){
                    cfg_eula.setConfigData("eula","true");
                }
            }
        }else{
            new Download(this,true,fo_minecraft,fi_minecraft,url,null).setVisible(true);
        }
    }//GEN-LAST:event_btn_iniciar_serverActionPerformed

    private void btn_eliminar_mundoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminar_mundoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_eliminar_mundoActionPerformed

    private void btn_crear_mundoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crear_mundoActionPerformed
        
    }//GEN-LAST:event_btn_crear_mundoActionPerformed

    private void btn_editar_mundoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editar_mundoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_editar_mundoActionPerformed

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
    private javax.swing.JButton btn_crear_mundo;
    private javax.swing.JButton btn_editar_mundo;
    private javax.swing.JButton btn_eliminar_mundo;
    private javax.swing.JButton btn_iniciar_server;
    private javax.swing.JPanel panel_instances;
    private javax.swing.JPanel panel_worlds;
    private javax.swing.JScrollPane scroll_instances;
    private javax.swing.JScrollPane scroll_mundos;
    // End of variables declaration//GEN-END:variables
}
