package com.dogiloki.minecraftserver.core.config;

import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.google.gson.annotations.Expose;

/**
 *
 * @author _dogi
 */

@Directory(type=DirectoryType.JSON)
public class Downloads{
    
    @Expose
    public String version_manifest_name="version_manifest.json";
    @Expose
    public String version_manifest_url="https://launchermeta.mojang.com/mc/game/version_manifest.json";
    
    public Downloads(){
        
    }
    
}
