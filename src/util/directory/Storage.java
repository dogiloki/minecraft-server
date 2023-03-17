package util.directory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import util.enums.DirectoryType;

/**
 *
 * @author dogi_
 */

public class Storage{
    
    protected String src=null;
    protected DirectoryType type=null;
    protected File file=null;
    private BufferedWriter bw=null;
    private BufferedReader br=null;
    
    public Storage(){
        
    }
    
<<<<<<< HEAD
    public Storage(String src, DirectoryType type){
        this.run(src,type);
    }
    
=======
>>>>>>> 9ddecff630b5415f72ffaa92c1134f75924175b8
    public void run(String src, DirectoryType type){
        this.type=type;
        this.src=src;
    }
    
    public boolean open(boolean append){
<<<<<<< HEAD
        if(this.type!=null && this.type!=DirectoryType.FOLDER && this.src!=null){
            try{
                this.file=new File(this.src);
                this.bw=new BufferedWriter(new FileWriter(this.file,append));
                this.br=new BufferedReader(new FileReader(this.file));
                return true;
            }catch(IOException ex){
                return false;
            }
=======
        if(this.type==DirectoryType.FOLDER){
            return true;
        }
        try{
            this.file=new File(src);
            this.bw=new BufferedWriter(new FileWriter(this.file,append));
            this.br=new BufferedReader(new FileReader(this.file));
            return true;
        }catch(IOException ex){
            return false;
>>>>>>> 9ddecff630b5415f72ffaa92c1134f75924175b8
        }
        return false;
    }
    
    public boolean clean(){
        this.close();
        try{
            if(!this.open(false) || !this.close()){
                return false;
            }
            this.bw.write("");
            this.close();
            return true;
        }catch(Exception ex){
            return false;
        }
    }
    
    public boolean write(Object text){
        try{
            if(!this.open(true)){
                return false;
            }
            this.bw.write(((String)text));
            this.close();
            return true;
        }catch(IOException ex){
            return false;
        }
    }
    
    public String read(){
        try{
            if(!this.open(true)){
                return null;
            }
            String line=null;
            String lines="";
            while((line=this.br.readLine())!=null){
                lines+=line+"\n";
            }
            this.close();
            return lines;
        }catch(IOException ex){
            return null;
        }
    }
    
    public boolean close(){
        try{
            if(this.bw==null || this.br==null){
                return true;
            }
            this.bw.close();
            this.br.close();
            return true;
        }catch(IOException ex){
            return false;
        }
    }
    
    public boolean delete(){
        try{
            return this.file.delete();
        }catch(Exception ex){
            return false;
        }
    }
    
    // Crea una carpeta
    public static boolean createFolder(String path){
        try{
            String ruta_crear="";
            for(String sub_ruta:path.replace("\\","/").split("/")){
                ruta_crear+=sub_ruta+"/";
                File directorio=new File(ruta_crear);
                if(!directorio.exists()){
                    directorio.mkdir();
                }
            }
            return true;
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Saber si existe un archivo o carpeta
    public static boolean exists(String path, DirectoryType type, boolean created){
        File directorio=new File(path);
        if(!directorio.exists() && created){
            Storage store=new Storage();
<<<<<<< HEAD
            store.run(path,type);
=======
            store.run(ruta,type);
>>>>>>> 9ddecff630b5415f72ffaa92c1134f75924175b8
            switch(type){
                case FOLDER: return Storage.createFolder(path);
                case FILE: return store.write("");
            }
            store.close();
        }
        return directorio.exists();
    }
    
}
