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
import util.Storage;

/**
 *
 * @author dogi_
 */
public class Test {
    
    public Test(){
        FolderDAO f=new FolderDAO("E:\\Escritorio\\hola");
        f.delete();
        System.out.println(f.toString());
    }
    
    public static void main(String[] args){
        new Test();
    }
    
}
