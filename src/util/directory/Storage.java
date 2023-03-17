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
    
    public void run(String src, DirectoryType type){
        this.type=type;
        this.src=src;
        this.open(true);
    }
    
    public boolean open(boolean append){
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
        }
    }
    
    public boolean clean(){
        this.close();
        try{
            this.open(false);
            this.bw.write("");
            this.close();
            return true;
        }catch(Exception ex){
            return false;
        }
    }
    
    public boolean write(Object text){
        try{
            this.open(true);
            this.bw.write(((String)text));
            this.close();
            return true;
        }catch(IOException ex){
            return false;
        }
    }
    
    public String read(){
        try{
            this.open(true);
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
            this.bw.close();
            this.br.close();
            return true;
        }catch(IOException ex){
            return false;
        }
    }
    
    // Crea una carpeta
    public static boolean createFolder(String ruta){
        try{
            String ruta_crear="";
            for(String sub_ruta:ruta.replace("\\","/").split("/")){
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
    public static boolean exists(String ruta, DirectoryType type, boolean created){
        File directorio=new File(ruta);
        if(!directorio.exists() && created){
            Storage store=new Storage();
            store.run(ruta,type);
            switch(type){
                case FOLDER: return Storage.createFolder(ruta);
                case FILE: return store.write("");
            }
            store.close();
        }
        return directorio.exists();
    }
    
}
