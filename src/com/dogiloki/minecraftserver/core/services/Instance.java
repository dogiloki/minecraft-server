package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.minecraftserver.application.dao.InstanceCfg;
import com.dogiloki.minecraftserver.application.dao.Properties;
import com.dogiloki.minecraftserver.application.dao.ServerProperties;
import com.dogiloki.minecraftserver.infraestructure.ui.CopyDialog;
import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.dogiloki.multitaks.persistent.ExecutionObserver;

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
        this.cfg=new InstanceCfg(this.getSrc()+"/"+Properties.files.instance_cfg).builder();
        this.server_properties=new ServerProperties(this.getSrc()+"/"+Properties.folders.instances_server+"/"+Properties.files.instance_properties).builder();
        this.worlds=new Worlds(this.getSrc()+"/"+Properties.folders.instances_server+"/"+Properties.folders.instances_worlds);
    }
    
    public boolean save(String path){
        super.aim(path);
        try{
            if(this.cfg.file_forge_installer!=null){
                MinecraftServer minecraft_server=new MinecraftServer(this.cfg).forge(this.cfg.file_forge_installer.getName());
                ExecutionObserver execution=new ExecutionObserver(
                        "\""+this.cfg.java_path+"\" -jar "+this.cfg.file_forge_installer.getSrc()+" --installServer "+
                        minecraft_server.forge_jar.getFolder()
                );
                execution.start((line,posi)->{
                    
                });
                this.cfg.forge_version=minecraft_server.forge_version;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
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
                throw new Exception("Error al guardar configuración de la Instancia");
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
