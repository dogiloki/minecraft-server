package multitaks;

import java.awt.Frame;
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
import java.util.Arrays;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import multitaks.directory.Storage;

/**
 *
 * @author dogi_
 */

public class StorageOld{
    
    public static final int FOLDER=0;
    public static final int FILE=1;
    public static final int ALL=2;
    public static final boolean CREATED=true;
    
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
            String[] texto=StorageOld.readFile(ruta);
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
        StorageOld._copyDirectory(path_old,path_new,null);
    }
    public static void copyDirectory(String path_old, String path_new, Frame context) throws Exception{
        StorageOld._copyDirectory(path_old,path_new,context);
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
                    StorageOld._copyDirectory(path_old+"/"+directory, path_new+"/"+directory,context);
                }else
                if(Storage.isFile(path_old+"/"+directory)){
                    StorageOld.copyFile(path_old+"/"+directory, path_new+"/"+directory);
                }
            }
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
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
                StorageOld.deleteFolder(direct.getPath());
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

}