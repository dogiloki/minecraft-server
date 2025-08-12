package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.minecraftserver.application.dao.InstanceCfg;
import com.dogiloki.minecraftserver.application.dao.ServerProperties;
import com.dogiloki.multitaks.directory.ModelDirectory;

/**
 *
 * @author dogi_
 */

public class Server extends ModelDirectory{
    
    public InstanceCfg cfg;
    public ServerProperties server_properties;
    public Worlds worlds;
    public Mods mods=null;
    
}
