package com.dogiloki.minecraftserver.core.config;

import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.google.gson.annotations.Expose;

/**
 *
 * @author _dogi
 */

@Directory(type=DirectoryType.JSON)
public class Files{
    
    @Expose
    public String instance_cfg="instance.cfg";
    @Expose
    public String instance_properties="server.properties";
    @Expose
    public String minecraft_server_jar="minecraft_server.{0}.jar";
    
    public Files(){
        
    }
    
}
