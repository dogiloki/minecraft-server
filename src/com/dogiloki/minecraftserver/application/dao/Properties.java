package com.dogiloki.minecraftserver.application.dao;

import com.dogiloki.minecraftserver.core.config.Downloads;
import com.dogiloki.minecraftserver.core.config.Files;
import com.dogiloki.minecraftserver.core.config.Folders;
import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.google.gson.annotations.Expose;

/**
 *
 * @author _dogi
 */

@Directory(type=DirectoryType.JSON,src="app.properties")
public class Properties{
    
    @Expose
    public static Folders folders=new Folders();
    @Expose
    public static Files files=new Files();
    @Expose
    public static Downloads downloads=new Downloads();
    
}
