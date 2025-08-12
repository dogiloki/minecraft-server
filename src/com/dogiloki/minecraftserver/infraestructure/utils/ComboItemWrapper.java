package com.dogiloki.minecraftserver.infraestructure.utils;

/**
 *
 * @author _dogi
 */

public class ComboItemWrapper<T>{
    
    private T value;
    private String label;
    
    public ComboItemWrapper(T value){
        this.value=value;
        this.label=null;
    }
    
    public ComboItemWrapper(T value, String label){
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
