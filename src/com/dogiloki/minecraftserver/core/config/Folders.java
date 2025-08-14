package com.dogiloki.minecraftserver.core.config;

import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.google.gson.annotations.Expose;

/**
 *
 * @author _dogi
 */

@Directory(type=DirectoryType.JSON)
public class Folders{
    
    @Expose
    public String instances_folder="instances";
    @Expose
    public String instances_server="server";
    @Expose
    public String instances_worlds="saves";
    @Expose
    public String instances_mods="mods";
    
    @Expose
    public String metadata_folder="meta";
    @Expose
    public String metadata_mc="minecraft";
    @Expose
    public String metadata_forge="forge";
    
    @Expose
    public String libraries_folder="libraries";
    @Expose
    public String libraries_mc="minecraft";
    @Expose
    public String libraries_forge="forge";
    
    @Expose
    public String packages_mods_folder="mods";
    
    @Expose
    public String logger="logs";
    
    public Folders(){
        
    }
    
}