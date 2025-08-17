package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.minecraftserver.core.entities.ListModFiles;
import com.dogiloki.multitaks.directory.DirectoryList;
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
    
    private final ListModFiles item=new ListModFiles();
    
    public Mods(String path){
        super.aim(path);
        this.exists(true);
        this.reload();
    }
    
    public Mods reload(){
        DirectoryList folders=this.listFolders();
        this.item.clear();
        while(folders.hasNext()){
            String name=folders.next().getFileName().toString();
            this.item.append(name,new ModFile(this.getSrc()+"/"+name));
        }
        return this;
    }
    
    public ListModFiles items(){
        return this.item;
    }
    
    @Override
    public String toString(){
        return this.getName();
    }
    
}
