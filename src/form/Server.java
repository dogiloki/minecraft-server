package form;

import java.io.OutputStream;
import java.util.UUID;

/**
 *
 * @author dogi_
 */

public class Server{
    
    public String id=UUID.randomUUID().toString();
    public Process process;
    public OutputStream output;
    
    public Server(Process process){
        this.process=process;
        this.output=process.getOutputStream();
    }
    
    public boolean send(String text){
        try{
            OutputStream out=this.output;
            String command=text;
            out.write((command+"\n").getBytes());
            out.flush();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    
}
