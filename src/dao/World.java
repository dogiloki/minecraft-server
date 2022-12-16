package dao;

/**
 *
 * @author dogi_
 */

import javax.swing.Icon;
import util.Storage;
import interfaces.DAO;

public class World implements DAO {
    
    private String old_folder_path="";
    public String folder_path="";
    public String name="";
    public Icon icon=null;

    public World(){
        
    }
    
    // Constructor para obtener la información de un mundo
    public World(String folder_path){
        this.old_folder_path=folder_path;
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
