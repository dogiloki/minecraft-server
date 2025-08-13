package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.minecraftserver.application.dao.Properties;
import com.dogiloki.minecraftserver.core.entities.ListMods;
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
public final class PackagesMods extends ModelDirectory{
    
    private final ListMods item=new ListMods();
    
    public PackagesMods(){
        super.aim(Properties.folders.packages_mods_folder);
        this.exists(true);
        this.reload();
    }
    
    public PackagesMods createMods(String name){
        Storage.createFolder(this.getSrc()+"/"+name);
        this.item.append(name,new Mods(this.getSrc()+"/"+name));
        return this;
    }
    
    public PackagesMods reload(){
        DirectoryList folders=this.listFolders();
        this.item.clear();
        while(folders.hasNext()){
            String name=folders.next().getFileName().toString();
            this.item.append(name,new Mods(this.getSrc()+"/"+name));
        }
        this.toString();
        return this;
    }
    
    public ListMods items(){
        return this.item;
    }
    
}
