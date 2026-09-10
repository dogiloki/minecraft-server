package com.dogiloki.minecraftserver.core.entities;

import com.dogiloki.minecraftserver.core.entities.enums.NBTType;
import com.dogiloki.multitaks.directory.HashFields;
import com.dogiloki.multitaks.directory.ListFields;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.querz.nbt.tag.ByteArrayTag;
import net.querz.nbt.tag.ByteTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.DoubleTag;
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

public class NBTNode{
    
    private String path;
    private String name;
    private Tag<?> tag;
    private NBTType type;
    private Object value;
    
    public NBTNode(String name, Tag<?> tag){
        this.name=name;
        this.tag=tag;
        this.type=NBTType.fromId(this.tag.getID());
        this.value=getValue(this.tag);
    }
    
    public void setPath(String value){
        this.path=value;
    }
    
    public String getPath(){
        return this.path;
    }
    
    public String getName(){
        return this.name;
    }
    
    public Tag<?> getTag(){
        return this.tag;
    }
    
    public NBTType getType(){
        return this.type;
    }
    
    public void setTag(Tag<?> tag){
        this.tag=tag;
        this.type=NBTType.fromId(this.tag.getID());
        this.value=getValue(this.tag);
    }
    
    public Object getValue(){
        return this.value;
    }
    
    private Object getValue(Tag<?> tag){
        if(tag instanceof CompoundTag){
            CompoundTag compound=(CompoundTag)tag;
            return compound.keySet()
                    .stream()
                    .collect(
                        HashFields::new,
                        (hash,key)->hash.append(key,getValue(compound.get(key))),
                        (a,b)->b.forEach(a::put)
                    );
        }
        if(tag instanceof ListTag){
            ListTag<?> list=(ListTag<?>)tag;
            return list.size()>0?
                    StreamSupport.stream(list.spliterator(),false)
                    .map(this::getValue)
                    .collect(Collectors.toCollection(ListFields::new)):
                    new ListFields();
        }
        if(tag instanceof IntTag){
            return ((IntTag)tag).asInt();
        }
        if(tag instanceof ShortTag){
            return ((ShortTag)tag).asLong();
        }
        if(tag instanceof ByteTag){
            return ((ByteTag)tag).asByte();
        }
        if(tag instanceof LongTag){
            return ((LongTag)tag).asLong();
        }
        if(tag instanceof FloatTag){
            return ((FloatTag)tag).asFloat();
        }
        if(tag instanceof DoubleTag){
            return ((DoubleTag)tag).asDouble();
        }
        if(tag instanceof StringTag){
            return ((StringTag)tag).getValue();
        }
        if(tag instanceof ByteArrayTag){
            return ((ByteArrayTag)tag).getValue();
        }
        if(tag instanceof IntArrayTag){
            return ((IntArrayTag)tag).getValue();
        }
        if(tag instanceof LongArrayTag){
            return ((LongArrayTag)tag).getValue();
        }
        return tag.valueToString();
    }
    
    @Override
    public String toString(){
        return this.getValue(this.getTag()).toString();
    }
    
}
