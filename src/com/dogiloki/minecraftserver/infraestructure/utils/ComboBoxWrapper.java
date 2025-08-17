package com.dogiloki.minecraftserver.infraestructure.utils;

import javax.swing.JComboBox;

/**
 *
 * @author _dogi
 */

public class ComboBoxWrapper<T> extends JComboBox<T>{
    
    private boolean loading=false;
    
    public ComboBoxWrapper setLoading(boolean value){
        this.loading=value;
        return this;
    }
    
    public boolean isLoading(){
        return this.loading;
    }
    
}
