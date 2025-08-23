package com.dogiloki.minecraftserver.core.exceptions;

/**
 *
 * @author _dogi
 */

public class InstanceSaveException extends DomainException{
    
    @Override
    public String getMessage(){
        return "Error al guardar Instancia";
    }
    
}
