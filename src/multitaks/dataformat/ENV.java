package multitaks.dataformat;

import java.util.Map;
import java.util.HashMap;
import multitaks.interfaces.DataFormat;

/**
 *
 * @author dogiloki
 */

public class ENV implements DataFormat{
    
    public Map<String,Object> datas=new HashMap<>();
    
    public ENV(String text){
        this.format(text.split("\n"));
    }
    public ENV(String... args){
        this.format(args);
    }
    public ENV(Map<String,Object> datas){
        this.datas=datas;
    }
    
    private void format(String[] text){
        for(String line:text){
            if(line.substring(0,1).equals("#")){
                continue;
            }
            int index_key=0;
            int index_value=line.indexOf("=");
            int end_key=index_value;
            index_value++;
            int end_value=line.length();
            String key=line.substring(index_key,end_key);
            Object value=line.substring(index_value,end_value);
            this.datas.put(key,value);
        }
    }
    
    public Object getValue(String key){
        return this.datas.get(key);
    }
    
    @Override
    public String toString(){
        String text="";
        for(Map.Entry<String,Object> entry:this.datas.entrySet()){
            text+=entry.getKey()+"="+entry.getValue()+"\n";
        }
        return text.substring(0,text.length()-1);
    }
    
}
