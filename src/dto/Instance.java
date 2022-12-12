package dto;

/**
 *
 * @author dogi_
 */

import util.Config;
import util.Storage;

public class Instance {
    
    public String name;
    public String version;
    public String java_path;
    private String file_path;
    private Config cfg=null;
    
    // Constructor para abrir información de una instancia ya existente
    public Instance(String file_path){
        this.file_path=file_path;
        this.cfg=new Config(file_path);
        this.name=this.cfg.getConfigData("name");
        this.version=this.cfg.getConfigData("version");
        this.java_path=this.cfg.getConfigData("JavaPath");
    }
    
    // Constructor para crear un archivo .cfg, con la información básica de la instancia
    public Instance(String file_path, String name, String version, String java_path){
        this.file_path=file_path;
        this.name=name;
        this.version=version;
        this.java_path=java_path;
    }

    // Guardar los valores en el archivo .cfg
    public void save(){
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
    }

}
