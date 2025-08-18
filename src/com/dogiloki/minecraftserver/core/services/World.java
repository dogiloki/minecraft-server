package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.minecraftserver.core.entities.ListSnapshots;
import com.dogiloki.multitaks.directory.ModelDirectory;
import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.dogiloki.multitaks.logger.AppLogger;
import com.dogiloki.multitaks.persistent.ExecutionObserver;
import java.io.File;
import javax.swing.Icon;

/**
 *
 * @author dogi_
 */

@Directory(type=DirectoryType.FOLDER)
public class World extends ModelDirectory{
    
    public static String DEFAULT_NAME="world";
    public static String GIT_PATH="git";
    
    public final ExecutionObserver execution;
    public final Icon icon=null;
    public final File git;
    //public String branch="master";
    public String tmp_branch="tmp";
    
    public World(String path){
        super.aim(path);
        this.exists(true);
        this.execution=new ExecutionObserver().context(this.getSrc()).waitCompletion(true);
        this.git=new File(this.getSrc()+"/.git");
        //this.icon= // Pendiente a obtener el icono
    }
    
    public boolean isGit(){
        try{
            /*
            boolean rev_parse=false;
            boolean exists=this.git.exists();
            this.execution.onOutput=(line,posi)->{
                
            };
            this.execution.onFinalized=(output,code)->{
                rev_parse=output.trim().equals("true");
            };
            this.execution.command(World.GIT_PATH+" rev-parse --is-inside-work-tree").start();
            */
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return this.git.exists();
    }
    
    // Iniciar repositorio si no existe
    public void initGit(){
        try{
            if(this.isGit()) return;
            
            this.execution.onOutput=(line,posi)->{
                AppLogger.info(line);
            };
            
            // Iniciar repositorio
            this.execution.command(World.GIT_PATH+" init").start();
            AppLogger.info("Se creó repositorio: "+this.getSrc());
            
            // Agregar todo el contenido inicial al mundo y crear commit inicial
            this.snapshot("Initial copy of the world");
        }catch(Exception ex){
            ex.printStackTrace();
            AppLogger.error(ex.getMessage());
        }
    }
    
    public void snapshot(String message){
        try{
            this.execution.onOutput=(line,posi)->{
                AppLogger.info(line);
            };
            
            // Agregar cambios
            this.execution.command(World.GIT_PATH+" add .").start();
            AppLogger.info("Se agregó el contenido del mundo: "+this.getSrc());
            
            // Intentar hacer commit
            this.execution.onFinalized=(output,code)->{
                if(output.contains("nothing to commit")){
                    AppLogger.logger().showMessage();
                    AppLogger.warning("No hay cambios en el mundo: "+this.getSrc());
                }else{
                    AppLogger.info("Se creó el respaldo \""+message+"\": "+this.getSrc());
                }
            };
            this.execution.command(World.GIT_PATH+" commit -m \""+message+"\"").start();
            this.execution.onFinalized=(outpur,code)->{
                
            };
        }catch(Exception ex){
            ex.printStackTrace();
            AppLogger.error(ex.getMessage());
        }
    }
    
    public ListSnapshots listSnapshots(){
        ListSnapshots list=new ListSnapshots();
        try{
            this.execution.onOutput=(line,code)->{
                int first_space=line.indexOf(" ");
                if(first_space>0){
                    list.append(new Snapshot(line.substring(0,first_space),line.substring(first_space+1)));
                }
            };
            this.execution.command(World.GIT_PATH+" log --pretty=format:\"%H %s\"").start();
        }catch(Exception ex){
            ex.printStackTrace();
            AppLogger.error(ex.getMessage());
        }
        return list;
    }
    
    public void checkout(Snapshot snap){
        try{
            this.execution.onOutput=(line,posi)->{
                AppLogger.info(line);
            };
            this.execution.command(World.GIT_PATH+" checkout "+snap.getHash()).start();
            AppLogger.logger().showMessage();
            AppLogger.info("Se posisionó en el respaldo \""+snap.getMessage()+"\" ("+snap.getHash()+"): "+this.getSrc());
        }catch(Exception ex){
            ex.printStackTrace();
            AppLogger.error(ex.getMessage());
        }
    }
    
}
