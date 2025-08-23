package com.dogiloki.minecraftserver.core.exceptions;

/**
 *
 * @author _dogi
 */

public class DomainException extends RuntimeException{
    
    public static final String DEFAULT_MESSAGE="";
    
    @Override
    public String getMessage(){
        return DomainException.DEFAULT_MESSAGE;
    }
    
}
