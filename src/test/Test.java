/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dao.Instance;
import multitaks.Config;
import multitaks.directory.ModelDirectory;
import multitaks.StorageOld;
import multitaks.annotations.directory.Directory;
import multitaks.annotations.directory.Key;
import multitaks.enums.DirectoryType;
import multitaks.enums.FieldType;

/**
 *
 * @author dogi_
 */
@Directory(type=DirectoryType.FOLDER)
public class Test extends ModelDirectory{

    @Key(value="key_name")
    public String name="Julio";
    @Key(value="key_edad")
    public int edad=20;
    @Key(value="key_peso")
    public float peso=58;
    
    public String var;
    public String temp;
    
    public Test(){
        super.run(this,"E:\\Escritorio\\inversiones");
        this.name="HOla";
        String[] folderes=this.listDirectory();
        for(String f:folderes){
            System.out.println(f);
        }
    }
    
    public static void main(String[] args){
        new Test();
    }
    
}
