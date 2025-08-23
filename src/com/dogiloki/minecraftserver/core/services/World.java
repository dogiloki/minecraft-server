package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.minecraftserver.core.entities.ListSnapshots;
import com.dogiloki.minecraftserver.core.entities.enums.WorldState;
import com.dogiloki.multitaks.directory.ModelDirectory;
import com.dogiloki.multitaks.directory.Storage;
import com.dogiloki.multitaks.directory.annotations.Directory;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.dogiloki.multitaks.logger.AppLogger;
import com.dogiloki.multitaks.persistent.ExecutionObserver;
import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import javax.swing.Icon;

/**
 *
 * @author dogi_
 */

@Directory(type=DirectoryType.FOLDER)
public class World extends ModelDirectory{
    
    public static String DEFAULT_NAME="world";
    public static String GIT_PATH="git";
    
    private final Icon icon=null;
    private final File git;
    private String main_branch="master";
    private String tmp_branch="tmp";
    private File git_lock;
    private File world_lock;
    
    public World(String path){
        super.aim(path);
        this.exists(true);
        this.git=new File(this.getSrc()+"/.git");
        this.git_lock=new File(this.git.getPath(),"index.lock");
        this.world_lock=new File(this.getSrc(),"session.lock");
        //this.icon= // Pendiente a obtener el icono
    }
    
