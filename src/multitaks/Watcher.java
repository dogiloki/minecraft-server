package multitaks;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.lang.reflect.Method;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 *
 * @author dogi_
 */

public class Watcher implements Runnable{
    
    public Path directorio=null;
    public Method[] methods=null;
    public String[] str_methods=null;
    public Class object=null;
    public Object context=null;
    public boolean terminar=false;
    
    public Watcher(String ruta, Object context, Method... methods){
        this.methods(methods);
        this.watcher(ruta,context,null);
    }
    
    public Watcher(String ruta, Object context, Class object, String... methods){
        this.methods(methods);
        this.watcher(ruta,context,object);
    }
    
    private void watcher(String ruta, Object context, Class object){
        this.directorio=Paths.get(ruta);
        this.context=context;
        this.object=object;
        new Thread(this).start();
    }
    
    private void methods(Method[] methods){
        this.methods=methods;
    }
    
    private void methods(String[] methods){
        this.str_methods=methods;
    }
    
    @Override
    public void run(){
        
        if(this.directorio==null){
            System.out.println("No existe el directorio para Watcher");
        }
        try{
            // Solicitar servicio WatchService
            WatchService watch_service=this.directorio.getFileSystem().newWatchService();
            // Registrar los eventos que se van a monitoreas
            this.directorio.register(watch_service,new WatchEvent.Kind[]{ENTRY_CREATE,ENTRY_DELETE,ENTRY_MODIFY});
            // Poner a la espera de algun evento que se esta monitoreando
            WatchKey key=watch_service.take();
            while(key!=null && !this.terminar){
                key.pollEvents().forEach((evt)->{
                    // Algún evento ya detectado
                    if(this.methods!=null){
                        for(Method method:this.methods){
                            try{
                                method.invoke(this.context);
                            }catch(Exception ex){
                                System.out.println(ex.getCause());
                            }
                        }
                    }else{
                        for(String method:this.str_methods){
                            try{
                                this.object.getMethod(method).invoke(this.context);
                            }catch(Exception ex){
                                System.out.println(ex.getCause());
                            }
                        }
                    }
                });
            }
        }catch(InterruptedException | IOException ex){
            System.out.println(ex);
        }
        
    }
    
}
