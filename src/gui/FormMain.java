package gui;

/**
 *
 * @author dogi_
 */

import dao.Instance;
import dao.Properties;
import dao.World;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import static java.lang.Long.parseLong;
import java.text.MessageFormat;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import util.Storage;
import util.Config;
import util.Download;
import util.Function;
import util.GsonManager;
import util.Watcher;
import java.util.List;

public class FormMain extends javax.swing.JFrame {
    
    private Watcher watcher;
    private ArrayList<Instance> instances=new ArrayList<>();
    private Instance sele_instance=null;
    private Config cfg_global=null;
    private List<Server> servers=new ArrayList<>();

    public FormMain() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.dic();
        this.setInstances();
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
        Storage.createFolder(this.cfg_global.getDic("fo_instances"));
    }
    
    public void runWath(){
        this.watcher=new Watcher(this.cfg_global.getDic("fo_instances"),this,FormMain.class,"setInstances");
    }
    
    public void setInstances(){
        this._setInstances(true);
    }
    
    public void setInstances(boolean get_folder){
        this._setInstances(get_folder);
    }
    
    private void _setInstances(boolean get_folder){
        this.sele_instance=null;
        if(this.instances.size()<=0 || get_folder){
            this.getInstancesDictory();
        }
        this.panel_instances.removeAll();
        this.scroll_instances.updateUI();
        // Valores del panel en cada instancia
        int ancho=210, alto=80;
        int ancho_total=this.scroll_instances.getWidth()-25;
        int total_columnas=(int)(Math.floorDiv(ancho_total,ancho))-1;
        int x=0, y=0, count=0, filas=0;
        for(Instance ins:this.instances){
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
                        if(sele_instance.panel_ins==panel){
                            return;
                        }
                        sele_instance.panel_ins.setBackground(null);
                        sele_instance.panel_ins.setOpaque(false);
                    }
                    btn_crear_world.setEnabled(true);
                    btn_folder_worlds.setEnabled(true);
                    btn_iniciar_server.setEnabled(false);
                    btn_eliminar_world.setEnabled(false);
                    btn_edit.setEnabled(true);
                    btn_delete.setEnabled(true);
                    sele_instance=ins;
                    sele_instance.panel_ins=panel;
                    sele_instance.panel_ins.setBackground(Color.decode("#b2cff0"));
                    sele_instance.panel_ins.setOpaque(true);
                    // Propiedades de la instancia
                    Properties proper=new Properties(ins.folder_ins+"/"+cfg_global.getDic("fo_server")+"/server.properties");
                    proper.save();
                    sele_instance.properties=proper;
                    setWorlds(false);
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
            
            ins.panel_ins=panel;
            
            // Diseño
            //Design.margen(panel,10);
            
            // Agregar a la GUI
            this.panel_instances.add(panel);
        }
        // Adaptar panel de las instancias
        this.panel_instances.updateUI();
        this.panel_instances.setPreferredSize(Function.createDimencion(ancho_total,filas*alto));
    }
    
    public void getInstancesDictory(){
        this.instances.clear();
        String path_instances=this.cfg_global.getDic("fo_instances");
        String file_instance=this.cfg_global.getDic("fi_instance");
        String[] folders=Storage.listDirectory(path_instances);
        for(String folder_tmp:folders){
            String folder=path_instances+"/"+folder_tmp;
            if(!Storage.exists(folder+"/"+file_instance)){
                continue;
            }
            // Obtener datos de la instancia
            Instance ins=new Instance(folder+"/"+file_instance);
            
            // Almacena instancias
            ins.panel_world=null;
            ins.folder_ins=folder;
            ins.folder_world="";
            ins.world=null;
            this.instances.add(ins);
        }
        
    }
    
    public void setWorlds(){
        this._setWorlds(true);
    }
    
    public void setWorlds(boolean get_folder){
        this._setWorlds(get_folder);
    }
    
    private void _setWorlds(boolean get_folder){
        if(this.sele_instance==null){
            this.panel_worlds.removeAll();
            this.panel_worlds.updateUI();
            return;
        }
        if(this.sele_instance.worlds.size()<=0 || get_folder){
            this.getWorldsDictory();
        }
        this.panel_worlds.removeAll();
        this.panel_worlds.updateUI();
        // Valores del panel en cada instancia
        int ancho=170, alto=170;
        int ancho_total=this.scroll_mundos.getWidth()-25;
        int total_columnas=(int)Math.floorDiv(ancho_total, ancho)-1;
        int x=0, y=0, count=0, filas=0;
        for(World world:this.sele_instance.worlds){
            Instance ins=this.sele_instance;
            // Panel principal
            JPanel panel=new JPanel();
            panel.setLayout(null);
            panel.setBounds(x,y,ancho,alto);
            panel.setOpaque(false);
            panel.setToolTipText(world.name);
            panel.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
            panel.addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent evt){}
                @Override
                public void mousePressed(MouseEvent evt){
                    if(sele_instance.panel_world!=null){
                        if(sele_instance.panel_world==panel){
                            return;
                        }
                        sele_instance.panel_world.setBackground(null);
                        sele_instance.panel_world.setOpaque(false);
                    }
                    btn_iniciar_server.setEnabled(true);
                    btn_eliminar_world.setEnabled(true);
                    sele_instance.world=world;
                    sele_instance.folder_world=cfg_global.getDic("fo_worlds")+"/"+world.name;
                    
                    // Propiedades
                    sele_instance.properties.level_name=sele_instance.folder_world;
                    sele_instance.properties.save();
                    
                    sele_instance.panel_world=panel;
                    sele_instance.panel_world.setBackground(Color.decode("#b2cff0"));
                    sele_instance.panel_world.setOpaque(true);
                    
                    // Obtener server si ya tiene uno iniciado
                    Server server=sele_instance.world.server;
                    if(server==null){
                        server_text.setText("");
                        btn_iniciar_server.setEnabled(true);
                    }else{
                        server.startOutput();
                        btn_iniciar_server.setEnabled(false);
                    }
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
            world.icon=new ImageIcon(new ImageIcon(world.folder_path+"/"+world.name+"/icon.png").getImage().getScaledInstance(label_icon.getWidth()-30, label_icon.getHeight()-10,Image.SCALE_DEFAULT));
            // Agregar icon al panel
            label_icon.setIcon(world.icon);
            panel.add(label_icon);
            
            // Compoente Nombre
            JLabel label_nombre=new JLabel(world.name);
            label_nombre.setBounds(0,(alto/7)*5,ancho,alto/7);
            label_nombre.setFont(new Font("arial",Font.PLAIN,16));
            label_nombre.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            panel.add(label_nombre);
            
            // Diseño
            //Design.margen(panel,10);
            
            // Agregar a la GUI
            this.panel_worlds.add(panel);
        }
        // Adaptar panel de las instancias
        this.panel_worlds.updateUI();
        this.panel_worlds.setPreferredSize(Function.createDimencion(ancho_total,filas*alto));
    }
    
    public void getWorldsDictory(){
        String path_worlds=this.sele_instance.folder_ins+"/"+this.cfg_global.getDic("fo_server")+"/"+this.cfg_global.getDic("fo_worlds");
        Storage.exists(path_worlds,Storage.CREATED,Storage.FOLDER);
        String[] folders=Storage.listDirectory(path_worlds);
        if(folders==null || folders.length<=0){
            return;
        }
        List<World> worlds=new ArrayList<>();
        for(String folder:folders){
            // Obtener datos de la World
            World world=new World(path_worlds+"/"+folder);
            // Datos del World
            world.name=folder;
            worlds.add(world);
        }
        this.sele_instance.worlds=worlds;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        btn_iniciar_server = new javax.swing.JButton();
        btn_crear_world = new javax.swing.JButton();
        btn_folder_worlds = new javax.swing.JButton();
        btn_eliminar_world = new javax.swing.JButton();
        scroll_mundos = new javax.swing.JScrollPane();
        panel_worlds = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btn_crear = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        scroll_instances = new javax.swing.JScrollPane();
        panel_instances = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        server_text = new javax.swing.JTextArea();
        server_box_input = new javax.swing.JTextField();
        btn_stop_server = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jSplitPane1.setBorder(null);

        btn_iniciar_server.setText("Iniciar");
        btn_iniciar_server.setEnabled(false);
        btn_iniciar_server.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_iniciar_serverActionPerformed(evt);
            }
        });

        btn_crear_world.setText("Crear mundo");
        btn_crear_world.setEnabled(false);
        btn_crear_world.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crear_worldActionPerformed(evt);
            }
        });

        btn_folder_worlds.setText("Carpeta de mundos");
        btn_folder_worlds.setEnabled(false);
        btn_folder_worlds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_folder_worldsActionPerformed(evt);
            }
        });

        btn_eliminar_world.setText("Eliminar mundo");
        btn_eliminar_world.setEnabled(false);
        btn_eliminar_world.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_worldActionPerformed(evt);
            }
        });

        scroll_mundos.setBorder(null);

        panel_worlds.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout panel_worldsLayout = new javax.swing.GroupLayout(panel_worlds);
        panel_worlds.setLayout(panel_worldsLayout);
        panel_worldsLayout.setHorizontalGroup(
            panel_worldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );
        panel_worldsLayout.setVerticalGroup(
            panel_worldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 273, Short.MAX_VALUE)
        );

        scroll_mundos.setViewportView(panel_worlds);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btn_iniciar_server)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 255, Short.MAX_VALUE)
                        .addComponent(btn_crear_world)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_folder_worlds)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_eliminar_world))
                    .addComponent(scroll_mundos, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_iniciar_server)
                    .addComponent(btn_crear_world)
                    .addComponent(btn_folder_worlds)
                    .addComponent(btn_eliminar_world))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_mundos, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel5);

        btn_crear.setText("Crear instancia");
        btn_crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crearActionPerformed(evt);
            }
        });

        btn_edit.setText("MOD");
        btn_edit.setEnabled(false);
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        btn_delete.setText("DEL");
        btn_delete.setEnabled(false);
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        scroll_instances.setBorder(null);

        panel_instances.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout panel_instancesLayout = new javax.swing.GroupLayout(panel_instances);
        panel_instances.setLayout(panel_instancesLayout);
        panel_instancesLayout.setHorizontalGroup(
            panel_instancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 183, Short.MAX_VALUE)
        );
        panel_instancesLayout.setVerticalGroup(
            panel_instancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 273, Short.MAX_VALUE)
        );

        scroll_instances.setViewportView(panel_instances);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btn_crear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_edit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_delete))
            .addComponent(scroll_instances)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_crear)
                    .addComponent(btn_edit)
                    .addComponent(btn_delete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_instances, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        server_text.setColumns(20);
        server_text.setLineWrap(true);
        server_text.setRows(5);
        jScrollPane1.setViewportView(server_text);

        server_box_input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                server_box_inputKeyReleased(evt);
            }
        });

        btn_stop_server.setText("Stop");
        btn_stop_server.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_stop_serverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(server_box_input)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_stop_server)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(server_box_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_stop_server))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearActionPerformed
        new DialogInstance(this,true,this.cfg_global,null).setVisible(true);
    }//GEN-LAST:event_btn_crearActionPerformed

    private void btn_iniciar_serverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_iniciar_serverActionPerformed
        this.btn_iniciar_server.setEnabled(false);
        this.server_text.setText("Starting server...");
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
        GsonManager json=new GsonManager(fo_meta_mc+"/"+name_json,GsonManager.FILE);
        String url=json.getJson("downloads").getJson("server").getValue("url");
        String fo_minecraft=this.cfg_global.getDic("fo_mc")+"/"+ins.version;
        String fi_server=Function.assign(ins.file_server,MessageFormat.format(this.cfg_global.getDic("fi_server"),ins.version));
        String fo_server=ins.folder_ins+"/"+this.cfg_global.getDic("fo_server");
        String fi_start=fo_server+"/start.bat";
        long fi_minecraft_size=parseLong(json.getJson("downloads").getJson("server").getValue("size"));
        if(Storage.exists(fo_minecraft+"/"+fi_server) && (fi_minecraft_size==Storage.getSize(fo_minecraft+"/"+fi_server) || ins.file_server!=null)){
            // Archivo start.bat para iniciar servidor
            String text_bat_server="--serverJar=../../../"+fo_minecraft+"/"+MessageFormat.format(this.cfg_global.getDic("fi_server"),ins.version);
            //String text_bat_server="";
            String[] text_bat={"\""+ins.java_path+"\" -jar -Xmx"+ins.memory_max+" -Xms"+ins.memory_max+" ../../../"+fo_minecraft+"/"+fi_server+" nogui "+(ins.file_server==null?"":text_bat_server)};
            Storage.writeFile(text_bat, fi_start);
            // Iniciar servidor
            Server server=new Server(text_bat[0],fo_server,this.server_text);
            // Archivo eula del servidor
            if(Storage.exists(fo_server+"/eula.txt")){
                Config cfg_eula=new Config(fo_server+"/eula.txt");
                if(cfg_eula.getConfigData("eula").equals("false")){
                    int op=JOptionPane.showInternalConfirmDialog(null,"By changing the setting below to TRUE\nyou are indicating your agreement to our EULA\n( https://account.mojang.com/documents/minecraft_eula ).","EULA",JOptionPane.WARNING_MESSAGE);
                    if(op==0){
                        cfg_eula.setConfigData("eula","true");
                        cfg_eula.save();
                    }
                    this.btn_iniciar_server.setEnabled(true);
                }else{
                    ins.world.server=server;
                    this.servers.add(server);
                }
            }
        }else{
            new Download(this,true,fo_minecraft,fi_server,url,null).setVisible(true);
            this.btn_iniciar_server.setEnabled(true);
        }
    }//GEN-LAST:event_btn_iniciar_serverActionPerformed

    private void btn_eliminar_worldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminar_worldActionPerformed
        if(this.sele_instance!=null && this.sele_instance.folder_world!=null){
            try{
                String folder=this.sele_instance.folder_ins+"/"+this.cfg_global.getDic("fo_server")+"/"+this.sele_instance.folder_world;
                Storage.deleteFolder(folder);
            }catch(Exception ex){
               JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
            }
            this.setWorlds();
        }
    }//GEN-LAST:event_btn_eliminar_worldActionPerformed

    private void btn_crear_worldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crear_worldActionPerformed
        if(this.sele_instance==null){
            JOptionPane.showMessageDialog(null,"Seleccione una Instancia","Advertencia",JOptionPane.WARNING_MESSAGE);
            return;
        }
        String name_world=JOptionPane.showInputDialog("Ingrese nombre mundo");
        String folder_world=this.sele_instance.folder_ins+"/"+this.cfg_global.getDic("fo_server")+"/"+this.cfg_global.getDic("fo_worlds")+"/"+name_world;
        if(name_world==null || name_world.trim().equals("")){
            return;
        }
        if(Storage.exists(folder_world)){
            JOptionPane.showMessageDialog(null, "El mundo "+name_world+" ya existe","Avertencia",JOptionPane.WARNING_MESSAGE);
        }else{
            Storage.createFolder(folder_world);
        }
        this.setWorlds();
    }//GEN-LAST:event_btn_crear_worldActionPerformed

    private void btn_folder_worldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_folder_worldsActionPerformed
        if(this.sele_instance==null || this.sele_instance.folder_ins.equals("")){
            return;
        }
        try{
            Desktop.getDesktop().open(new File(Storage.getDir()+"/"+this.sele_instance.folder_ins+"/"+this.cfg_global.getDic("fo_server")+"/"+this.cfg_global.getDic("fo_worlds")));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        /*String path_world_old=Storage.selectFolder(this,"");
        String[] array_world_old=path_world_old.replace("\\","/").split("/");
        String name_world_new=JOptionPane.showInputDialog("Ingrese nombre mundo",array_world_old[array_world_old.length-1]);
        String path_world_new=this.sele_instance.folder_ins+"/"+this.cfg_global.getDic("fo_server")+"/"+this.cfg_global.getDic("fo_worlds")+"/"+name_world_new;
        new DialogCopy(this,true,path_world_old,path_world_new,"Importado con exito!!!").setVisible(true);
        this.getWorlds();*/
    }//GEN-LAST:event_btn_folder_worldsActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        for(Server server:this.servers){
            server.send("stop");
        }
    }//GEN-LAST:event_formWindowClosing

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        if(this.sele_instance==null){
            JOptionPane.showMessageDialog(null,"Seleccione una Instancia","Advertencia",JOptionPane.WARNING_MESSAGE);
            return;
        }
        new DialogInstance(this,true,this.cfg_global,this.sele_instance).setVisible(true);
        this.setInstances();
        this.setWorlds();
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        if(this.sele_instance!=null && this.sele_instance.folder_ins!=null){
            try{
                String folder=this.sele_instance.folder_ins;
                Storage.deleteFolder(folder);
            }catch(Exception ex){
               JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
            }
            this.setWorlds();
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void server_box_inputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_server_box_inputKeyReleased
        if(this.sele_instance==null || this.sele_instance.world==null || this.sele_instance.world.server==null){
            return;
        }
        if(evt.getKeyCode()==10){
            Server server=this.sele_instance.world.server;
            server.send(this.server_box_input.getText());
            this.server_box_input.setText("");
        }
    }//GEN-LAST:event_server_box_inputKeyReleased

    private void btn_stop_serverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_stop_serverActionPerformed
        if(this.sele_instance==null || this.sele_instance.world==null || this.sele_instance.world.server==null){
            return;
        }
        this.sele_instance.world.server.send("stop");
        this.sele_instance.world.server=null;
        this.setWorlds();
    }//GEN-LAST:event_btn_stop_serverActionPerformed

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
    private javax.swing.JButton btn_crear_world;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_eliminar_world;
    private javax.swing.JButton btn_folder_worlds;
    private javax.swing.JButton btn_iniciar_server;
    private javax.swing.JButton btn_stop_server;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel panel_instances;
    private javax.swing.JPanel panel_worlds;
    private javax.swing.JScrollPane scroll_instances;
    private javax.swing.JScrollPane scroll_mundos;
    private javax.swing.JTextField server_box_input;
    private javax.swing.JTextArea server_text;
    // End of variables declaration//GEN-END:variables
}
