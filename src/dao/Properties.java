package dao;

/**
 *
 * @author dogi_
 */

import multitaks.Config;
import multitaks.StorageOld;
import interfaces.DAO;
import multitaks.Function;
import multitaks.directory.Storage;
import multitaks.enums.DirectoryType;

public class Properties implements DAO{
    
    public String file_path=null;
    private Config cfg=null;
    
    // Valores aceptados en el archivo .properties
    
    // Gamemode
    final public static String SURVIVAL="survival";
    final public static String CREATIVE="creative";
    final public static String ADVENTURE="adventure";
    final public static String SPECTATOR="spectator";
    
    // Difficulty
    final public static String PEACEFUL="peaceful";
    final public static String EASY="easy";
    final public static String NORMAL="normal";
    final public static String HARD="hard";
    
    // Clave valor, del archivo .properties
    public String level_name;
    public String max_players;
    public String gamemode;
    public String difficulty;
    public String white_list;
    public String online_mode;
    public String pvp;
    public String enable_command_block;
    public String allow_flight;
    public String spawn_animals;
    public String spawn_mosters;
    public String spawn_npcs;
    public String allow_nether;
    public String force_gamemode;
    public String spawn_protection;
    public String require_resorce_pack;
    public String resource_pack;
    public String resource_pack_promp;
    public String port;
    
    // Atributos por default
    final public static String LEVEL_NAME="";
    final public static String MAX_PLAYERS="28";
    final public static String GAMEMODE=Properties.SURVIVAL;
    final public static String DIFFICULTY=Properties.NORMAL;
    final public static String WHITE_LIST="false";
    final public static String ONLINE_MODE="true";
    final public static String PVP="true";
    final public static String ENABLED_COMMAND_BLOCK="true";
    final public static String ALLOW_FLIGHT="true";
    final public static String SPAWN_ANIMALES="true";
    final public static String SPAWN_MOSTERS="true";
    final public static String SPAWN_NPCS="true";
    final public static String ALLOW_NETHER="true";
    final public static String FORCE_GAMEMODE="false";
    final public static String SPAWN_PROTECTION="8";
    final public static String REQUIRE_RESORCE_PACK="false";
    final public static String RESOURCE_PACK="";
    final public static String RESOURCE_PACK_PROMP="";
    final public static String PORT="25565";
    
    public Properties(){
        this.reset();
    }
    
    public Properties(String file_path){
        this.file_path=file_path;
        this.reset();
        this.create();
        this.setters();
    }
    
    public Properties(Config cfg){
        this.cfg=cfg;
        this.reset();
        this.setters();
    }
    
    private void setters(){
        Function<String> funt=new Function<>();
        this.level_name=funt.set(this.cfg.getConfigData("level-name"),this.level_name);
        this.max_players=funt.set(this.cfg.getConfigData("max-players"),this.max_players);
        this.gamemode=funt.set(this.cfg.getConfigData("gamemode"),this.gamemode);
        this.difficulty=funt.set(this.cfg.getConfigData("difficulty"),this.difficulty);
        this.white_list=funt.set(this.cfg.getConfigData("white-list"),this.white_list);
        this.online_mode=funt.set(this.cfg.getConfigData("online-mode"),this.online_mode);
        this.pvp=funt.set(this.cfg.getConfigData("pvp"),this.pvp);
        this.enable_command_block=funt.set(this.cfg.getConfigData("enable-command-block"),this.enable_command_block);
        this.allow_flight=funt.set(this.cfg.getConfigData("allow-flight"),this.allow_flight);
        this.spawn_animals=funt.set(this.cfg.getConfigData("spawn-animals"),this.spawn_animals);
        this.spawn_mosters=funt.set(this.cfg.getConfigData("spawn-mosters"),this.spawn_mosters);
        this.spawn_npcs=funt.set(this.cfg.getConfigData("spawn-npcs"),this.spawn_npcs);
        this.allow_nether=funt.set(this.cfg.getConfigData("allow-nether"),this.allow_nether);
        this.force_gamemode=funt.set(this.cfg.getConfigData("force-gamemode"),this.force_gamemode);
        this.spawn_protection=funt.set(this.cfg.getConfigData("spawn-protection"),this.spawn_protection);
        this.require_resorce_pack=funt.set(this.cfg.getConfigData("require-resorce-pack"),this.require_resorce_pack);
        this.resource_pack=funt.set(this.cfg.getConfigData("resource-pack"),this.resource_pack);
        this.resource_pack_promp=funt.set(this.cfg.getConfigData("resource-pack-promp"),this.resource_pack_promp);
        this.port=funt.set(this.cfg.getConfigData("server-port"),this.port);
    }
    
    public void reset(){
        this.level_name=Properties.LEVEL_NAME;
        this.max_players=Properties.MAX_PLAYERS;
        this.gamemode=Properties.GAMEMODE;
        this.difficulty=Properties.NORMAL;
        this.white_list=Properties.WHITE_LIST;
        this.online_mode=Properties.ONLINE_MODE;
        this.pvp=Properties.PVP;
        this.enable_command_block=Properties.ENABLED_COMMAND_BLOCK;
        this.allow_flight=Properties.ALLOW_FLIGHT;
        this.spawn_animals=Properties.SPAWN_ANIMALES;
        this.spawn_mosters=Properties.SPAWN_MOSTERS;
        this.spawn_npcs=Properties.SPAWN_NPCS;
        this.allow_nether=Properties.ALLOW_NETHER;
        this.force_gamemode=Properties.FORCE_GAMEMODE;
        this.spawn_protection=Properties.SPAWN_PROTECTION;
        this.require_resorce_pack=Properties.REQUIRE_RESORCE_PACK;
        this.resource_pack=Properties.RESOURCE_PACK;
        this.resource_pack_promp=Properties.RESOURCE_PACK_PROMP;
        this.port=Properties.PORT;
    }

    public boolean create(){
        Storage.exists(this.file_path,DirectoryType.FILE,true);
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
        this.cfg.setConfigData("server-port", this.port);
        this.cfg.save();
        return true;
    }
    
}
