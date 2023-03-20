package dao;

/**
 *
 * @author dogi_
 */

import javax.swing.Icon;
import multitaks.StorageOld;
import interfaces.DAO;
import multitaks.annotations.relation.Relation;
import multitaks.directory.Storage;
import multitaks.enums.DirectoryType;
import multitaks.enums.RelationType;
import multitaks.relation.ModelRelation;

public class World extends ModelRelation implements DAO {
    
    private String old_folder_path="";
    public String folder_path="";
    public String name="";
    public Icon icon=null;
    @Relation(type=RelationType.OneByOne)
    public Server server=null;

    public World(){
        
    }
    
    // Constructor para obtener la información de un mundo
    public World(String folder_path){
        this.old_folder_path=folder_path;
        this.folder_path=folder_path;
    }
    
    public boolean create(){
        Storage.exists(this.folder_path+"/"+this.name+".properties",DirectoryType.FILE,true);
        Storage.exists(this.folder_path,DirectoryType.FOLDER,true);
        return true;
    }
    
    public boolean delete(){
        return Storage.deleteFile(this.folder_path);
    }
    
    // Almacenar cambios hechos en el mundo
    public boolean save(){
        StorageOld.rename(this.old_folder_path, this.folder_path);
        return true;
    }
    
}
