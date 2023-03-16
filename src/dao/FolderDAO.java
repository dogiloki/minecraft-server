package dao;

import util.interfaces.Directory;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Storage;

/**
 *
 * @author dogi_
 */

public class FolderDAO implements Directory, Cloneable{

    private File file=null;
    private String src=null;
    private FolderDAO clone;
    
    public FolderDAO(String src){
        this.file=new File(src);
        this.constructor(this.file);
    }
    
    public FolderDAO(File file){
        this.constructor(file);
    }
    
    private void constructor(File file){
        this.file=file;
        this.src=this.file.getPath();
        this.create();
    }
    
    // Setters y Getters
    public void setFile(File file){
        this.constructor(file);
    }

    public void setSrc(String src){
        this.file=new File(src);
        this.constructor(this.file);
    }
    
    public File getFile(){
        return this.file;
    }
    
    @Override
    public String getPath(){
        return this.src;
    }
    
    @Override
    public void create(){
        Storage.exists(this.file.getPath(),Storage.CREATED,Storage.FOLDER);
        try{
            this.clone=(FolderDAO)this.clone();
        }catch(CloneNotSupportedException ex) {
            Logger.getLogger(FileDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void save(){
        if(!this.clone.getPath().equals(this.getPath())){
            Storage.rename(this.clone.getPath(), this.getPath());
        }
        Storage.createFolder(this.getPath());
        this.constructor(new File(this.getPath()));
    }

    @Override
    public void delete(){
        Storage.deleteFile(this.file.getPath());
        
    }

    @Override
    public String toString() {
        return "FileDAO{" + "file=" + file + ", src=" + src + ", clone=" + clone + '}';
    }
    
}
