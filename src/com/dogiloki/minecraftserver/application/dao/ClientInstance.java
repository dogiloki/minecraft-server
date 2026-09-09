package com.dogiloki.minecraftserver.application.dao;

import com.dogiloki.multitaks.database.ModelDB;
import com.dogiloki.multitaks.database.annotations.Collect;
import com.google.gson.annotations.Expose;

/**
 *
 * @author _dogi
 */

@Collect(src="client_instance")
public class ClientInstance extends ModelDB{
    
    @Expose
    public String path;
    
    public ClientInstance(){
        
    }
    
}
