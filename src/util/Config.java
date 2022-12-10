package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dogi_
 */

public class Config{
    
    private String file;
    private Map<String,String> configurations=new HashMap<>();
    private Map<String,Integer> position=new HashMap<>();
    private int position_end=0;
    private Map<String,String> dic=new HashMap<>();
    private GsonManager gson;
    private final boolean is_json;
    
    public static final boolean JSON=true;
    public static final boolean CONFIG=true;
    
    // Configuración
    public Config(Class _class, String file, boolean is_json){
        this.file=file;
        this.is_json=is_json;
        String[] text=_class==null?Storage.readFile(this.file):Storage.readFile(_class,this.file);
        if(this.is_json){
            this.gson=new GsonManager(String.join(" ",text));
        }else{
            this.generated(text);
        }
    }
    
    public Config(Class _class, String file){
        this.file=file;
        this.is_json=false;
        String[] text=_class==null?Storage.readFile(this.file):Storage.readFile(_class,this.file);
        this.generated(text);
    }
    
    public Config(String file, boolean is_json){
        this.file=file;
        this.is_json=is_json;
        String[] text=Storage.readFile(this.file);
        if(this.is_json){
            this.gson=new GsonManager(String.join(" ",text));
        }else{
            this.generated(text);
        }
    }
    
    public Config(String file){
        this.file=file;
        this.is_json=false;
        String[] text=Storage.readFile(this.file);
        this.generated(text);
    }
    
    private void generated(String[] text){
        int contador=0;
        for(String linea:text){
            int posi_key_inicio=0;
            int posi_key_fin=linea.indexOf("=");
            boolean is_comment=(linea.length()<=0?" ":linea).trim().substring(0,1).equals("#");
            if(posi_key_fin>=0 && !is_comment){
                int posi_value_inicio=posi_key_fin+1;
                int posi_value_fin=linea.length();
                String key=linea.substring(posi_key_inicio,posi_key_fin);
                String value=linea.substring(posi_value_inicio,posi_value_fin);
                this.configurations.put(key,value);
                this.position.put(key,contador);
                this.position_end=contador+1;
            }
            contador++;
        }
    }
    
    public GsonManager getJson(){
        return this.gson;
    }
    
    public boolean isJson(){
        return this.is_json;
    }
    
    public void setConfigData(String key, String value){
        if(this.isJson()){
            
        }else{
            int linea_num=this.position.get(key)==null?this.position_end:this.position.get(key);
            String linea_texto=key+"="+value;
            Storage.writeFile(
                    new HashMap<Integer,String>(){
                        {put(linea_num,linea_texto);}
                    },this.file
            );
            this.configurations.put(key,value);
        }
    }
    
    public String getConfigData(String key){
        return this.isJson()?this.gson.getValue(key):this.configurations.get(key);
    }
    
    public GsonManager getConfigJson(String key){
        return this.gson.getJson(key);
    }
    
    public void setDic(String key, String value){
        this.dic.put(key,value);
    }
    
    public String getDic(String key){
        return this.dic.get(key);
    }
    
}
