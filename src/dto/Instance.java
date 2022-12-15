package dto;

/**
 *
 * @author dogi_
 */

import interfaces.DTO;
import javax.swing.JPanel;
import util.Config;
import util.Storage;

public class Instance implements DTO{
    
    public String name;
    public String version;
    public String java_path;
    public String file_path;
    private Config cfg=null;
    
    // Atributos para manejo en la interfaz
    public JPanel panel_ins=null;
    public JPanel panel_world=null;
    public String folder_ins="";
    public String folder_world="";
    public World wolrd;
    
    public Instance(){
        
    }
    
    // Constructor para abrir información de una instancia ya existente
    public Instance(String file_path){
        this.file_path=file_path;
        this.cfg=new Config(file_path);
        this.name=this.cfg.getConfigData("name");
        this.version=this.cfg.getConfigData("version");
        this.java_path=this.cfg.getConfigData("JavaPath");
        this.create();
    }
    
    // Constructor para crear un archivo .cfg, con la información básica de la instancia
    public Instance(String file_path, String name, String version, String java_path){
        this.file_path=file_path;
        this.name=name;
        this.version=version;
        this.java_path=java_path;
    }
    
    public boolean create(){
        return Storage.exists(this.file_path,Storage.CREATED,Storage.FILE);
    }
    
    public boolean delete(){
        return Storage.deleteFile(this.file_path);
    }

    // Guardar los valores en el archivo .cfg
    public boolean save(){
        if(this.cfg==null){
            String[] datas={
                "name="+this.name,
                "version="+this.version,
                "JavaPath="+this.java_path
            };
            Storage.writeFile(datas,this.file_path);
            this.cfg=new Config(this.file_path);
        }
        this.cfg.setConfigData("name",this.name);
        this.cfg.setConfigData("version",this.version);
        this.cfg.setConfigData("JavaPath",this.java_path);
        return true;
    }

}
