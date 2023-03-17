package util.directory;

import com.google.gson.Gson;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import util.annotations.directory.Directory;
import util.annotations.directory.Key;
import util.enums.DirectoryType;
import util.enums.FieldType;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.dataformat.GsonManager;
import util.dataformat.ENV;
import util.interfaces.DataFormat;

/**
 *
 * @author dogi_
 */
public class ModelDirectory extends Storage{
    
    Class<?> child_class;
    public Object instance;
    Map<String,Object> fields=new HashMap<>();
    Map<String,String> attributes=new HashMap<>();

    public ModelDirectory(){
        super();
    }

    public void run(Object instance){
        this._run(instance,null);
    }
    public void run(Object instance, String src){
        this._run(instance,src);
    }
    
    private void _run(Object instance, String src){
        this.instance=instance;
        this.child_class=instance.getClass();
        this.src=src;
        Directory annot_directory=this.child_class.getAnnotation(Directory.class);
        if(annot_directory instanceof Directory){
            this.type=annot_directory.type();
        }
        this.create();
        super.run(src,this.type);
    }
    
    private void create(){
        if(this.type==null || this.src==null){
            return;
        }
        if(this.isFolder()){
            Storage.createFolder(this.src);
        }else{
            Storage.exists(this.src,DirectoryType.FILE,true);
            this.setText();
        }
    }
    
    private void get(){
        try{
            Directory annot_directory=this.child_class.getAnnotation(Directory.class);
            if(annot_directory instanceof Directory){
                this.type=annot_directory.type();
                if(this.isFolder()){
                    return;
                }
            }
            for(Field field:this.child_class.getDeclaredFields()){
                Key annot_key=field.getAnnotation(Key.class);
                if(annot_key instanceof Key){
                    Object value=field.get(this.instance);
                    if(annot_key.type()==FieldType.CLASS && value!=null){
                        ModelDirectory model=new ModelDirectory();
                        model.run(value);
                        value=model.getText();
                    }else{
                        if(value==null){
                            value="";
                        }else{
                            value=field.getType().isPrimitive()?field.get(this.instance):String.valueOf(field.get(this.instance));
                        }
                    }
                    this.fields.put(annot_key.value(),value);
                    this.attributes.put(annot_key.value(),field.getName());
                }
            }
        }catch(IllegalAccessException ex){
            ex.printStackTrace();
        }
    }
    
    private Object setText(){
        this.get();
        String text=this.read();
        if(text==null || text.equals("")){
            return null;
        }
        DataFormat data;
        switch(this.type){
            case JSON: data=new GsonManager(text); break;
            case ENV: data=new ENV(text); break;
            default: data=null; break;
        }
        if(data==null){
            return null;
        }
        for(Map.Entry<String,String> entry:this.attributes.entrySet()){
            String key=entry.getKey();
            String name=entry.getValue();
            try{
                Object value=data.getValue(key);
                Field field=this.child_class.getField(name);
                Constructor<?> constructor;
                if(field.getType().isPrimitive()){
                    String class_name=field.getType().getName();
                    if(int.class.equals(field.getType())){
                        class_name+="eger";
                    }
                    Class<?> class_type=Class.forName("java.lang."+class_name.substring(0,1).toUpperCase()+class_name.substring(1));
                    constructor=class_type.getDeclaredConstructor(value.getClass());
                }else{
                    constructor=field.getType().getDeclaredConstructor(value.getClass());
                }
                field.set(this.instance,constructor.newInstance(value));
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return text;
    }
    
    private String getText(){
        if(this.type==null){
            return null;
        }
        this.get();
        switch(this.type){
            case JSON: return new Gson().toJson(this.fields);
            case ENV: return new ENV(this.fields).toString()+"\n";
            case XML:{
                try{
                    String xml="";
                    for(Map.Entry<String,Object> entry:this.fields.entrySet()){
                        String key=entry.getKey();
                        Object value=entry.getValue();
                        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder=factory.newDocumentBuilder();
                        Document document=builder.newDocument();
                        Element root_element=document.createElement(key);
                        root_element.appendChild(document.createTextNode(String.valueOf(value)));
                        document.appendChild(root_element);
                        TransformerFactory transformer_factory=TransformerFactory.newInstance();
                        Transformer transformer=transformer_factory.newTransformer();
                        DOMSource source=new DOMSource(document);
                        StringWriter writer=new StringWriter();
                        StreamResult result=new StreamResult(writer);
                        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
                        transformer.transform(source,result);
                        xml+=writer.toString()+"\n";
                    }
                    return xml.substring(0,xml.length()-1);
                }catch(Exception ex){
                    ex.printStackTrace();
                    return "";
                }
            }
            default: return null;
        }
    }
    
    public boolean save(){
        if(this.type!=null && this.src!=null && this.isFile()){
            this.clean();
            return this.write(this.getText());
        }
        return false;
    }
    
    public boolean isFile(){
        if(this.type==null){
            return false;
        }
        switch(this.type){
            case JSON: return true;
            case XML: return true;
            case ENV: return true;
            default: return false;
        }
    }
    
    public boolean isFolder(){
        if(this.type==null){
            return false;
        }
        return this.type==DirectoryType.FOLDER;
    }
    
}
