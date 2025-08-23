package com.dogiloki.minecraftserver.core.exceptions;

/**
 *
 * @author _dogi
 */

public class InstanceConfigurationSaveException extends DomainException{
    
    @Override
    public String getMessage(){
        return "Error al guardar configuración de la Instancia";
    }
    
}
