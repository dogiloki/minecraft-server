package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.minecraftserver.application.dao.Properties;
import com.dogiloki.multitaks.dataformat.JSON;
import com.dogiloki.multitaks.dataformat.contracts.DataFormat;
import com.dogiloki.multitaks.directory.Storage;
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
    
    public String version;
    public Storage version_json;
    public Storage server_jar;
    
    public MinecraftServer(String version){
        this.version=version;
        this.version_json=new Storage(Properties.folders.metadata_folder+"/"+Properties.folders.metadata_mc+"/"+this.getNameMetaJson());
        this.server_jar=new Storage(Properties.folders.minecraft_server_folder+"/"+version+"/"+this.getNameServerJar());
    }
    
    public String getNameServerJar(){
        return DataFormat.messageFormat(Properties.files.minecraft_server_jar,new MapValues().append("0",this.version));
    }
    
    public String getUrlServerJar(){
        if(!this.version_json.exists()){
            return null;
        }
        JsonObject json=JSON.gson().fromJson(this.version_json.read(),JsonObject.class);
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
            JsonObject version=element.getAsJsonObject();
            if(!version.get("id").getAsString().equals(this.version)) continue;
            return version.get("url").getAsString();
        }
        return null;
    }
    
}
