package dto;

/**
 *
 * @author dogi_
 */

import interfaces.DTO;
import javax.swing.Icon;
import util.Storage;

public class World implements DTO {
    
    private String old_folder_path;
    public String folder_path;
    public String name;
    public Properties properties;
    public Icon icon;

    public World(){
        
    }
    
    // Constructor para obtener la información de un mundo
    public World(String folder_path){
        
    }
    
    public boolean create(){
        Storage.exists(this.folder_path+"/"+this.name+".properties",Storage.CREATED,Storage.FILE);
        Storage.exists(this.folder_path,Storage.CREATED,Storage.FOLDER);
        return true;
    }
    
    public boolean delete(){
        return Storage.deleteFile(this.folder_path);
    }
    
    // Almacenar cambios hechos en el mundo
    public boolean save(){
        Storage.rename(this.old_folder_path, this.folder_path);
        return true;
    }
    
}
