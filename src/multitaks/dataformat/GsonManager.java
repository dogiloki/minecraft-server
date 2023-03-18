package multitaks.dataformat;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.List;
import java.util.Map;
import multitaks.StorageOld;
import multitaks.enums.DirectoryType;
import multitaks.interfaces.DataFormat;

/**
 *
 * @author dogi_
 */

public class GsonManager implements DataFormat{

    public String json;
    private JsonParser parser;
    private JsonArray array;
    private JsonElement element;
    private JsonObject object;
    private int indice;
    private boolean is_array;

    public GsonManager(String json){
        this.indice=0;
        this.json=json==null || json.trim().equals("")?null:json.trim();
        this.constructer();
    }
    
    public GsonManager(String dir, Class _class, DirectoryType type){
        this.indice=0;
        switch(type){
            case FILE: this.json=String.join(" ",StorageOld.readFile(_class,dir.trim())); break;
        }
        this.constructer();
    }
    
    public GsonManager(String dir, DirectoryType type){
        this.indice=0;
        switch(type){
            case FILE: this.json=String.join(" ",StorageOld.readFile(dir.trim())); break;
        }
        this.constructer();
    }
    
    private void constructer(){
        if(this.json==null){
            this.object=new JsonObject();
            return;
        }
        this.parser=new JsonParser();
        this.json=(this.json==null || this.json.equals(""))?"[]":this.json;
        this.is_array=this.json.substring(0,1).equals("[") && this.json.substring(this.json.length()-1,this.json.length()).equals("]");
        if(this.is_array){
            this.array=this.parser.parse(this.json).getAsJsonArray();
            this.element=this.array.get(this.indice);
            this.object=this.element.getAsJsonObject();
        }else{
            this.object=this.parser.parse(this.json).getAsJsonObject();
        }
    }
    
    public boolean isArray(){
        return this.is_array;
    }

    public JsonArray getArray(){
        return this.is_array?this.array:new JsonArray();
    }

    public JsonObject getObject(){
        return this.is_array?this.object:new JsonObject();
    }

    public JsonObject selectObject(int indice){
        if(this.is_array){
            this.indice=indice;
            this.element=this.array.get(this.indice);
            this.object=this.element.getAsJsonObject();
            return this.object;
        }else{
            return new JsonObject();
        }
    }

    public boolean nextObject(){
        if(this.array.size()<=0){
            return false;
        }
        if(this.is_array){
        	if(this.indice<this.array.size()){
            	this.element=this.array.get(this.indice);
            	this.object=this.element.getAsJsonObject();
            	this.indice++;
                return this.indice<=this.array.size() || this.indice==1;
        	}else{
                return false;
            }
        }else{
            return false;
        }
    }
    
    public String getJson(){
        return this.json;
    }

    public Object getValue(String key){
        JsonElement object=this.object.get(key);
        if(object==null){
            return null;
        }
        return object;
    }
    
    public GsonManager getJson(String key){
        JsonElement object=this.object.get(key);
        if(object==null){
            return new GsonManager(null);
        }
        return new GsonManager(object.getAsJsonObject().toString());
    }
    
    public GsonManager getJsonArray(String key){
        JsonElement object=this.object.get(key);
        if(object==null){
            return new GsonManager(null);
        }
        return new GsonManager(object.getAsJsonArray().toString());
    }
    
    public GsonManager searchArray(GsonManager jsonArray, String key, String value){
        jsonArray.selectObject(0);
        while(jsonArray.nextObject()){
            if(jsonArray.getValue(key).equals(value)){
                return jsonArray;
            }
        }
        return null;
    }

    // Crear texto json a partir de un objeto
    public static String createJson(List json){
        return new Gson().toJson(json);
    }
    public static String createJson(Object json){
        return new Gson().toJson(json);
    }
    public static String createJson(Map<String,Object> json){
        return new Gson().toJson(json);
    }

}