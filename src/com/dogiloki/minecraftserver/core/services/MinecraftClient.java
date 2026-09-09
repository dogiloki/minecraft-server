package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.minecraftserver.application.dao.ClientInstance;
import com.dogiloki.multitaks.directory.ModelDirectory;
import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;

/**
 *
 * @author _dogi
 */

@Directory(type=DirectoryType.FOLDER)
public final class MinecraftClient extends ModelDirectory{
    
    public final Worlds worlds;
    public final Mods mods;
    
    public MinecraftClient(ClientInstance instance){
        super(instance.path);
        this.worlds=new Worlds(this.getSrc()+"/saves");
        this.mods=new Mods(this.getSrc()+"/mods");
    }
    
}
