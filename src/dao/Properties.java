package dao;

/**
 *
 * @author dogi_
 */

import util.Config;
import util.Storage;
import interfaces.DAO;

public class Properties implements DAO{
    
    public String file_path=null;
    private Config cfg=null;
    
    public Properties(){
        
    }
    
    public Properties(String file_path){
        this.file_path=file_path;
        this.create();
        this.setters();
        this.save();
    }
    
    public Properties(Config cfg){
        this.cfg=cfg;
        this.setters();
    }
    
    // Valores aceptados en el archivo .properties
    
    // Gamemode
    final public static String SURVIVAL="survival";
    final public static String CREATIVE="creactive";
    final public static String ADVENTURE="adventure";
    final public static String SPECTATOR="spectator";
    
    // Difficulty
    final public static String PEACEFUL="peaceful";
    final public static String EASY="easy";
    final public static String NORMAL="normal";
    final public static String HARD="hard";
    
    // Clave valor, del archivo .properties
    public String level_name="";
    public String max_players="28";
    public String gamemode=Properties.SURVIVAL;
    public String difficulty=Properties.NORMAL;
    public String white_list="false";
    public String online_mode="true";
    public String pvp="true";
    public String enable_command_block="true";
    public String allow_flight="true";
    public String spawn_animals="true";
    public String spawn_mosters="true";
    public String spawn_npcs="true";
    public String allow_nether="true";
    public String force_gamemode="false";
    public String spawn_protection="8";
    public String require_resorce_pack="false";
    public String resource_pack="";
    public String resource_pack_promp="";
    
    private void setters(){
        this.level_name=this.cfg.getConfigData("level-name");
        this.max_players=this.cfg.getConfigData("max-players");
        this.gamemode=this.cfg.getConfigData("gamemode");
        this.difficulty=this.cfg.getConfigData("difficulty");
        this.white_list=this.cfg.getConfigData("white-list");
        this.online_mode=this.cfg.getConfigData("online-mode");
        this.pvp=this.cfg.getConfigData("pvp");
        this.enable_command_block=this.cfg.getConfigData("enable-command-block");
        this.allow_flight=this.cfg.getConfigData("allow-flight");
        this.spawn_animals=this.cfg.getConfigData("spawn-animals");
        this.spawn_mosters=this.cfg.getConfigData("spawn-mosters");
        this.spawn_npcs=this.cfg.getConfigData("spawn-npcs");
        this.allow_nether=this.cfg.getConfigData("allow-nether");
        this.force_gamemode=this.cfg.getConfigData("force-gamemode");
        this.spawn_protection=this.cfg.getConfigData("spawn-protection");
        this.require_resorce_pack=this.cfg.getConfigData("require-resorce-pack");
        this.resource_pack=this.cfg.getConfigData("resource-pack");
        this.resource_pack_promp=this.cfg.getConfigData("resource-pack-promp");
    }

    public boolean create(){
        Storage.exists(this.file_path,Storage.CREATED,Storage.FILE);
        this.cfg=new Config(this.file_path);
        return true;
    }

    public boolean delete(){
        return Storage.deleteFile(this.file_path);
    }

    public boolean save(){
        if(this.cfg==null){
            this.create();
        }
        this.cfg.setConfigData("level-name", this.level_name);
        this.cfg.setConfigData("max-players", this.max_players);
        this.cfg.setConfigData("gamemode", this.gamemode);
        this.cfg.setConfigData("difficulty", this.difficulty);
        this.cfg.setConfigData("white-list", this.white_list);
        this.cfg.setConfigData("online-mode", this.online_mode);
        this.cfg.setConfigData("pvp", this.pvp);
        this.cfg.setConfigData("enable-command-block", this.enable_command_block);
        this.cfg.setConfigData("allow-flight", this.allow_flight);
        this.cfg.setConfigData("spawn-animals", this.spawn_animals);
        this.cfg.setConfigData("spawn-mosters", this.spawn_mosters);
        this.cfg.setConfigData("spawn-npcs", this.spawn_npcs);
        this.cfg.setConfigData("allow-nether", this.allow_nether);
        this.cfg.setConfigData("force-gamemode", this.force_gamemode);
        this.cfg.setConfigData("spawn-protection", this.spawn_protection);
        this.cfg.setConfigData("require-resorce-pack", this.require_resorce_pack);
        this.cfg.setConfigData("resource-pack", this.resource_pack);
        this.cfg.setConfigData("resource-pack-promp", this.resource_pack_promp);
        this.cfg.save();
        return true;
    }
    
}
