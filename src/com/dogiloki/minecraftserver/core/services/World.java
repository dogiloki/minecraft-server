package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.minecraftserver.core.entities.ListSnapshots;
import com.dogiloki.minecraftserver.core.entities.enums.WorldState;
import static com.dogiloki.minecraftserver.core.entities.enums.WorldState.COMMIT_IN_PROGRESS;
import static com.dogiloki.minecraftserver.core.entities.enums.WorldState.ERROR;
import static com.dogiloki.minecraftserver.core.entities.enums.WorldState.NOT_INITIALIZED;
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
    
    public final Icon icon=null;
    public final File git;
    public String branch="master";
    public String tmp_branch="tmp";
    public File lock;
    
    public World(String path){
        super.aim(path);
        this.exists(true);
        this.git=new File(this.getSrc()+"/.git");
        this.lock=new File(this.git.getPath()+"/index.lock");
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
    
    public boolean isLock(){
        return this.lock.exists();
    }
    
    public WorldState getState(){
        try{
            if(!this.isGit()){
                return WorldState.NOT_INITIALIZED;
            }
            if(this.isLock()){
                return WorldState.COMMIT_IN_PROGRESS;
            }
            ExecutionObserver check_head=this.executionGit("rev-parse --verify HEAD");
            check_head.start();
            if(check_head.exitCode()!=0){
                return WorldState.DIRTY;
            }
            ExecutionObserver check=this.executionGit("diff --quiet");
            check.start();
            if(check.exitCode()!=0){
                return WorldState.DIRTY;
            }
            final boolean[] dirty={false};
            ExecutionObserver status=this.executionGit("status --porcelain");
            status.start((line,posi)->{
                if(!line.trim().isEmpty()){
                    dirty[0]=true;
                }
            });
            return dirty[0]?WorldState.DIRTY:WorldState.CLEAN;
        }catch(Exception ex){
            ex.printStackTrace();
            AppLogger.error(ex.getMessage());
            return WorldState.ERROR;
        }
    }
    
    public ExecutionObserver executionGit(String command){
        return new ExecutionObserver()
                    .context(this.getSrc())
                    .waitCompletion()
                    .command(World.GIT_PATH+" "+command);
    }
    
    // Iniciar repositorio si no existe
    public WorldState initGit(){
        try{
            if(this.isGit()) return WorldState.INITIALIZED;
            
            // Iniciar repositorio
            ExecutionObserver init=this.executionGit("init");
            init.start();
            if(init.exitCode()==0){
                AppLogger.info("Se creó repositorio: "+this.getSrc());
            }else{
                AppLogger.error("Error al crear repositorio: "+this.getSrc());
                return WorldState.NOT_INITIALIZED;
            }
            // Agregar todo el contenido inicial al mundo y crear commit inicial
            return this.snapshot("Initial copy of the world");
        }catch(Exception ex){
            ex.printStackTrace();
            AppLogger.error(ex.getMessage());
        }
        return WorldState.ERROR;
    }
    
    public String getCurrentBranch(){
        try{
            StringBuilder str=new StringBuilder();
            ExecutionObserver branch=this.executionGit("rev-parse --abbrev-ref HEAD");
            branch.start((line,posi)->{
                str.append(line.trim());
            });
            return str.toString();
        }catch(Exception ex){
            ex.printStackTrace();
            AppLogger.error(ex.getMessage());
        }
        return null;
    }
    
    public WorldState snapshot(String message){
        try{
            WorldState state=this.getState();
            switch(state){
                case NOT_INITIALIZED:
                case COMMIT_IN_PROGRESS:{
                    AppLogger.logger().showMessage();
                    AppLogger.warning(state.toString());
                    return state;
                }
                case DIRTY:{
                    // Aregar cambios
                    ExecutionObserver add=this.executionGit("add .");
                    add.start();
                    if(add.exitCode()==0){
                        AppLogger.info("Se agregó el contenido del mundo: "+this.getSrc());
                    }else{
                        AppLogger.error("Error al agregar el contenido del mundo: "+this.getSrc());
                        return state;
                    }
                    //  Hacer commit
                    ExecutionObserver commit=this.executionGit("commit -m \""+message+"\"");
                    commit.start();
                    AppLogger.logger().showMessage();
                    if(commit.exitCode()==0){
                        AppLogger.info("Se creó el respaldo mundo: "+this.getSrc());
                    }else{
                        AppLogger.error("Error al crear respaldo del mundo: "+this.getSrc());
                        return state;
                    }
                    if(this.getCurrentBranch().equals(this.tmp_branch)){
                        System.out.println(this.getCurrentBranch());
                        ExecutionObserver checkout=this.executionGit("checkout "+this.branch);
                        checkout.start();
                        ExecutionObserver temp_checkout=this.executionGit("checkout "+this.tmp_branch+" -- .");
                        temp_checkout.start();
                        ExecutionObserver branch=this.executionGit("branch -D "+this.tmp_branch);
                        branch.start();
                        return this.snapshot(message);
                    }
                    return WorldState.COMMITTED;
                }
                case CLEAN:{
                    AppLogger.logger().showMessage();
                    AppLogger.info(state.toString());
                    return state;
                }
                case ERROR:{
                    AppLogger.logger().showMessage();
                    AppLogger.error(state.toString());
                    return state;
                }
                default: return WorldState.ERROR;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            AppLogger.error(ex.getMessage());
        }
        return WorldState.ERROR;
    }
    
    public ListSnapshots listSnapshots(){
        ListSnapshots list=new ListSnapshots();
        try{
            ExecutionObserver log=this.executionGit(" log --pretty=format:\"%H %s\"");
            log.start((line,posi)->{
                int first_space=line.indexOf(" ");
                if(first_space>0){
                    list.append(new Snapshot(line.substring(0,first_space),line.substring(first_space+1)));
                }
            });
        }catch(Exception ex){
            ex.printStackTrace();
            AppLogger.error(ex.getMessage());
        }
        return list;
    }
    
    public WorldState checkout(Snapshot snap){
        try{
            WorldState state=this.getState();
            switch(state){
                case NOT_INITIALIZED:
                case COMMIT_IN_PROGRESS:
                case DIRTY:{
                    AppLogger.logger().showMessage();
                    AppLogger.warning(state.toString());
                    return state;
                }
                case CLEAN:{
                    ExecutionObserver checkout=this.executionGit("checkout -b "+this.tmp_branch+" "+snap.getHash());
                    checkout.start();
                    AppLogger.logger().showMessage();
                    if(checkout.exitCode()==0){
                        AppLogger.info("Se cargo el respaldo \""+snap.getMessage()+"\" ("+snap.getHash()+"): "+this.getSrc());
                        return WorldState.CHECKED_OUT;
                    }else{
                        AppLogger.error("Error cargar el respaldo \""+snap.getMessage()+"\" ("+snap.getHash()+"): "+this.getSrc());
                        return state;
                    }
                }
                case ERROR:{
                    AppLogger.logger().showMessage();
                    AppLogger.error(state.toString());
                    return state;
                }
                default: return WorldState.ERROR;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            AppLogger.error(ex.getMessage());
        }
        return WorldState.ERROR;
    }
    
    public void branch(String name){
        
    }
    
}
