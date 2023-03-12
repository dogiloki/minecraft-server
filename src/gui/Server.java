package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author dogi_
 */

public class Server{
    
    public String id=UUID.randomUUID().toString();
    public String command="";
    public String path_directory="";
    public JTextArea server_text=null;
    public Process process=null;
    public OutputStream output=null;
    public String text_log="";
    private boolean active_output=true;
    private Thread thread=null;
    
    public Server(String command, String path_directory, JTextArea server_text){
        this.command="cmd /c "+command;
        this.path_directory=path_directory;
        this.server_text=server_text;
        this.setProcess();
        this.startOutput();
    }
    
    private void setProcess(){
        try{
            // Inicializar el servidor
            ProcessBuilder pb=new ProcessBuilder(this.command.split(" "));
            pb.directory(new File(this.path_directory));
            Process process=pb.start();
            this.process=process;
            this.output=process.getOutputStream();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void stopOutput(){
        this.active_output=false;
    }
    
    public void startOutput(){
        // Obtener salida del servidor
        this.active_output=true;
        this.thread=new Thread(()->{
            try{
                BufferedReader reader=new BufferedReader(new InputStreamReader(this.process.getInputStream()));
                String line;
                this.server_text.setText(this.text_log);
                while((line=reader.readLine())!=null){
                    String text=this.server_text.getText()+line+"\n";
                    this.server_text.setText(text);
                    this.text_log=this.server_text.getText();
                    this.server_text.setCaretPosition(this.server_text.getDocument().getLength());
                }
                process.waitFor();
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
    
}
