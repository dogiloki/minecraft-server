package com.dogiloki.minecraftserver.core.world;

import com.dogiloki.multitaks.directory.ModelDirectory;
import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import java.io.IOException;
import net.querz.nbt.io.NBTDeserializer;
import net.querz.nbt.io.NamedTag;

/**
 *
 * @author _dogi
 */

@Directory(type=DirectoryType.FILE)
public class LevelDat extends ModelDirectory{
    
    public LevelDat(String path){
        super(path);
    }
    
    public NamedTag getDeserializer()throws IOException{
        NBTDeserializer deserializer=new NBTDeserializer();
        return deserializer.fromFile(this.getFile());
    }
    
}
