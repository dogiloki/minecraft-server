package com.dogiloki.minecraftserver.core.entities;

import com.dogiloki.multitaks.directory.HashFields;
import com.dogiloki.multitaks.directory.ListFields;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.querz.nbt.tag.ByteTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.ListTag;
import net.querz.nbt.tag.ShortTag;
import net.querz.nbt.tag.StringTag;
import net.querz.nbt.tag.Tag;

/**
 *
 * @author _dogi
 */

public class NBTNode{
    
    private String name;
    private Tag<?> tag;
    
    public NBTNode(String name, Tag<?> tag){
        this.name=name;
        this.tag=tag;
    }
    
    public String getName(){
        return this.name;
    }
    
    public Tag<?> getTag(){
        return this.tag;
    }
    
    public CompoundTag getCompound(){
        return (CompoundTag)this.getTag();
    }
    
    public void setTag(Tag<?> tag){
        this.tag=tag;
    }
    
    public boolean isCompound(){
        return tag instanceof CompoundTag;
    }
    
    @Override
    public String toString(){
        if(this.getTag() instanceof ListTag){
            ListTag<?> list=(ListTag<?>)this.getTag();
            return list.size()>0?StreamSupport.stream(
                        list.spliterator(),
                        false
                    )
                    .map(tag->{
                        if(tag instanceof CompoundTag){
                            CompoundTag compound=(CompoundTag)tag;
                            return compound.keySet()
                                    .stream()
                                    .collect(
                                        HashFields::new,
                                        (hash,key)->hash.append(key,compound.get(key).valueToString()),
                                        (a,b)->b.forEach(a::put)
                                    );
                        }
                        return tag.valueToString();
                    })
                    .collect(
                        Collectors.toCollection(ListFields::new)
                    )
                    .toString():new ListFields().toString();
        }
        return this.getTag().valueToString();
    }
    
}
