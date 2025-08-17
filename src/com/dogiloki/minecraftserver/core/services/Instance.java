package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.minecraftserver.application.dao.InstanceCfg;
import com.dogiloki.minecraftserver.application.dao.Properties;
import com.dogiloki.minecraftserver.application.dao.ServerProperties;
import com.dogiloki.multitaks.directory.DirectoryList;
import com.dogiloki.multitaks.directory.Storage;
import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.dogiloki.multitaks.logger.AppLogger;
import java.nio.file.Files;

/**
 *
 * @author _dogi
 */

@Directory(type=DirectoryType.FOLDER)
public final class Instance extends Server{
    
    public Instance(){
        this.cfg=new InstanceCfg();
        this.server_properties=new ServerProperties();
        this.worlds=new Worlds();
    }
    
    public Instance(String path){
        super.aim(path);
        this.reload();
    }
    
    public void reload(){
        if(!this.exists()) return;
        this.cfg=new InstanceCfg(this.getSrc()+"/"+Properties.files.instance_cfg).builder();
        this.server_properties=new ServerProperties(this.getSrc()+"/"+Properties.folders.instances_server+"/"+Properties.files.instance_properties).builder();
        this.worlds=new Worlds(this.getSrc()+"/"+Properties.folders.instances_server+"/"+Properties.folders.instances_worlds);
    }
    
    public boolean save(String path){
        super.aim(path);
        return this.save();
    }
    
    @Override
    public boolean save(){
        try{
            if(!super.save()){
                throw new Exception("Error al crear Instancia");
            }
            if(this.cfg.getSrc()==null){
                this.cfg.aim(this.getSrc()+"/"+Properties.files.instance_cfg);
            }
            if(!this.cfg.save()){
                AppLogger.error("Error al guardar configuración de la Instancia "+this.getSrc());
            }else{
                String mods_dir=this.getSrc()+"/"+Properties.folders.instances_server+"/"+Properties.folders.instances_mods;
                if(this.cfg.usedForge() && this.cfg.usedMods() && this.cfg.change_mods){
                    Storage.deleteFolder(mods_dir);
                    Storage.createFolder(mods_dir);
                    Mods mods=new Mods(Properties.folders.instances_mods+"/"+this.cfg.mods);
                    DirectoryList mods_files=mods.listFiles();
                    while(mods_files.hasNext()){
                        Storage existing=new Storage(mods_files.next().toString());
                        Storage link=new Storage(mods_dir+"/"+existing.getName());
                        if(!existing.exists()){
                            AppLogger.debug("No existe "+existing.getSrc());
                            continue;
                        }
                        if(!existing.hashing().equals(link.hashing())){
                            Files.createLink(link.asPath(),existing.asPath());

                        }
                    }
                }else{
                    if(!this.cfg.usedMods()){
                        Storage.deleteFolder(mods_dir);
                    }
                }
            }
            if(this.server_properties.getSrc()==null){
                this.server_properties.aim(this.getSrc()+"/"+Properties.folders.instances_server+"/"+Properties.files.instance_properties);
            }
            if(!this.server_properties.save()){
                throw new Exception("Error al guardar propiedades del servidor");
            }
            if(this.worlds.getSrc()==null){
                this.worlds.aim(this.getSrc()+"/"+Properties.folders.instances_server+"/"+Properties.folders.instances_worlds);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return true;
    }
    
}
