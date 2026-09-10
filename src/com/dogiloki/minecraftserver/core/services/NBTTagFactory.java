package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.minecraftserver.core.entities.enums.NBTType;
import net.querz.nbt.tag.ByteArrayTag;
import net.querz.nbt.tag.ByteTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.DoubleTag;
import net.querz.nbt.tag.EndTag;
import net.querz.nbt.tag.FloatTag;
import net.querz.nbt.tag.IntArrayTag;
import net.querz.nbt.tag.IntTag;
import net.querz.nbt.tag.ListTag;
import net.querz.nbt.tag.LongArrayTag;
import net.querz.nbt.tag.LongTag;
import net.querz.nbt.tag.ShortTag;
import net.querz.nbt.tag.StringTag;
import net.querz.nbt.tag.Tag;

/**
 *
 * @author _dogi
 */

public class NBTTagFactory{
    
    public static Tag<?> create(NBTType type, Object value){
        switch(type){
            case BYTE: return new ByteTag(Byte.parseByte(value.toString()));
            case SHORT: return new ShortTag(Short.parseShort(value.toString()));
            case INT: return new IntTag(Integer.parseInt(value.toString()));
            case LONG: return new LongTag(Long.parseLong(value.toString()));
            case FLOAT: return new FloatTag(Float.parseFloat(value.toString()));
            case DOUBLE: return new DoubleTag(Double.parseDouble(value.toString()));
            case STRING: return new StringTag(value.toString());
            case BYTE_ARRAY: return new ByteArrayTag(new byte[0]);
            case INT_ARRAY: return new IntArrayTag(new int[0]);
            case LONG_ARRAY: return new LongArrayTag(new long[0]);
            case LIST: return new ListTag(EndTag.class);
            case COMPOUND: return new CompoundTag();
            case END:
            default:
                throw new IllegalArgumentException("Tipo de NBT no válido "+type);
            
        }
    }
    
}
