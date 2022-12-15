package util;

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
import java.util.Map;
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
    public static final boolean CREATED=true;
    
    // Crea una carpeta
    public static boolean createFolder(String ruta){
        try{
            String ruta_crear="";
            for(String sub_ruta:ruta.split("/")){
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
            //int conta=0;
            for(int a=0; a<texto.length || lineas.get(a)!=null; a++){
                linea=lineas.get(a);
                if(linea==null){
                    linea=texto[a];
                }else{
                    //conta++;
                }
                if(linea!=null){
                    bw.write((a<texto.length-1)?(linea+"\n"):(linea));
                }
                //a=conta>=lineas.size()?texto.length:a;
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
    
    // Obtener tamaño de archivo
    public static long getSize(String ruta){
        File directorio=new File(ruta);
        return directorio.isFile()?directorio.length():-1;
    }
    
    // Cambiar el nombre a archivos o carpetas
    public static boolean rename(String name_old, String name_new){
        File directorio_old=new File(name_old);
        File directorio_new=new File(name_new);
        return directorio_old.renameTo(directorio_new);
    }
    
    // Copiar archivos de un lugar a otro
    public static void copyFile(String ruta_old, String ruta_new){
        File directorio_old=new File(ruta_old);
        File directorio_new=new File(ruta_new);
        if(directorio_old.exists()){
            try{
                InputStream in=new FileInputStream(directorio_old);
                OutputStream out=new FileOutputStream(directorio_new);
                byte[] buf=new byte[1024];
                int len;
                while((len=in.read(buf))>0){
                    out.write(buf,0,len);
                }
                in.close();
                out.close();
            }catch(IOException ex){
                JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null,"No existe "+ruta_old,"Error",JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    // Seleccion de archivos
    public static String selectFile(JFrame frame,String directorio_actual){ 
        JFileChooser chooser=new JFileChooser(directorio_actual);
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.showOpenDialog(frame);
        try{
            return chooser.getSelectedFile().toString();
        }catch(Exception ex){
            return "";
        }
    }
    
    // Seleccion de carpetas
    public static String selectFolder(JFrame frame,String directorio_actual){ 
        JFileChooser chooser=new JFileChooser(directorio_actual);
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
    public static String[] listDirectory(String ruta, boolean folders, boolean files, String[] exceptions){
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
                if(folders){
                    if(file_temp.isDirectory()){
                        ficheros.add(fichero);
                    }
                }
                if(files){
                    if(file_temp.isFile()){
                        ficheros.add(fichero);
                    }
                }
            }
            return ficheros.toArray(new String[ficheros.size()]);
        }
        return null;
    }
    
    // Ejecutar programa externo
    public static boolean execute(String path){
        try{
            ProcessBuilder pb=new ProcessBuilder(path);
            Process p=pb.start();
            return p.waitFor()==0;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    // Obtener ruta donde se ejecuta el programa
    public static String getDir(){
        return new File("").getAbsolutePath().replace("\\","/");
    }

}