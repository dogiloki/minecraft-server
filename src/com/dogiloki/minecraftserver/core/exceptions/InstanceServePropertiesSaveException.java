package com.dogiloki.minecraftserver.core.exceptions;

/**
 *
 * @author _dogi
 */

public class InstanceServePropertiesSaveException extends DomainException{
    
    @Override
    public String getMessage(){
        return "Error al guardar propiedades del servidor";
    }
    
}
