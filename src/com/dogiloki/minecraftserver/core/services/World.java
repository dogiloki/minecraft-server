package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.multitaks.directory.ModelDirectory;
import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import javax.swing.Icon;

/**
 *
 * @author dogi_
 */

@Directory(type=DirectoryType.FOLDER)
public class World extends ModelDirectory{
    
    public static String DEFAULT_NAME="world";
    
    public Icon icon=null;
    
    public World(String path){
        super.aim(path);
        this.exists(true);
        //this.icon= // Pendiente a obtener el icono
    }
    
}
