package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.minecraftserver.core.entities.NBTNode;
import com.dogiloki.multitaks.datastructure.Node;
import com.dogiloki.multitaks.datastructure.tree.Tree;
import java.io.File;
import java.io.IOException;
import net.querz.nbt.io.NBTDeserializer;
import net.querz.nbt.io.NBTSerializer;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.ByteArrayTag;
import net.querz.nbt.tag.ByteTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.IntArrayTag;
import net.querz.nbt.tag.IntTag;
import net.querz.nbt.tag.ListTag;
import net.querz.nbt.tag.LongArrayTag;
import net.querz.nbt.tag.LongTag;
import net.querz.nbt.tag.Tag;

/**
 *
 * @author _dogi
 */

public class NBTService{
    
    private final File file;
    private Tree<NBTNode> tree;
    
    public NBTService(File file)throws IOException{
        this.file=file;
        this.initialize();
    }
    
    public NBTService(String path)throws IOException{
        this.file=new File(path);
        this.initialize();
    }
    
    private void initialize()throws IOException{
        NBTDeserializer des=new NBTDeserializer();
        NamedTag named_tag=des.fromFile(this.file);
        this.tree=new Tree<>();
        this.tree.onKey((node)->node.getName());
        NBTNode root=new NBTNode(named_tag.getName(),named_tag.getTag());
        this.tree.root(root);
        this.build(this.tree.rootNode(),named_tag.getTag());
    }
    
    public Tree<NBTNode> tree(){
        return this.tree;
    }
    
    private void build(Node<NBTNode> parent, Tag<?> tag){
        if(tag instanceof CompoundTag){
            CompoundTag compound=(CompoundTag)tag;
            for(String name:compound.keySet()){
                Tag<?> child_tag=compound.get(name);
                Node<NBTNode> child=this.tree().add(parent,new NBTNode(name,child_tag));
                this.build(child,child_tag);
            }
        }else if(tag instanceof ListTag){
            ListTag<?> list=(ListTag<?>)tag;
            for(int a=0; a<list.size(); a++){
                Tag<?> child_tag=list.get(a);
                Node<NBTNode> child=this.tree().add(parent,new NBTNode(String.valueOf(a),child_tag));
                this.build(child,child_tag);
            }
        }else if(tag instanceof ByteArrayTag){
            ByteArrayTag array=(ByteArrayTag)tag;
            int index=0;
            for(byte value:array.getValue()){
                Node<NBTNode> child=this.tree().add(parent,new NBTNode(String.valueOf(index++),new ByteTag(value)));
            }
        }else if(tag instanceof IntArrayTag){
            IntArrayTag array=(IntArrayTag)tag;
            int index=0;
            for(Integer value:array.getValue()){
                Node<NBTNode> child=this.tree().add(parent,new NBTNode(String.valueOf(index++),new IntTag(value)));
            }
        }
        else if(tag instanceof LongArrayTag){
            LongArrayTag array=(LongArrayTag)tag;
            int index=0;
            for(Long value:array.getValue()){
                Node<NBTNode> child=this.tree().add(parent,new NBTNode(String.valueOf(index++),new LongTag(value)));
            }
        }
    }
    
    public Node<NBTNode> getLeaf(String path){
        return this.tree().index().get(path);
    }
    
    public Node<NBTNode> getNode(String path){
        String[] parts=path.split("\\.");
        Node<NBTNode> current=this.tree().rootNode();
        for(String part:parts){
            boolean found=false;
            for(Node<NBTNode> child:current.childNodes()){
                if(child.getValue().getName().equals(part)){
                    current=child;
                    found=true;
                    break;
                }
            }
            if(!found){
                return null;
            }
        }
        return current;
    }
    
    public void save()throws IOException{
        NBTNode root=this.tree.rootNode().getValue();
        NBTSerializer serializer=new NBTSerializer();
        NamedTag named_tag=new NamedTag(root.getName(),root.getTag());
        System.out.println(((CompoundTag)named_tag.getTag()).valueToString());
        serializer.toFile(named_tag,this.file);
    }
    
}
