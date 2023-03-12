package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;
import javax.swing.JTextArea;
import util.Storage;

/**
 *
 * @author dogi_
 */

public class Server{
    
    interface Callback{
        void call();
    }
    
    public String id=UUID.randomUUID().toString();
    public String command="";
    public String path_directory="";
    public Process process=null;
    public OutputStream output=null;
    public String text_log="";
    private boolean active_output=true;
    private Thread thread=null;
    public Callback call_finalized;
    
    public Server(String command, String path_directory){
        this.command="cmd /c start cmd /c"+command;
        this.path_directory=path_directory;
        this.setProcess();
    }
    
    private void setProcess(){
        try{
            // Inicializar el servidor
            ProcessBuilder pb=new ProcessBuilder(this.command.split(" "));
            pb.directory(new File(this.path_directory));
            pb.redirectErrorStream(false);
            Process process=pb.start();
            this.startOutput();
            this.process=process;
            this.output=process.getOutputStream();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void getOutput(JTextArea text_area){
        text_area.setText(String.join(" ",Storage.readFile(this.path_directory+"/logs/latest.log")));
        text_area.setCaretPosition(text_area.getDocument().getLength());
    }
    
    public void startOutput(){
        // Obtener salida del servidor
        this.active_output=true;
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
        this.call_finalized.call();
    }
    
}
