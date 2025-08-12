package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.minecraftserver.application.dao.InstanceCfg;
import com.dogiloki.minecraftserver.application.dao.Properties;
import com.dogiloki.multitaks.dataformat.JSON;
import com.dogiloki.multitaks.dataformat.contracts.DataFormat;
import com.dogiloki.multitaks.directory.Storage;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.dogiloki.multitaks.validator.MapValues;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 *
 * @author _dogi
 */

public final class MinecraftServer{
    
    public static Storage VERSION_MANIFEST=new Storage(
                Properties.folders.metadata_folder+"/"
                +Properties.folders.metadata_mc+"/"
                +Properties.downloads.version_manifest_name
        );
    
    public InstanceCfg cfg;
    
    public String version;
    public String forge_version;
    
    public Storage server_json;
    public Storage server_jar;
    
    public Storage forge_json;
    public Storage forge_jar;
    
    public MinecraftServer(InstanceCfg cfg){
        this.cfg=cfg;
        this.version=cfg.version;
        this.server_json=new Storage(Properties.folders.metadata_folder+"/"+Properties.folders.metadata_mc+"/"+this.getNameMetaJson());
        this.server_jar=new Storage(Properties.folders.libraries_folder+"/"+Properties.folders.libraries_mc+"/"+version+"/"+this.getNameServerJar());
        this.forge_version=cfg.usedForge()?cfg.forge_version:null;
        this.forge_jar=this.getForgeJar();
    }
    
    public MinecraftServer forge(String name){
        String regex="^forge-\\d+\\.\\d+\\.\\d+-\\d+(\\.\\d+)*-installer\\.jar$";
        this.forge_version=name.replaceAll("forge-","").replaceAll("-installer.jar","");
        this.forge_jar=new Storage(Properties.folders.libraries_folder+"/"+Properties.folders.libraries_forge+"/"+this.forge_version+"/"+name);
        return this;
    }
    
    public String getNameServerJar(){
        return DataFormat.messageFormat(Properties.files.minecraft_server_jar,new MapValues().append("0",this.version));
    }
    
    public Storage getForgeJar(){
        Storage forge_file=new Storage(Properties.folders.libraries_folder+"/"+Properties.folders.libraries_forge+"/"+this.forge_version+"/run.bat",DirectoryType.FILE);
        if(forge_file.exists()){
            return forge_file;
        }
        return null;
    }
    
    public String getUrlServerJar(){
        if(!this.server_json.exists()){
            return null;
        }
        JsonObject json=JSON.gson().fromJson(this.server_json.read(),JsonObject.class);
        JsonObject downloads=json.getAsJsonObject("downloads");
        JsonObject server=downloads.getAsJsonObject("server");
        return server.get("url").getAsString();
    }
    
    public String getNameMetaJson(){
        return this.version+".json";
    }
    
    public String getUrlMetaJson(){
        if(!MinecraftServer.VERSION_MANIFEST.exists()){
            return null;
        }
        JsonObject json=JSON.gson().fromJson(MinecraftServer.VERSION_MANIFEST.read(),JsonObject.class);
        JsonArray versions=json.getAsJsonArray("versions");
        for(JsonElement element:versions){
            JsonObject version_json=element.getAsJsonObject();
            if(!version_json.get("id").getAsString().equals(this.version)) continue;
            return version_json.get("url").getAsString();
        }
        return null;
    }
    
}
