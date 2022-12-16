package dto;

/**
 *
 * @author dogi_
 */

import util.Config;
import interfaces.DTO;

public class Properties implements DTO{
    
    public String file_path;
    private Config cfg=null;
    
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
    public int max_players=28;
    public String gamemode=Properties.SURVIVAL;
    public String difficulty=Properties.NORMAL;
    public boolean white_list=false;
    public boolean online_mode=true;
    public boolean pvp=true;
    public boolean enable_command_block=true;
    public boolean allow_flight=true;
    public boolean spawn_animals=true;
    public boolean spawn_mosters=true;
    public boolean spawn_npcs=true;
    public boolean allow_nether=true;
    public boolean force_gamemode=false;
    public int spawn_protection=8;
    public boolean require_resorce_pack=false;
    public String resource_pack="";
    public String resource_pack_promp="";
    
    public String toString(){
        return "";
    }

    public boolean create() {
        return true;
    }

    public boolean delete() {
        return true;
    }

    public boolean save() {
        return true;
    }
    
}
