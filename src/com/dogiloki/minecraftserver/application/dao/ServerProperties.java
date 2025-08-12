package com.dogiloki.minecraftserver.application.dao;

import com.dogiloki.multitaks.directory.ModelDirectory;
import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author dogi_
 */

@Directory(type=DirectoryType.ENV)
public final class ServerProperties extends ModelDirectory{
    
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
    
    // Atributos por default
    final public static String LEVEL_NAME="";
    final public static String MAX_PLAYERS="28";
    final public static String GAMEMODE=ServerProperties.SURVIVAL;
    final public static String DIFFICULTY=ServerProperties.NORMAL;
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
    
    // clave-valor, del archivo .properties
    @Expose @SerializedName("level-name")
    public String level_name;
    @Expose @SerializedName("max-players")
    public String max_players;
    @Expose
    public String gamemode;
    @Expose
    public String difficulty;
    @Expose @SerializedName("white-list")
    public String white_list;
    @Expose @SerializedName("online-mode")
    public String online_mode;
    @Expose
    public String pvp;
    @Expose @SerializedName("enable-command-block")
    public String enable_command_block;
    @Expose @SerializedName("allow-flight")
    public String allow_flight;
    @Expose @SerializedName("spawn-animals")
    public String spawn_animals;
    @Expose @SerializedName("spawn-mosters")
    public String spawn_mosters;
    @Expose @SerializedName("spawn-npcs")
    public String spawn_npcs;
    @Expose @SerializedName("allow-nether")
    public String allow_nether;
    @Expose @SerializedName("force-gamemode")
    public String force_gamemode;
    @Expose @SerializedName("spawn-protection")
    public String spawn_protection;
    @Expose @SerializedName("require-resorce-pack")
    public String require_resorce_pack;
    @Expose @SerializedName("resource-pack")
    public String resource_pack;
    @Expose @SerializedName("resource-pack-promp")
    public String resource_pack_promp;
    @Expose
    public String port;
    
    public ServerProperties(){
        this.reset();
    }
    
    public ServerProperties(String src){
        this.reset();
        super.aim(src);
    }
    
    public void reset(){
        this.level_name=ServerProperties.LEVEL_NAME;
        this.max_players=ServerProperties.MAX_PLAYERS;
        this.gamemode=ServerProperties.GAMEMODE;
        this.difficulty=ServerProperties.NORMAL;
        this.white_list=ServerProperties.WHITE_LIST;
        this.online_mode=ServerProperties.ONLINE_MODE;
        this.pvp=ServerProperties.PVP;
        this.enable_command_block=ServerProperties.ENABLED_COMMAND_BLOCK;
        this.allow_flight=ServerProperties.ALLOW_FLIGHT;
        this.spawn_animals=ServerProperties.SPAWN_ANIMALES;
        this.spawn_mosters=ServerProperties.SPAWN_MOSTERS;
        this.spawn_npcs=ServerProperties.SPAWN_NPCS;
        this.allow_nether=ServerProperties.ALLOW_NETHER;
        this.force_gamemode=ServerProperties.FORCE_GAMEMODE;
        this.spawn_protection=ServerProperties.SPAWN_PROTECTION;
        this.require_resorce_pack=ServerProperties.REQUIRE_RESORCE_PACK;
        this.resource_pack=ServerProperties.RESOURCE_PACK;
        this.resource_pack_promp=ServerProperties.RESOURCE_PACK_PROMP;
        this.port=ServerProperties.PORT;
    }
    
}
