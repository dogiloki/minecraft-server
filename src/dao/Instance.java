package dao;

/**
 *
 * @author dogi_
 */

import javax.swing.JPanel;
import util.Config;
import util.Storage;
import interfaces.DAO;
import java.util.Optional;
import util.Function;

public class Instance implements DAO{
    
    public String file_path;
    private Config cfg=null;
    public Properties properties=null;
    
    public String name;
    public String version;
    public String java_path;
    public String memory_min;
    public String memory_max;
    
    
    // Atributos para manejo en la interfaz
    public JPanel panel_ins=null;
    public JPanel panel_world=null;
    public String folder_ins="";
    public String folder_world="";
    public World world;
    
    public Instance(){
        
    }
    
    // Constructor para abrir información de una instancia ya existente
    public Instance(String file_path){
        this.file_path=file_path;
        this.reset();
        this.create();
        this.setters();
    }
    
    public Instance(Config cfg){
        this.cfg=cfg;
        this.reset();
        this.setters();
    }
    
    public void setters(){
        Function<String> funt=new Function<>();
        this.name=funt.set(this.cfg.getConfigData("name"),this.name);
        this.version=funt.set(this.cfg.getConfigData("version"),this.version);
        this.java_path=funt.set(this.cfg.getConfigData("java.path"),this.java_path);
        this.memory_min=funt.set(this.cfg.getConfigData("memory.min"),this.memory_min);
        this.memory_max=funt.set(this.cfg.getConfigData("memory.max"),this.memory_max);
    }
    
    public void reset(){
        this.java_path="java";
        this.memory_min="1024M";
        this.memory_max="2045M";
    }
    
    public boolean create(){
        Storage.exists(this.file_path,Storage.CREATED,Storage.FILE);
        this.cfg=new Config(this.file_path);
        return true;
    }
    
    public boolean delete(){
        return Storage.deleteFile(this.file_path);
    }

    // Guardar los valores en el archivo .cfg
    public boolean save(){
        if(this.properties!=null){
            this.properties.save();
        }
        if(this.cfg==null){
            this.create();
        }
        this.cfg.setConfigData("name",this.name);
        this.cfg.setConfigData("version",this.version);
        this.cfg.setConfigData("java.path",this.java_path);
        this.cfg.setConfigData("memory.max",this.memory_min);
        this.cfg.setConfigData("memory.min",this.memory_min);
        this.cfg.save();
        return true;
    }

}
