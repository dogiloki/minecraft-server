package com.dogiloki.minecraftserver.application.dao;

import com.dogiloki.multitaks.ObjectId;
import com.dogiloki.multitaks.directory.ModelDirectory;
import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.dogiloki.multitaks.validator.annotations.Validatable;
import com.dogiloki.multitaks.validator.annotations.ValidationRule;
import com.dogiloki.multitaks.validator.enums.ValidatorRule;
import com.google.gson.annotations.Expose;

/**
 *
 * @author dogi_
 */

@Directory(type=DirectoryType.ENV)
public final class InstanceCfg extends ModelDirectory{
    
    // Atributos por default
    final public static String JAVA_PATH="java";
    final public static String MEMORY_MIN="1024M";
    final public static String MEMORY_MAX="2045M";
    
    @Expose
    public String id=ObjectId.generate();
    @Expose
    @Validatable(
            rules={
                @ValidationRule(type=ValidatorRule.NOT_NULL)
            }
    )
    public String name="";
    @Expose
    @Validatable(
            rules={
                @ValidationRule(type=ValidatorRule.NOT_NULL)
            }
    )
    public String version="";
    @Expose
    public String forge_version="";
    @Expose
    public String java_path;
    @Expose
    public String memory_min;
    @Expose
    public String memory_max;
    
    public InstanceCfg(){
        this.reset();
    }
    
    public InstanceCfg(String path){
        this.reset();
        super.aim(path);
    }
    
    public boolean usedForge(){
        return this.forge_version!=null && !this.forge_version.trim().equals("null") && !this.forge_version.trim().equals("");
    }
    
    public void reset(){
        this.java_path=InstanceCfg.JAVA_PATH;
        this.memory_min=InstanceCfg.MEMORY_MIN;
        this.memory_max=InstanceCfg.MEMORY_MAX;
    }
    
    public String toAlias(){
        return this.name+" - "+this.version+(this.usedForge()?" ("+this.forge_version+")":"");
    }

}
