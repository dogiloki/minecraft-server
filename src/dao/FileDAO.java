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

public class FileDAO implements Directory, Cloneable{

    private File file=null;
    private String src=null;
    private FileDAO clone;
    public String name=null;
    public String text="";
    
    public FileDAO(String src){
        this.file=new File(src);
        this.constructor(this.file);
    }
    
    public FileDAO(File file){
        this.constructor(file);
    }
    
    private void constructor(File file){
        this.file=file;
        this.src=this.file.getPath();
        this.name=Storage.getName(this.src);
        this.src=Storage.getFolder(this.src);
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

    public void setExtension(String extension){
        this.name=Storage.getNameNotExtension(this.name)+"."+extension;
    }
    
    public void setText(String... text){
        this.text=String.join("\n", text);
    }
    
    public File getFile(){
        return this.file;
    }
    
    public String getExtension(){
        return Storage.getExtension(this.name);
    }
    
    @Override
    public String getPath(){
        return this.src+"/"+this.name;
    }
    
    @Override
    public void create(){
        Storage.exists(this.file.getPath(),Storage.CREATED,Storage.FILE);
        try{
            this.clone=(FileDAO)this.clone();
        }catch(CloneNotSupportedException ex) {
            Logger.getLogger(FileDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void save(){
        if(!this.clone.getPath().equals(this.getPath())){
            Storage.rename(this.clone.getPath(), this.getPath());
        }
        Storage.writeFile(this.text, this.getPath());
        this.constructor(new File(this.getPath()));
    }

    @Override
    public void delete(){
        Storage.deleteFile(this.file.getPath());
        
    }

    @Override
    public String toString() {
        return "FileDAO{" + "file=" + file + ", name=" + name + ", src=" + src + ", text=" + text + ", clone=" + clone + '}';
    }
    
}
