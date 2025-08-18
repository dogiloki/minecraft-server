package com.dogiloki.minecraftserver.core.services;

/**
 *
 * @author _dogi
 */

public class Snapshot{
    
    private final String hash;
    private final String message;
    
    public Snapshot(String hash, String message){
        this.hash=hash;
        this.message=message;
    }
    
    public String getHash(){
        return this.hash;
    }
    
    public String getMessage(){
        return this.message;
    }
    
    @Override
    public String toString(){
        return this.getMessage();
    }
    
}
