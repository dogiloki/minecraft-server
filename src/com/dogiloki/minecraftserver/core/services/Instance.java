package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.minecraftserver.application.dao.InstanceCfg;
import com.dogiloki.minecraftserver.application.dao.Properties;
import com.dogiloki.minecraftserver.application.dao.ServerProperties;
import com.dogiloki.minecraftserver.core.exceptions.InstanceConfigurationSaveException;
import com.dogiloki.minecraftserver.core.exceptions.InstanceServePropertiesSaveException;
import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.dogiloki.multitaks.logger.AppLogger;

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
        AppLogger.debug("Crear Instancia");
        super.aim(path);
        this.reload();
    }
    
    public void reload(){
        if(!this.exists()) return;
        AppLogger.debug("Recargar datos de la Instancia");
        this.cfg=new InstanceCfg(this.getSrc()+"/"+Properties.files.instance_cfg).builder();
        this.server_properties=new ServerProperties(this.getSrc()+"/"+Properties.folders.instances_server+"/"+Properties.files.instance_properties).builder();
        this.worlds=new Worlds(this.getSrc()+"/"+Properties.folders.instances_server+"/"+Properties.folders.instances_worlds);
        this.mods=new Mods(this.getSrc()+"/"+Properties.folders.instances_server+"/"+Properties.folders.instances_mods);
    }
    
    public boolean save(String path){
        AppLogger.debug("Apuntar la Instancia a la ruta "+path);
        super.aim(path);
        return this.save();
    }
    
    @Override
    public boolean save(){
        AppLogger.debug("Guardar Instancia");
        try{
            if(!this.cfg.exists()){
                this.cfg.aim(this.getSrc()+"/"+Properties.files.instance_cfg);
            }
            if(!this.cfg.save()){
                throw new InstanceConfigurationSaveException();
            }
            if(!this.server_properties.exists()){
                this.server_properties.aim(this.getSrc()+"/"+Properties.folders.instances_server+"/"+Properties.files.instance_properties);
            }
            if(!this.server_properties.save()){
                throw new InstanceServePropertiesSaveException();
            }
            if(!this.worlds.exists()){
                this.worlds.aim(this.getSrc()+"/"+Properties.folders.instances_server+"/"+Properties.folders.instances_worlds);
            }
        }catch(Exception ex){
            AppLogger.warning(ex.getMessage()).showMessage();
            AppLogger.debug("Error al Guardar Instancia").exception(ex);
            return false;
        }
        return true;
    }
    
}
