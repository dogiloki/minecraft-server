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
import org.eclipse.jgit.api.CherryPickResult;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;

/**
 *
 * @author dogi_
 */

@Directory(type=DirectoryType.FOLDER)
public final class World extends ModelDirectory{
    
    public static String DEFAULT_NAME="world";
    public static String GIT_PATH="git";
    
    private final Icon icon=null;
    private File git_dir;
    private Repository repo;
    private Git git;
    private String main_branch="master";
    private String tmp_branch="tmp";
    private File git_lock;
    private File world_lock;
    
    public World(String path){
        super.aim(path);
        this.exists(true);
        this.world_lock=new File(this.getSrc(),"session.lock");
        this.git_dir=new File(this.getSrc()+"/.git");
        this.git_lock=new File(this.git_dir,"index.lock");
        //this.icon= // Pendiente a obtener el icono
        try{
            this.repo=new RepositoryBuilder()
                    .setGitDir(this.git_dir)
                    .readEnvironment()
                    .findGitDir()
                    .build();
            this.git=new Git(this.repo);
        }catch(Exception ex){
            AppLogger.debug("Error al contruir repositorio existente").exception(ex);
        }
    }
    
    // Iniciar repositorio
    public WorldState initializeRepository(){
        try{
            if(this.hasGitRepository()) return WorldState.INITIALIZED;
            this.git=Git.init().setDirectory(this.getFile()).setInitialBranch(this.main_branch).call();
            this.repo=this.git.getRepository();
            if(new Storage(this.getSrc()+"/level.dat").exists()){
                return this.createSnapshot("Respaldo inicial del mundo");
            }
            return this.getState();
        }catch(Exception ex){
            AppLogger.error(ex.getMessage()).exception(ex);
        }
        return WorldState.ERROR;
    }
    
    // Verificar si el mundo esta bloqueado
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
            AppLogger.debug("Error al verificar si el mundo esta bloqueado").exception(ex);
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
        return (this.repo!=null && this.repo.getDirectory().exists());
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
            if(this.repo.resolve(Constants.HEAD)==null){
                return WorldState.DIRTY;
            }
            Status status=this.git.status().call();
            return status.isClean()?WorldState.CLEAN:WorldState.DIRTY;
        }catch(Exception ex){
            AppLogger.error("Error al obtener estado del mundo segun el repositorio").exception(ex);
            return WorldState.ERROR;
        }
    }
    
    public String getCurrentBranch(){
        try{
            return this.repo.getBranch();
        }catch(Exception ex){
            AppLogger.error("Error al obtener rama del repositorio").exception(ex);
        }
        return null;
    }
    
    public ExecutionObserver executeGitCommand(String command){
        return new ExecutionObserver()
                    .context(this.getSrc())
                    .waitCompletion()
                    .command(World.GIT_PATH+" "+command);
    }
    
    public WorldState createSnapshot(String message){
        try{
            WorldState state=this.getState();
            switch(state){
                case NOT_INITIALIZED:
                case COMMIT_IN_PROGRESS:{
                    AppLogger.warning(state.toString());
                    return state;
                }
                case DIRTY:{
                    // Aregar cambios
                    ExecutionObserver add=this.executeGitCommand("add .");
                    add.start();
                    if(add.exitCode()==0){
                        AppLogger.info("Se agregó el contenido del mundo: "+this.getSrc());
                    }else{
                        AppLogger.error("Error al agregar el contenido del mundo: "+this.getSrc()).showMessage();
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
            AppLogger.error("Error al crear respaldo").exception(ex);
        }
        return WorldState.ERROR;
    }
    
    public ListSnapshots getSnapshots(){
        ListSnapshots list=new ListSnapshots();
        try{
            if(this.repo.resolve(Constants.HEAD)==null){
                return list;
            }
            Iterable<RevCommit> logs=this.git.log().setMaxCount(10).call();
            for(RevCommit commit:logs){
                list.append(new Snapshot(commit.getId().getName(),commit.getShortMessage()));
            }
        }catch(Exception ex){
            AppLogger.error("Error al obtener respaldos").exception(ex);
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
                    this.git.checkout()
                            .setName(this.tmp_branch)
                            .setCreateBranch(true)
                            .setStartPoint(snap.getHash())
                            .call();
                    AppLogger.info("Se cargó el respaldo \""+snap.getMessage()+"\" ("+snap.getHash()+"): "+this.getSrc()).showMessage();
                    return WorldState.CHECKED_OUT;
                }
                case ERROR:{
                    AppLogger.error(state.toString());
                    return state;
                }
                default: return WorldState.ERROR;
            }
        }catch(Exception ex){
            AppLogger.error("Error al cargar respaldo").exception(ex);
        }
        return WorldState.ERROR;
    }
    
    public WorldState discardChanges(){
        try{
            WorldState state=this.getState();
            switch(state){
                case NOT_INITIALIZED:
                case COMMIT_IN_PROGRESS:{
                    AppLogger.warning(state.toString());
                    return state;
                }
                case CLEAN:
                case DIRTY:{
                    this.git.clean().setForce(true).setCleanDirectories(true).call();
                    this.git.reset().setMode(ResetCommand.ResetType.HARD).call();
                    this.git.checkout().setName(this.main_branch).call();
                    Ref tmp_ref=this.repo.findRef(this.tmp_branch);
                    if(tmp_ref!=null){
                        this.git.branchDelete().setBranchNames(this.tmp_branch).setForce(true).call();
                    }
                    AppLogger.info("Cambios descartados: "+this.getSrc());
                    return WorldState.CLEAN;
                }
                case ERROR:{
                    AppLogger.error(state.toString());
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
