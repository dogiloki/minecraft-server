package com.dogiloki.minecraftserver.core.entities.enums;

/**
 *
 * @author _dogi
 */

public enum WorldState{
    
    NOT_INITIALIZED("No existe respositorio git"),
    INITIALIZED("Respositorio git creado"),
    CLEAN("Sin cambios pendientes"),
    DIRTY("Hay cambios sin respaldar"),
    COMMIT_IN_PROGRESS("Hay otro proceso en curso"),
    COMMITTED("Se creó el respaldo"),
    CHECKED_OUT("Esta cargado el respaldo: {0}"),
    ERROR("Fallo inesperado");
    
    private String str;
    
    private WorldState(String str){
        this.str=str;
    }
    
    @Override
    public String toString(){
        return this.str;
    }
    
}
