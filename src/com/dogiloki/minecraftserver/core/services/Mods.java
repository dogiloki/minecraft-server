package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.multitaks.directory.ModelDirectory;
import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;

/**
 *
 * @author _dogi
 */

@Directory(type=DirectoryType.FOLDER)
public class Mods extends ModelDirectory{
    
    public static String WITHOUT_MODS="-- Sin Mods --";
    
    public Mods(String path){
        super.aim(path);
        this.exists(true);
    }
    
    @Override
    public String toString(){
        return this.getName();
    }
    
}
