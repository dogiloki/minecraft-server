package com.dogiloki.minecraftserver.core.entities.enums;

/**
 *
 * @author _dogi
 */

public enum NBTType{
    
    END(0),
    BYTE(1),
    SHORT(2),
    INT(3),
    LONG(4),
    FLOAT(5),
    DOUBLE(6),
    BYTE_ARRAY(7),
    STRING(8),
    LIST(9),
    COMPOUND(10),
    INT_ARRAY(11),
    LONG_ARRAY(12);
    
    private final Integer id;
    
    private NBTType(Integer id){
        this.id=id;
    }
    
    public Integer getID(){
        return this.id;
    }
    
    public static NBTType fromId(int id){
        for(NBTType type:values()){
            if(type.id==id) return type;
        }
        return null;
    }
    
}
