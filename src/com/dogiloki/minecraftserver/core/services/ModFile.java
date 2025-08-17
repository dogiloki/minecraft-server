package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.multitaks.directory.ModelDirectory;
import com.dogiloki.multitaks.directory.Storage;
import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;

/**
 *
 * @author _dogi
 */

 @Directory(type=DirectoryType.FILE)
public class ModFile extends ModelDirectory{
     
    public static String FLAG_DISABLE=".disable";

    public ModFile(String src){
        super.aim(src);
    }
    
    public boolean isEnable(){
        return !this.getName().contains(ModFile.FLAG_DISABLE);
    }
    
    public boolean isDisable(){
        return this.getName().contains(ModFile.FLAG_DISABLE);
    }
    
    public ModFile enable(){
        String new_src=this.getFolder()+"/"+this.getName().replace(ModFile.FLAG_DISABLE,"");
        Storage.rename(this.getSrc(),new_src);
        super.aim(new_src);
        return this;
    }
    
    public ModFile disable(){
        String new_src=this.getFolder()+"/"+this.toString()+ModFile.FLAG_DISABLE;
        Storage.rename(this.getSrc(),new_src);
        super.aim(new_src);
        return this;
    }
    
    @Override
    public String toString(){
        return this.getName().replace(ModFile.FLAG_DISABLE,"");
    }
    
}
