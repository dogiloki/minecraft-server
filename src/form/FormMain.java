package form;

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
import java.awt.event.ItemEvent;
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
import form.Server;
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
                    getProperties();
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
                    }else{
                        server.startOutput();
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
    
    public void getProperties(){
        if(this.sele_instance==null || this.sele_instance.properties==null){
            return;
        }
        
        Properties proper=this.sele_instance.properties;
        
        if(this.gamemode.getItemCount()<=0){
            this.gamemode.addItem(Properties.SPECTATOR);
            this.gamemode.addItem(Properties.ADVENTURE);
            this.gamemode.addItem(Properties.CREATIVE);
            this.gamemode.addItem(Properties.SURVIVAL);
        }
        if(this.difficulty.getItemCount()<=0){
            this.difficulty.addItem(Properties.PEACEFUL);
            this.difficulty.addItem(Properties.EASY);
            this.difficulty.addItem(Properties.NORMAL);
            this.difficulty.addItem(Properties.HARD);
        }
        
        this.gamemode.setSelectedItem(proper.gamemode);
        this.difficulty.setSelectedItem(proper.difficulty);
        this.max_players.setText(proper.max_players);
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
        this.port.setText(proper.port);
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panel_properties = new javax.swing.JPanel();
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
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        port = new javax.swing.JTextField();
        panel_server = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        server_text = new javax.swing.JTextArea();
        server_box_input = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

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
            .addGap(0, 618, Short.MAX_VALUE)
        );
        panel_worldsLayout.setVerticalGroup(
            panel_worldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 238, Short.MAX_VALUE)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 254, Short.MAX_VALUE)
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
                .addComponent(scroll_mundos, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
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
            .addGap(0, 238, Short.MAX_VALUE)
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
                .addComponent(scroll_instances, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel1);

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
                        .addGap(0, 0, Short.MAX_VALUE))
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

        gamemode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                gamemodeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gamemode, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
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

        difficulty.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                difficultyItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap(217, Short.MAX_VALUE))
                    .addComponent(difficulty, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

        spawn_protection.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                spawn_protectionKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
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

        resource_pack.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                resource_packKeyReleased(evt);
            }
        });

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

        resource_pack_promp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                resource_pack_prompKeyReleased(evt);
            }
        });

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
        white_list.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                white_listItemStateChanged(evt);
            }
        });

        online_mode.setText("Comprobar cuenta");
        online_mode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                online_modeItemStateChanged(evt);
            }
        });

        pvp.setText("PVP");
        pvp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pvpItemStateChanged(evt);
            }
        });

        allow_flight.setText("Volar");
        allow_flight.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                allow_flightItemStateChanged(evt);
            }
        });

        enable_command_block.setText("Bloques de comandos");
        enable_command_block.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                enable_command_blockItemStateChanged(evt);
            }
        });

        spawn_animals.setText("Animales");
        spawn_animals.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                spawn_animalsItemStateChanged(evt);
            }
        });

        spawn_mosters.setText("Mostruos");
        spawn_mosters.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                spawn_mostersItemStateChanged(evt);
            }
        });

        spawn_npcs.setText("Aldeanos");
        spawn_npcs.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                spawn_npcsItemStateChanged(evt);
            }
        });

        force_gamemode.setText("Forzar modo de juego");
        force_gamemode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                force_gamemodeItemStateChanged(evt);
            }
        });

        require_resorce_pack.setText("Paquete de recursos requerido");
        require_resorce_pack.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                require_resorce_packItemStateChanged(evt);
            }
        });

        allow_nether.setText("Inframundo");
        allow_nether.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                allow_netherItemStateChanged(evt);
            }
        });

        btn_reset_properties.setText("Restaurar configuración");
        btn_reset_properties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reset_propertiesActionPerformed(evt);
            }
        });

        jLabel4.setText("Puerto");

        port.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                portKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 140, Short.MAX_VALUE))
                    .addComponent(port))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_propertiesLayout = new javax.swing.GroupLayout(panel_properties);
        panel_properties.setLayout(panel_propertiesLayout);
        panel_propertiesLayout.setHorizontalGroup(
            panel_propertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_propertiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_propertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_propertiesLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_propertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_propertiesLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_propertiesLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_propertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_propertiesLayout.createSequentialGroup()
                                .addComponent(white_list)
                                .addGap(18, 18, 18)
                                .addComponent(force_gamemode))
                            .addGroup(panel_propertiesLayout.createSequentialGroup()
                                .addComponent(pvp)
                                .addGap(18, 18, 18)
                                .addComponent(allow_flight)
                                .addGap(18, 18, 18)
                                .addComponent(online_mode))
                            .addGroup(panel_propertiesLayout.createSequentialGroup()
                                .addComponent(enable_command_block)
                                .addGap(18, 18, 18)
                                .addComponent(allow_nether))
                            .addGroup(panel_propertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(panel_propertiesLayout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(btn_reset_properties, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_propertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_propertiesLayout.createSequentialGroup()
                                        .addComponent(spawn_npcs)
                                        .addGap(18, 18, 18)
                                        .addComponent(spawn_animals)
                                        .addGap(18, 18, 18)
                                        .addComponent(spawn_mosters))
                                    .addComponent(require_resorce_pack))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_propertiesLayout.setVerticalGroup(
            panel_propertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_propertiesLayout.createSequentialGroup()
                .addGroup(panel_propertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_propertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_propertiesLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel_propertiesLayout.createSequentialGroup()
                        .addGroup(panel_propertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_propertiesLayout.createSequentialGroup()
                                .addGroup(panel_propertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(white_list)
                                    .addComponent(force_gamemode))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_propertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(pvp)
                                    .addComponent(allow_flight)
                                    .addComponent(online_mode)))
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_propertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(enable_command_block)
                            .addComponent(allow_nether))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_propertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spawn_npcs)
                            .addComponent(spawn_animals)
                            .addComponent(spawn_mosters))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(require_resorce_pack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_reset_properties)
                        .addGap(76, 76, 76))))
        );

        jTabbedPane1.addTab("Propiedades", panel_properties);

        server_text.setColumns(20);
        server_text.setLineWrap(true);
        server_text.setRows(5);
        jScrollPane1.setViewportView(server_text);

        server_box_input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                server_box_inputKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panel_serverLayout = new javax.swing.GroupLayout(panel_server);
        panel_server.setLayout(panel_serverLayout);
        panel_serverLayout.setHorizontalGroup(
            panel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_serverLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(server_box_input, javax.swing.GroupLayout.DEFAULT_SIZE, 803, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        panel_serverLayout.setVerticalGroup(
            panel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_serverLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(server_box_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Servidor", panel_server);

        jMenu1.setText("Configuración");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1)
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearActionPerformed
        new DialogInstance(this,true,this.cfg_global,null).setVisible(true);
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
        GsonManager json=new GsonManager(fo_meta_mc+"/"+name_json,GsonManager.FILE);
        String url=json.getJson("downloads").getJson("server").getValue("url");
        String fo_minecraft=this.cfg_global.getDic("fo_mc")+"/"+ins.version;
        String fi_minecraft=MessageFormat.format(this.cfg_global.getDic("fi_server"),ins.version);
        String fo_server=ins.folder_ins+"/"+this.cfg_global.getDic("fo_server");
        String fi_start=fo_server+"/start.bat";
        long fi_minecraft_size=parseLong(json.getJson("downloads").getJson("server").getValue("size"));
        if(Storage.exists(fo_minecraft+"/"+fi_minecraft) && fi_minecraft_size==Storage.getSize(fo_minecraft+"/"+fi_minecraft)){
            // Archivo start.bat para iniciar servidor
            String[] text_bat={"\""+ins.java_path+"\" -jar -Xmx"+ins.memory_max+" -Xms"+ins.memory_max+" ../../../"+fo_minecraft+"/"+fi_minecraft+" nogui"};
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
                }else{
                    ins.world.server=server;
                    this.servers.add(server);
                }
            }
        }else{
            new Download(this,true,fo_minecraft,fi_minecraft,url,null).setVisible(true);
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

    private void max_playersKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_max_playersKeyReleased
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.max_players=this.max_players.getText();
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_max_playersKeyReleased

    private void spawn_protectionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spawn_protectionKeyReleased
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.spawn_protection=this.spawn_protection.getText();
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_spawn_protectionKeyReleased

    private void gamemodeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_gamemodeItemStateChanged
        if(this.sele_instance!=null && this.sele_instance.properties!=null && evt.getStateChange()==ItemEvent.DESELECTED){
            this.sele_instance.properties.gamemode=this.gamemode.getSelectedItem().toString();
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_gamemodeItemStateChanged

    private void difficultyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_difficultyItemStateChanged
        if(this.sele_instance!=null && this.sele_instance.properties!=null && evt.getStateChange()==ItemEvent.DESELECTED){
            this.sele_instance.properties.difficulty=this.difficulty.getSelectedItem().toString();
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_difficultyItemStateChanged

    private void resource_packKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resource_packKeyReleased
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.resource_pack=this.resource_pack.getText();
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_resource_packKeyReleased

    private void resource_pack_prompKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resource_pack_prompKeyReleased
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.resource_pack_promp=this.resource_pack_promp.getText();
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_resource_pack_prompKeyReleased

    private void white_listItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_white_listItemStateChanged
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.white_list=this.white_list.isSelected()?"true":"false";
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_white_listItemStateChanged

    private void online_modeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_online_modeItemStateChanged
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.online_mode=this.online_mode.isSelected()?"true":"false";
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_online_modeItemStateChanged

    private void pvpItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pvpItemStateChanged
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.pvp=this.pvp.isSelected()?"true":"false";
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_pvpItemStateChanged

    private void allow_flightItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_allow_flightItemStateChanged
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.allow_flight=this.allow_flight.isSelected()?"true":"false";
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_allow_flightItemStateChanged

    private void enable_command_blockItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_enable_command_blockItemStateChanged
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.enable_command_block=this.enable_command_block.isSelected()?"true":"false";
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_enable_command_blockItemStateChanged

    private void force_gamemodeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_force_gamemodeItemStateChanged
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.force_gamemode=this.force_gamemode.isSelected()?"true":"false";
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_force_gamemodeItemStateChanged

    private void spawn_npcsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_spawn_npcsItemStateChanged
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.spawn_npcs=this.spawn_npcs.isSelected()?"true":"false";
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_spawn_npcsItemStateChanged

    private void spawn_animalsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_spawn_animalsItemStateChanged
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.spawn_animals=this.spawn_animals.isSelected()?"true":"false";
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_spawn_animalsItemStateChanged

    private void spawn_mostersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_spawn_mostersItemStateChanged
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.spawn_mosters=this.spawn_mosters.isSelected()?"true":"false";
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_spawn_mostersItemStateChanged

    private void allow_netherItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_allow_netherItemStateChanged
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.allow_nether=this.allow_nether.isSelected()?"true":"false";
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_allow_netherItemStateChanged

    private void require_resorce_packItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_require_resorce_packItemStateChanged
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.require_resorce_pack=this.require_resorce_pack.isSelected()?"true":"false";
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_require_resorce_packItemStateChanged

    private void btn_reset_propertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reset_propertiesActionPerformed
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.reset();
            this.sele_instance.properties.save();
            this.getProperties();
        }
    }//GEN-LAST:event_btn_reset_propertiesActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        new DialogInstance(this,true,this.cfg_global,this.sele_instance).setVisible(true);
        this.setInstances();
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

    private void portKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_portKeyReleased
        if(this.sele_instance!=null && this.sele_instance.properties!=null){
            this.sele_instance.properties.port=this.port.getText();
            this.sele_instance.properties.save();
        }
    }//GEN-LAST:event_portKeyReleased

    private void server_box_inputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_server_box_inputKeyReleased
        if(evt.getKeyCode()==10){
            Server server=this.sele_instance.world.server;
            System.out.println(server.id);
            server.send(this.server_box_input.getText());
            this.server_box_input.setText("");
        }
    }//GEN-LAST:event_server_box_inputKeyReleased

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
    private javax.swing.JButton btn_crear_world;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_eliminar_world;
    private javax.swing.JButton btn_folder_worlds;
    private javax.swing.JButton btn_iniciar_server;
    private javax.swing.JButton btn_reset_properties;
    private javax.swing.JComboBox<String> difficulty;
    private javax.swing.JCheckBox enable_command_block;
    private javax.swing.JCheckBox force_gamemode;
    private javax.swing.JComboBox<String> gamemode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField max_players;
    private javax.swing.JCheckBox online_mode;
    private javax.swing.JPanel panel_instances;
    private javax.swing.JPanel panel_properties;
    private javax.swing.JPanel panel_server;
    private javax.swing.JPanel panel_worlds;
    private javax.swing.JTextField port;
    private javax.swing.JCheckBox pvp;
    private javax.swing.JCheckBox require_resorce_pack;
    private javax.swing.JTextField resource_pack;
    private javax.swing.JTextField resource_pack_promp;
    private javax.swing.JScrollPane scroll_instances;
    private javax.swing.JScrollPane scroll_mundos;
    private javax.swing.JTextField server_box_input;
    private javax.swing.JTextArea server_text;
    private javax.swing.JCheckBox spawn_animals;
    private javax.swing.JCheckBox spawn_mosters;
    private javax.swing.JCheckBox spawn_npcs;
    private javax.swing.JTextField spawn_protection;
    private javax.swing.JCheckBox white_list;
    // End of variables declaration//GEN-END:variables
}
