package multitaks.directory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import multitaks.enums.DirectoryType;

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
    
    public Storage(String src){
        this.run(src,null);
    }
    
    public Storage(String src, DirectoryType type){
        this.run(src,type);
    }
    
    public void run(String src, DirectoryType type){
        this.type=type;
        this.src=src;
    }
    
    public boolean open(boolean append){
        if(this.type!=null && this.type!=DirectoryType.FOLDER && this.src!=null){
            try{
                this.file=new File(this.src);
                this.bw=new BufferedWriter(new FileWriter(this.file,append));
                this.br=new BufferedReader(new FileReader(this.file));
                return true;
            }catch(IOException ex){
                return false;
            }
        }
        return false;
    }
    
    public boolean clean(){
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
    
    public boolean append(Object text){
        try{
            if(!this.open(false)){
                return false;
            }
            this.bw.write(((String)text));
            this.close();
            return true;
        }catch(IOException ex){
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
    
    public long getSize(){
        return Storage.getSize(this.src);
    }
    
    public String[] listDirectory(){
        return Storage.listDirectory(this.src);
    }
    public String[] listFolders(){
        return Storage.listDirectory(this.src,DirectoryType.FOLDER);
    }
    public String[] listFiles(){
        return Storage.listDirectory(this.src,DirectoryType.FILE);
    }
    
    // Saber si existe un archivo o carpeta
    public static boolean exists(String path){
        return Storage._exists(path,DirectoryType.ALL,false);
    }
    public static boolean exists(String path, DirectoryType type, boolean created){
        return Storage._exists(path,type,created);
    }
    private static boolean _exists(String path, DirectoryType type, boolean created){
        File directorio=new File(path);
        if(!directorio.exists() && created){
            Storage store=new Storage(path,type);
            switch(type){
                case FOLDER: return Storage.createFolder(path);
                case FILE: return store.write("");
            }
            store.close();
        }
        return directorio.exists();
    }
    
     // Obtener tamaño de un directorio
    public static long getSize(String path){
        return Storage._getSize(path,0);
    }
    private static long _getSize(String path, long size){
        File directory=new File(path);
        if(directory.isDirectory()){
            String[] directories=Storage.listDirectory(path);
            for(String path_:directories){
                File direct=new File(path+"/"+path_);
                if(direct.isDirectory()){
                    size=Storage._getSize(direct.getPath(), size);
                }else
                if(direct.isFile()){
                    size+=direct.length();
                }
            }
        }else
        if(directory.isFile()){
            size+=directory.length();
        }else{
            size=-1;
        }
        return size;
    }
    
    // Enlistar archivos y/o carpetas
    public static String[] listDirectory(String path){
        return Storage._listDirectory(path,DirectoryType.ALL,null);
    }
    public static String[] listDirectory(String path, DirectoryType type){
        return Storage._listDirectory(path,type,null);
    }
    public static String[] listDirectory(String path, String[] exceptions){
        return Storage._listDirectory(path,DirectoryType.ALL,exceptions);
    }
    public static String[] listDirectory(String path, DirectoryType type, String[] exceptions){
        return Storage._listDirectory(path,type,exceptions);
    }
    public static String[] listDirectory(String path, String[] exceptions, DirectoryType type){
        return Storage._listDirectory(path,type,exceptions);
    }
    private static String[] _listDirectory(String path, DirectoryType type, String[] exceptions){
        if(exceptions==null){
            exceptions=new String[0];
        }
        File file=new File(path);
        String[] ficheros_temp=file.list();
        ArrayList<String> ficheros=new ArrayList<>();
        if(ficheros_temp!=null){
            for(String fichero:ficheros_temp){
                File file_temp=new File(path+"/"+fichero);
                for(int a=0; a<exceptions.length; a++){
                    fichero=fichero.replaceAll(exceptions[a],"");
                }
                if(type==DirectoryType.FOLDER || type==DirectoryType.ALL){
                    if(file_temp.isDirectory()){
                        ficheros.add(fichero);
                    }
                }
                if(type==DirectoryType.FILE || type==DirectoryType.ALL){
                    if(file_temp.isFile()){
                        ficheros.add(fichero);
                    }
                }
            }
            return ficheros.toArray(new String[ficheros.size()]);
        }
        return null;
    }
    
    // Comprimir ficheros
    public static boolean compress(String path, String name_zip){
        try{
            ZipOutputStream zos=new ZipOutputStream(new FileOutputStream(name_zip+".zip"));
            zos=Storage.compress(path,zos,"");
            zos.close();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    private static ZipOutputStream compress(String path, ZipOutputStream zos, String path_dir){
        try{
            for(String f:Storage.listDirectory(path)){
                String path_zip=path+"/"+f;
                if(Storage.isFolder(path_zip)){
                    zos=Storage.compress(path_zip,zos,(path_dir.equals("")?"":(path_dir+"\\"))+f);
                    if(zos==null){
                        return null;
                    }
                    continue;
                }
                ZipEntry ze=new ZipEntry((path_dir.equals("")?"":(path_dir+"\\"))+f);
                zos.putNextEntry(ze);
                FileInputStream fis=new FileInputStream(path_zip);
                byte[] buffer=new byte[1024];
                int len=0;
                while((len=fis.read(buffer))>0){
                    zos.write(buffer,0,len);
                }
                fis.close();
            }
            return zos;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    // Descomprimir ficheros
    public static boolean descompress(String path, String path_zip){
        try{
            ZipInputStream zis=new ZipInputStream(new FileInputStream(path_zip));
            ZipEntry zos;
            while((zos=zis.getNextEntry())!=null){
                String path_folder=path+"\\"+zos.getName();
                if(zos.isDirectory()){
                    Storage.createFolder(path_folder);
                    continue;
                }else{
                    String[] sub_path=path_folder.replace("\\","/").split("/");
                    String path_create="";
                    for(int a=0; a<sub_path.length-1; a++){
                        path_create+=sub_path[a]+"\\";
                    }
                    path_create=path_create.substring(0,path_create.length()-1);
                    Storage.createFolder(path_create);
                }
                FileOutputStream fos=new FileOutputStream(path_folder);
                int len=0;
                byte[] buffer=new byte[1024];
                while((len=zis.read(buffer))>0){
                    fos.write(buffer,0,len);
                }
                fos.close();
            }
            zis.close();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    
    // Seleccion de archivos
    public static String selectFile(){ 
        return Storage._selectFile(null,"");
    }
    public static String selectFile(JFrame frame){ 
        return Storage._selectFile(frame,"");
    }
    public static String selectFile(JFrame frame, String path_current){ 
        return Storage._selectFile(frame,path_current);
    }
    public static String selectFile(String path_current){ 
        return Storage._selectFile(null,path_current);
    }
    public static File[] selectFiles(JFrame frame, String path_current){
        return Storage._selectFiles(frame,path_current);
    }
    public static File[] selectFiles(String path_current){ 
        return Storage._selectFiles(null,path_current);
    }
    private static String _selectFile(JFrame frame, String path_current){ 
        JFileChooser chooser=new JFileChooser(path_current);
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.showOpenDialog(frame);
        try{
            return chooser.getSelectedFile().toString();
        }catch(Exception ex){
            return "";
        }
    }
    private static File[] _selectFiles(JFrame frame, String path_current){ 
        JFileChooser chooser=new JFileChooser(path_current);
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.showOpenDialog(frame);
        try{
            return chooser.getSelectedFiles();
        }catch(Exception ex){
            return null;
        }
    }
    
    // Seleccion de carpetas
    public static String selectFolder(){ 
        return Storage._selectFolder(null,"");
    }
    public static String selectFolder(JFrame frame){ 
        return Storage._selectFolder(frame,"");
    }
    public static String selectFolder(JFrame frame, String path_current){ 
        return Storage._selectFolder(frame,path_current);
    }
    public static String selectFolder(String path_current){ 
        return Storage._selectFolder(null,path_current);
    }
    private static String _selectFolder(JFrame frame, String path_current){ 
        JFileChooser chooser=new JFileChooser(path_current);
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showOpenDialog(frame);
        try{
            return chooser.getSelectedFile().toString();
        }catch(Exception ex){
            return "";
        }
    }
    
    // Saber si es una carpeta
    public static boolean isFolder(String path){
        return new File(path).isDirectory();
    }
    
    // Saber si es un archivo
    public static boolean isFile(String path){
        return new File(path).isFile();
    }
    
    public static String getExtension(String path){
        String[] path_array=path.split("\\.");
        return path_array[path_array.length-1];
    }
    
    public static String getNameNotExtension(String path){
        String[] path_array=path.replace("\\", "/").split("/");
        return (path_array[path_array.length-1]).split("\\.")[0];
    }
    
    public static String getName(String path){
        String[] path_array=path.replace("\\", "/").split("/");
        return path_array[path_array.length-1];
    }
    
    public static String getFolder(String path){
        String[] array=path.replace("\\", "/").split("/");
        return String.join("/",Arrays.copyOfRange(array,0,array.length-1));
    }
    
    // Obtener ruta donde se ejecuta el programa
    public static String getDir(){
        return new File("").getAbsolutePath().replace("\\","/");
    }
    
}
