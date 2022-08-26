package util;

import java.io.IOException;
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
    
    public Path directorio;
    public Method[] methods;
    public Object object;
    public boolean terminar=false;
    
    public Watcher(String ruta, Object object, Method[] methods){
        this.directorio=Paths.get(ruta);
        this.object=object;
        this.methods=methods;
        new Thread(this).start();
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
                    for(Method method:this.methods){
                        try{
                            method.invoke(this.object);
                        }catch(Exception ex){
                            System.out.println(ex);
                        }
                    }
                });
            }
        }catch(InterruptedException | IOException ex){
            System.out.println(ex);
        }
        
    }
    
}
