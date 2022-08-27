/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import util.Config;

/**
 *
 * @author dogi_
 */
public class Test {
    
    public Test(){
        Config config=new Config(this.getClass(),"minecraftServer.cfg",true);
        System.out.println(config.getConfigJson("folders").getValue("instances"));
    }
    
    public static void main(String[] args){
        new Test();
    }
    
}
