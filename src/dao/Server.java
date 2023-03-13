package dao;

import interfaces.DAO;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.UUID;
import util.Config;
import util.Function;
import util.Storage;

/**
 *
 * @author dogi_
 */

public final class Server implements DAO{

    public interface Callback{
        public void call();
    }
    
    public String id=UUID.randomUUID().toString();
    public Instance ins=null;
    public String name_file_run="start.bat";
    public String path_directory="";
    public String command;
    public Process process=null;
    public OutputStream output=null;
    public String text_log="";
    public Callback call_finalized=()->{};
    public Callback call_active=()->{};
    private boolean active_output=true;
    private Thread thread=null;
    
    public Server(Instance ins){
        this.ins=ins;
        this.path_directory=ins.folder_ins+"/"+Config.getDic("fo_server");
        this.create();
    }
    
    public void start(){
        try{
            // Inicializar el servidor
            this.command="cmd /c start cmd /c"+this.command;
            ProcessBuilder pb=new ProcessBuilder(this.command.split(" "));
            pb.directory(new File(this.path_directory));
            pb.redirectErrorStream(false);
            Process process=pb.start();
            this.startOutput();
            this.process=process;
            this.output=process.getOutputStream();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public Config eula(){
        if(Storage.exists(this.path_directory+"/eula.txt")){
            return new Config(this.path_directory+"/eula.txt");
        }
        return null;
    }
    
    public void startOutput(){
        // Obtener salida del servidor
        this.thread=new Thread(()->{
            try{
                BufferedReader reader=new BufferedReader(new InputStreamReader(this.process.getInputStream()));
                String line;
                while((line=reader.readLine())!=null){
                    this.text_log+=line+"\n";
                }
                this.process.waitFor();
                this.finalized();
                this.output.close();
                reader.close();
            }catch(IOException ex){
                ex.printStackTrace();
            }catch(InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        this.thread.start();
    }
    
    public boolean send(String text){
        if(this.process==null || this.output==null){
            return false;
        }
        try{
            OutputStream out=this.output;
            String command=text;
            out.write((command+"\n").getBytes());
            out.flush();
            return true;
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return false;
    }
    
    public void finalized(){
        ins.world.remove();
        this.call_finalized.call();
    }
    
    @Override
    public boolean create(){
        String fo_minecraft=Config.getDic("fo_mc")+"/"+this.ins.version;
        String fi_server=Function.assign(this.ins.file_server,MessageFormat.format(Config.getDic("fi_server"),this.ins.version));
        // Archivo ejecutable para iniciar servidor
        this.command="\""+this.ins.java_path+"\" -jar -Xmx"+ins.memory_max+" -Xms"+ins.memory_max+" ../../../"+fo_minecraft+"/"+fi_server+" nogui";
        return Storage.writeFile(this.command.split(" "), this.path_directory+"/"+this.name_file_run);
    }

    @Override
    public boolean delete() {
        return true;
    }

    @Override
    public boolean save() {
        return true;
    }
    
}
