package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.minecraftserver.core.entities.ListWorlds;
import com.dogiloki.multitaks.directory.DirectoryList;
import com.dogiloki.multitaks.directory.ModelDirectory;
import com.dogiloki.multitaks.directory.Storage;
import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;

/**
 *
 * @author _dogi
 */

@Directory(type=DirectoryType.FOLDER)
public final class Worlds extends ModelDirectory{
    
    private ListWorlds item=new ListWorlds();
    
    public Worlds(){
        
    }
    
    public Worlds(String src){
        super.aim(src);
        this.exists(true);
        this.reload();
    }
    
    public void createWorld(String name){
        Storage.createFolder(this.getSrc()+"/"+name);
    }
    
    public Worlds reload(){
        DirectoryList folders=this.listFolders();
        boolean was_worlds=false;
        this.item.clear();
        while(folders.hasNext()){
            was_worlds=true;
            String name=folders.next().getFileName().toString();
            this.item.append(name,new World(this.getSrc()+"/"+name));
        }
        if(!was_worlds){
            // Crear mundo por defecto (únicamente la carpeta, no un mundo de minecraft)
            String name=World.DEFAULT_NAME;
            this.item.append(name,new World(this.getSrc()+"/"+name));
        }
        return this;
    }
    
    public ListWorlds items(){
        return this.item;
    }
    
}
