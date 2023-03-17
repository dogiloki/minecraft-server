/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dao.FileDAO;
import dao.FolderDAO;
import dao.Instance;
import util.Config;
import util.directory.ModelDirectory;
import util.Storage;
import util.annotations.directory.Directory;
import util.annotations.directory.Key;
import util.enums.DirectoryType;
import util.enums.FieldType;

/**
 *
 * @author dogi_
 */
@Directory(type=DirectoryType.FOLDER)
public class Test extends ModelDirectory{

    @Key(value="key_name")
    public String name="Julio";
    @Key(value="key_edad")
    public int edad;
    @Key(value="key_peso")
    public float peso=58;
    
    public String var;
    public String temp;
    
    public Test(){
        super.run(this,"E:\\Escritorio\\prueba.json");
        this.save();
    }
    
    public static void main(String[] args){
        new Test();
    }
    
}
