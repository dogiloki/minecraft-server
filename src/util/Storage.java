package util;

import java.awt.Frame;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author dogi_
 */

public class Storage{
    
    public static final int FOLDER=0;
    public static final int FILE=1;
    public static final int ALL=2;
    public static final boolean CREATED=true;
    
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
    
    public static boolean isFolder(String ruta){
        return new File(ruta).isDirectory();
    }
    
    public static boolean isFile(String ruta){
        return new File(ruta).isFile();
    }
    
    // Esciribir contenido en un archivo
    public static boolean writeFile(String[] lineas, String ruta){
        try{
            File directorio=new File(ruta);
            BufferedWriter bw=new BufferedWriter(new FileWriter(directorio));
            for(int a=0; a<lineas.length; a++){
                bw.write((a<lineas.length-1)?(lineas[a]+"\n"):(lineas[a]));
            }
            bw.close();
            return true;
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    public static boolean writeFile(Map<Integer,String> lineas, String ruta){
        try{
            String[] texto=Storage.readFile(ruta);
            File directorio=new File(ruta);
            BufferedWriter bw=new BufferedWriter(new FileWriter(directorio));
            String linea;
            for(int a=0; a<texto.length || lineas.get(a)!=null; a++){
                linea=lineas.get(a);
                if(linea==null){
                    linea=texto[a];
                }
                bw.write(linea+(a<texto.length-1 || lineas.get(a-1)!=null || texto.length<=1?"\n":""));
            }
            bw.close();
            return true;
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    public static boolean writeFile(String linea, String ruta){
        try{
            File directorio=new File(ruta);
            BufferedWriter bw=new BufferedWriter(new FileWriter(directorio,true));
            bw.write(linea);
            bw.close();
            return true;
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    // Obtener contenido de un archivo
    public static String[] readFile(String ruta){
        ArrayList<String> lineas=new ArrayList<>();
        try{
            InputStreamReader directorio=new InputStreamReader(new FileInputStream(ruta));
            BufferedReader bf=new BufferedReader(directorio);
            String linea;
            while((linea=bf.readLine())!=null){
                lineas.add(linea);
            }
            bf.close();
            return lineas.toArray(new String[lineas.size()]);
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.ERROR_MESSAGE);
            return new String[0];
        }
        //return lineas.toArray(new String[lineas.size()]);
    }
    public static String[] readFile(Class _class, String ruta){
        ArrayList<String> lineas=new ArrayList<>();
        try{
            InputStream in=_class.getResourceAsStream("/"+ruta);
            BufferedReader bf=new BufferedReader(new InputStreamReader(in));
            String linea;
            while((linea=bf.readLine())!=null){
                lineas.add(linea);
            }
            return lineas.toArray(new String[lineas.size()]);
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.ERROR_MESSAGE);
            return new String[0];
        }
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
    
    // Cambiar el nombre a archivos o carpetas
    public static boolean rename(String name_old, String name_new){
        File directorio_old=new File(name_old);
        File directorio_new=new File(name_new);
        return directorio_old.renameTo(directorio_new);
    }
    
    // Copiar un archivo de un lugar a otro
    public static void copyFile(String ruta_old, String ruta_new) throws Exception{
        File directory_old=new File(ruta_old);
        File directory_new=new File(ruta_new);
        if(!directory_old.exists()){
            throw new Exception("No existe "+ruta_old);
        }else
        if(!directory_old.isFile()){
            throw new Exception(ruta_old+" no es un archivo");
        }
        try{
            InputStream in=new FileInputStream(directory_old);
            OutputStream out=new FileOutputStream(directory_new);
            byte[] buf=new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            in.close();
            out.close();
        }catch(IOException ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    // Copiar todo un directorio / folder
    public static void copyDirectory(String path_old, String path_new) throws Exception{
        Storage._copyDirectory(path_old,path_new,null);
    }
    public static void copyDirectory(String path_old, String path_new, Frame context) throws Exception{
        Storage._copyDirectory(path_old,path_new,context);
    }
    
    private static void _copyDirectory(String path_old, String path_new, Frame context) throws Exception{
        File directory_old=new File(path_old);
        File directory_new=new File(path_new);
        if(!directory_old.exists()){
            throw new Exception("No existe "+path_old);
        }else
        if(directory_old.isDirectory()){
            directory_new.mkdir();
        }
        try{
            String[] directories=Storage.listDirectory(path_old);
            for(String directory:directories){
                if(Storage.isFolder(path_old+"/"+directory)){
                    Storage._copyDirectory(path_old+"/"+directory, path_new+"/"+directory,context);
                }else
                if(Storage.isFile(path_old+"/"+directory)){
                    Storage.copyFile(path_old+"/"+directory, path_new+"/"+directory);
                }
            }
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    // Saber si existe un archivo o carpeta
    public static boolean exists(String ruta){
        File directorio=new File(ruta);
        return (directorio.exists());
    }
    
    // Saber si existe un archivo o carpeta
    public static boolean exists(String ruta, boolean created, int type){
        File directorio=new File(ruta);
        if(!directorio.exists() && created){
            switch(type){
                case Storage.FOLDER: return Storage.createFolder(ruta);
                case Storage.FILE: return Storage.writeFile("",ruta);
            }
        }
        return directorio.exists();
    }
    
    // Eliminar archivo
    public static boolean deleteFile(String ruta){
        try{
            File directorio=new File(ruta);
            if(!directorio.exists()){
                return false;
            }
            return directorio.delete();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    
    // Eliminar folder
    public static void deleteFolder(String path) throws Exception{
        try{
            File direct;
            String[] directories=Storage.listDirectory(path);
            for(String directory:directories){
                direct=new File(path+"/"+directory);
                if(direct.isFile()){
                    direct.delete();
                    continue;
                }
                Storage.deleteFolder(direct.getPath());
                direct.delete();
            }
            direct=new File(path);
            if(direct.isFile()){
                return;
            }
            String[] list=direct.list();
            if(list==null || list.length<=0){
                direct.delete();
            }
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    // Seleccion de archivos
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
    
    // Enlistar archivos y/o carpetas
    public static String[] listDirectory(String ruta){
        return Storage._listDirectory(ruta,Storage.ALL,null);
    }
    public static String[] listDirectory(String ruta, int type){
        return Storage._listDirectory(ruta,type,null);
    }
    public static String[] listDirectory(String ruta, String[] exceptions){
        return Storage._listDirectory(ruta,Storage.ALL,exceptions);
    }
    public static String[] listDirectory(String ruta, int type, String[] exceptions){
        return Storage._listDirectory(ruta,type,exceptions);
    }
    public static String[] listDirectory(String ruta, String[] exceptions, int type){
        return Storage._listDirectory(ruta,type,exceptions);
    }
    private static String[] _listDirectory(String ruta, int type, String[] exceptions){
        if(exceptions==null){
            exceptions=new String[0];
        }
        File file=new File(ruta);
        String[] ficheros_temp=file.list();
        ArrayList<String> ficheros=new ArrayList<>();
        if(ficheros_temp!=null){
            for(String fichero:ficheros_temp){
                File file_temp=new File(ruta+"/"+fichero);
                for(int a=0; a<exceptions.length; a++){
                    fichero=fichero.replaceAll(exceptions[a],"");
                }
                if(type==Storage.FOLDER || type==Storage.ALL){
                    if(file_temp.isDirectory()){
                        ficheros.add(fichero);
                    }
                }
                if(type==Storage.FILE || type==Storage.ALL){
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
    
    public static String getExtension(String path){
        String[] path_array=path.split("\\.");
        return path_array[path_array.length-1];
    }
    
    // Obtener ruta donde se ejecuta el programa
    public static String getDir(){
        return new File("").getAbsolutePath().replace("\\","/");
    }

}