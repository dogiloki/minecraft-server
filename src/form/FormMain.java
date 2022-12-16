package form;

/**
 *
 * @author dogi_
 */

import dao.Instance;
import dao.Properties;
import dao.World;

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
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    private Config cfg_global=null;
    private Process p=null;

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
        for(String folder_tmp:folders){
            String folder=path_instances+"/"+folder_tmp;
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
                    // Propiedades de la instancia
                    Properties proper=new Properties(folder+"/"+cfg_global.getDic("fo_server")+"/server.properties");
                    proper.save();
                    sele_instance.properties=proper;
                    getProperties();
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
        Storage.exists(path_worlds,Storage.CREATED,Storage.FOLDER);
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
            World world=new World(path_worlds+"/"+folder);
            
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
                    sele_instance.folder_world=cfg_global.getDic("fo_worlds")+"/"+folder;
                    
                    // Propiedades
                    sele_instance.properties.level_name=sele_instance.folder_world;
                    sele_instance.properties.save();
                    
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
            
            // Datos del World
            world.name=folder;
            
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
    
    public void getProperties(){
        if(this.sele_instance==null || this.sele_instance.properties==null){
            return;
        }
        
        Properties proper=this.sele_instance.properties;
        
        this.max_players.setText(proper.max_players);
        
        this.gamemode.removeAllItems();
        this.gamemode.addItem(Properties.PEACEFUL);
        this.gamemode.addItem(Properties.EASY);
        this.gamemode.addItem(Properties.NORMAL);
        this.gamemode.addItem(Properties.HARD);
        this.gamemode.setSelectedItem(proper.gamemode);
        
        this.difficulty.removeAllItems();
        this.difficulty.addItem(Properties.SPECTATOR);
        this.difficulty.addItem(Properties.ADVENTURE);
        this.difficulty.addItem(Properties.CREATIVE);
        this.difficulty.addItem(Properties.SURVIVAL);
        this.difficulty.setSelectedItem(proper.difficulty);
        
        this.white_list.setSelected(proper.white_list.equals("true"));
        this.online_mode.setSelected(proper.online_mode.equals("true"));
        this.pvp.setSelected(proper.pvp.equals("true"));
        this.enable_command_block.setSelected(proper.enable_command_block.equals("true"));
        this.allow_flight.setSelected(proper.allow_flight.equals("true"));
        this.spawn_animals.setSelected(proper.spawn_animals.equals("true"));
        this.spawn_mosters.setSelected(proper.spawn_mosters.equals("true"));
        this.spawn_npcs.setSelected(proper.spawn_npcs.equals("true"));
        this.allow_nether.setSelected(proper.allow_nether.equals("true"));
        this.force_gamemode.setSelected(proper.force_gamemode.equals("true"));
        this.spawn_protection.setText(proper.spawn_protection);
        this.require_resorce_pack.setSelected(proper.require_resorce_pack.equals("true"));
        this.resource_pack.setText(proper.resource_pack);
        this.resource_pack_promp.setText(proper.resource_pack_promp);
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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        max_players = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        gamemode = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        difficulty = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        spawn_protection = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        resource_pack = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        resource_pack_promp = new javax.swing.JTextField();
        white_list = new javax.swing.JCheckBox();
        online_mode = new javax.swing.JCheckBox();
        pvp = new javax.swing.JCheckBox();
        allow_flight = new javax.swing.JCheckBox();
        enable_command_block = new javax.swing.JCheckBox();
        spawn_animals = new javax.swing.JCheckBox();
        spawn_mosters = new javax.swing.JCheckBox();
        spawn_npcs = new javax.swing.JCheckBox();
        force_gamemode = new javax.swing.JCheckBox();
        require_resorce_pack = new javax.swing.JCheckBox();
        allow_nether = new javax.swing.JCheckBox();
        btn_reset_properties = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        scroll_instances.setPreferredSize(new java.awt.Dimension(918, 445));

        javax.swing.GroupLayout panel_instancesLayout = new javax.swing.GroupLayout(panel_instances);
        panel_instances.setLayout(panel_instancesLayout);
        panel_instancesLayout.setHorizontalGroup(
            panel_instancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 790, Short.MAX_VALUE)
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
            .addGap(0, 728, Short.MAX_VALUE)
        );
        panel_worldsLayout.setVerticalGroup(
            panel_worldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 414, Short.MAX_VALUE)
        );

        scroll_mundos.setViewportView(panel_worlds);

        jLabel1.setText("Máximo de jugadores");

        max_players.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                max_playersKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 20, Short.MAX_VALUE))
                    .addComponent(max_players))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(max_players, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setText("Modo de juego");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 107, Short.MAX_VALUE))
                    .addComponent(gamemode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gamemode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setText("Dificultad");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(difficulty, 0, 188, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(difficulty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel5.setText("Protección de spawn");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 23, Short.MAX_VALUE))
                    .addComponent(spawn_protection))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spawn_protection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("Paquete de recursos");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(resource_pack))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resource_pack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setText("Mensaje de paquete de recursos");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(resource_pack_promp))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resource_pack_promp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        white_list.setText("Lista blanca");

        online_mode.setText("Juego original");

        pvp.setText("PVP");

        allow_flight.setText("Volar");

        enable_command_block.setText("Bloques de comandos");

        spawn_animals.setText("Animales");

        spawn_mosters.setText("Mostruos");

        spawn_npcs.setText("Aldeanos");

        force_gamemode.setText("Forzar modo de juego");

        require_resorce_pack.setText("Paquete de recursos requerido");

        allow_nether.setText("Inframundo");

        btn_reset_properties.setText("Restaurar configuración");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(white_list)
                        .addGap(18, 18, 18)
                        .addComponent(online_mode)
                        .addGap(18, 18, 18)
                        .addComponent(pvp)
                        .addGap(18, 18, 18)
                        .addComponent(allow_flight))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(enable_command_block)
                        .addGap(18, 18, 18)
                        .addComponent(force_gamemode))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(require_resorce_pack)
                            .addGap(18, 18, 18)
                            .addComponent(btn_reset_properties, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(spawn_npcs)
                            .addGap(18, 18, 18)
                            .addComponent(spawn_animals)
                            .addGap(18, 18, 18)
                            .addComponent(spawn_mosters)
                            .addGap(18, 18, 18)
                            .addComponent(allow_nether)))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(2, 2, 2))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(white_list)
                            .addComponent(online_mode)
                            .addComponent(pvp)
                            .addComponent(allow_flight))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(enable_command_block)
                            .addComponent(force_gamemode))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spawn_npcs)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(spawn_animals)
                                    .addComponent(spawn_mosters)
                                    .addComponent(allow_nether))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(require_resorce_pack)
                            .addComponent(btn_reset_properties)))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_crear)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(scroll_instances, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btn_iniciar_server)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_crear_mundo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_editar_mundo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_eliminar_mundo))
                                    .addComponent(scroll_mundos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
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
                            .addComponent(btn_iniciar_server)
                            .addComponent(btn_eliminar_mundo)
                            .addComponent(btn_crear_mundo)
                            .addComponent(btn_editar_mundo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scroll_mundos, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))
                    .addComponent(scroll_instances, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
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
            String[] text_bat={ins.java_path+" -jar ../../../"+fo_minecraft+"/"+fi_minecraft+" nogui"};
            if(!Storage.exists(fi_start)){
                Storage.writeFile(text_bat, fi_start);
            }
            // Archivo eula del servidor
            Config cfg_eula=new Config(fo_server+"/eula.txt");
            if(cfg_eula.getConfigData("eula").equals("false")){
                int op=JOptionPane.showInternalConfirmDialog(null,"By changing the setting below to TRUE\nyou are indicating your agreement to our EULA\n( https://account.mojang.com/documents/minecraft_eula ).","EULA",JOptionPane.WARNING_MESSAGE);
                if(op==0){
                    cfg_eula.setConfigData("eula","true");
                    cfg_eula.save();
                }
            }
            /*try{
                ProcessBuilder pb=new ProcessBuilder();
                this.p=pb.command("cmd","/c","start "+text_bat[0]).directory(new File(fo_server)).start();
            }catch(Exception ex){
                ex.printStackTrace();
            }*/
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

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(this.p!=null){
            this.p.destroy();
        }
    }//GEN-LAST:event_formWindowClosing

    private void max_playersKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_max_playersKeyReleased
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.max_players=this.max_players.getText();
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_max_playersKeyReleased

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
    private javax.swing.JCheckBox allow_flight;
    private javax.swing.JCheckBox allow_nether;
    private javax.swing.JButton btn_crear;
    private javax.swing.JButton btn_crear_mundo;
    private javax.swing.JButton btn_editar_mundo;
    private javax.swing.JButton btn_eliminar_mundo;
    private javax.swing.JButton btn_iniciar_server;
    private javax.swing.JButton btn_reset_properties;
    private javax.swing.JComboBox<String> difficulty;
    private javax.swing.JCheckBox enable_command_block;
    private javax.swing.JCheckBox force_gamemode;
    private javax.swing.JComboBox<String> gamemode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTextField max_players;
    private javax.swing.JCheckBox online_mode;
    private javax.swing.JPanel panel_instances;
    private javax.swing.JPanel panel_worlds;
    private javax.swing.JCheckBox pvp;
    private javax.swing.JCheckBox require_resorce_pack;
    private javax.swing.JTextField resource_pack;
    private javax.swing.JTextField resource_pack_promp;
    private javax.swing.JScrollPane scroll_instances;
    private javax.swing.JScrollPane scroll_mundos;
    private javax.swing.JCheckBox spawn_animals;
    private javax.swing.JCheckBox spawn_mosters;
    private javax.swing.JCheckBox spawn_npcs;
    private javax.swing.JTextField spawn_protection;
    private javax.swing.JCheckBox white_list;
    // End of variables declaration//GEN-END:variables
}
