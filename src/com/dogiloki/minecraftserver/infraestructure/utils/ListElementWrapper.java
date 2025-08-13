package com.dogiloki.minecraftserver.infraestructure.utils;

/**
 *
 * @author _dogi
 */

public class ListElementWrapper<T>{
 
    private T value;
    private String label;
    
    public ListElementWrapper(T value){
        this.value=value;
        this.label=null;
    }
    
    public ListElementWrapper(T value, String label){
        this.value=value;
        this.label=label;
    }
    
    public T getValue(){
        return this.value;
    }
    
    @Override
    public String toString(){
        return this.label==null?this.value.toString():this.label;
    }
    
}
