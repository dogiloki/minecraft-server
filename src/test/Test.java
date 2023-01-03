/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dao.Instance;
import util.Config;
import util.Storage;

/**
 *
 * @author dogi_
 */
public class Test {
    
    public Test(){
        try{
            Storage.deleteFolder("instances - copia");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public static void main(String[] args){
        new Test();
    }
    
}