    public boolean isWorldLocked(){
        if(!this.world_lock.exists()) return false;
        try(FileInputStream fis=new FileInputStream(this.world_lock)){
            FileChannel channel=fis.getChannel();
            // Intentar obtener lock exclusivo
            FileLock lock=channel.tryLock(0L,Long.MAX_VALUE,true); // true = modo compartido
            if(lock==null){
                return true; // Otro proceso lo está usando
            }else{
                // Se pudo tomar => No se está usando por otro proceso
                lock.release();
                return false;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            AppLogger.error(ex.getMessage());
            return true;
        }
    }
    
    public boolean hasGitRepository(){
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
    
    public boolean isGitLocked(){
        return this.git_lock.exists() || this.isWorldLocked();
    }
    
    public WorldState getState(){
        try{
            if(!this.hasGitRepository()){
                return WorldState.NOT_INITIALIZED;
            }
            if(this.isGitLocked()){
                return WorldState.COMMIT_IN_PROGRESS;
            }
            ExecutionObserver check_head=this.executeGitCommand("rev-parse --verify HEAD");
            check_head.start();
            if(check_head.exitCode()!=0){
                return WorldState.DIRTY;
            }
            ExecutionObserver check=this.executeGitCommand("diff --quiet");
            check.start();
            if(check.exitCode()!=0){
                return WorldState.DIRTY;
            }
            final boolean[] dirty={false};
            ExecutionObserver status=this.executeGitCommand("status --porcelain");
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
    
    public ExecutionObserver executeGitCommand(String command){
        return new ExecutionObserver()
                    .context(this.getSrc())
                    .waitCompletion()
                    .command(World.GIT_PATH+" "+command);
    }
    
    // Iniciar repositorio si no existe
    public WorldState initializeRepository(){
        try{
            if(this.hasGitRepository()) return WorldState.INITIALIZED;
            
            // Iniciar repositorio
            ExecutionObserver init=this.executeGitCommand("init -b "+this.main_branch);
            init.start();
            if(init.exitCode()==0){
                AppLogger.info("Se creó repositorio: "+this.getSrc());
            }else{
                AppLogger.error("Error al crear repositorio: "+this.getSrc());
                return WorldState.NOT_INITIALIZED;
            }
            // Agregar todo el contenido inicial al mundo y crear commit inicial
            return new Storage(this.getSrc()+"/level.dat").exists()?this.createSnapshot("Initial copy of the world"):this.getState();
        }catch(Exception ex){
            ex.printStackTrace();
            AppLogger.error(ex.getMessage());
        }
        return WorldState.ERROR;
    }
    
    public String getCurrentBranch(){
        try{
            StringBuilder str=new StringBuilder();
            ExecutionObserver branch=this.executeGitCommand("rev-parse --abbrev-ref HEAD");
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
    
    public WorldState createSnapshot(String message){
        try{
            WorldState state=this.getState();
            switch(state){
                case NOT_INITIALIZED:
                case COMMIT_IN_PROGRESS:{
                    AppLogger.warning(state.toString()).showMessage();
                    return state;
                }
                case DIRTY:{
                    // Aregar cambios
                    ExecutionObserver add=this.executeGitCommand("add .");
                    add.start();
                    if(add.exitCode()==0){
                        AppLogger.info("Se agregó el contenido del mundo: "+this.getSrc());
                    }else{
                        AppLogger.error("Error al agregar el contenido del mundo: "+this.getSrc());
                        return state;
                    }
                    //  Hacer commit
                    ExecutionObserver commit=this.executeGitCommand("commit -m \""+message+"\"");
                    commit.start();
                    if(commit.exitCode()==0){
                        AppLogger.info("Se creó el respaldo mundo: "+this.getSrc()).showMessage();
                    }else{
                        AppLogger.error("Error al crear respaldo del mundo: "+this.getSrc()).showMessage();
                        return state;
                    }
                    if(this.getCurrentBranch().equals(this.tmp_branch)){
                        ExecutionObserver checkout=this.executeGitCommand("checkout "+this.main_branch);
                        checkout.start();
                        ExecutionObserver temp_checkout=this.executeGitCommand("checkout "+this.tmp_branch+" -- .");
                        temp_checkout.start();
                        ExecutionObserver branch=this.executeGitCommand("branch -D "+this.tmp_branch);
                        branch.start();
                        return this.createSnapshot(message);
                    }
                    return WorldState.COMMITTED;
                }
                case CLEAN:{
                    AppLogger.info(state.toString()).showMessage();
                    return state;
                }
                case ERROR:{
                    AppLogger.error(state.toString()).showMessage();
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
    
    public ListSnapshots getSnapshots(){
        ListSnapshots list=new ListSnapshots();
        try{
            ExecutionObserver log=this.executeGitCommand("log --pretty=format:\"%H %s\" -10");
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
    
    public WorldState restoreSnapshot(Snapshot snap){
        try{
            WorldState state=this.getState();
            switch(state){
                case NOT_INITIALIZED:
                case COMMIT_IN_PROGRESS:
                case DIRTY:{
                    AppLogger.warning(state.toString()).showMessage();
                    return state;
                }
                case CLEAN:{
                    ExecutionObserver checkout=this.executeGitCommand("checkout -b "+this.tmp_branch+" "+snap.getHash());
                    checkout.start();
                    if(checkout.exitCode()==0){
                        AppLogger.info("Se cargo el respaldo \""+snap.getMessage()+"\" ("+snap.getHash()+"): "+this.getSrc()).showMessage();
                        return WorldState.CHECKED_OUT;
                    }else{
                        AppLogger.error("Error cargar el respaldo \""+snap.getMessage()+"\" ("+snap.getHash()+"): "+this.getSrc()).showMessage();
                        return state;
                    }
                }
                case ERROR:{
                    AppLogger.error(state.toString()).showMessage();
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
    
    public WorldState discardChanges(){
        try{
            WorldState state=this.getState();
            switch(state){
                case NOT_INITIALIZED:
                case COMMIT_IN_PROGRESS:{
                    AppLogger.warning(state.toString()).showMessage();
                    return state;
                }
                case DIRTY:{
                    ExecutionObserver checkout=this.executeGitCommand("checkout .");
                    checkout.start();
                    if(checkout.exitCode()==0){
                        AppLogger.info("Se descartarón los cambios actuales: "+this.getSrc()).showMessage();
                        return WorldState.CLEAN;
                    }else{
                        AppLogger.error("Error al descartar los cambios actuales: "+this.getSrc()).showMessage();
                        return WorldState.ERROR;
                    }
                }
                case CLEAN:{
                    AppLogger.info(state.toString()).showMessage();
                    return state;
                }
                case ERROR:{
                    AppLogger.error(state.toString()).showMessage();
                    return state;
                }
                default: return WorldState.ERROR;
            }
        }catch(Exception ex){
            AppLogger.error(ex.getMessage());
        }
        return WorldState.ERROR;
    }
    
}
