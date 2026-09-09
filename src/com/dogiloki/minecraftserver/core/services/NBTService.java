package com.dogiloki.minecraftserver.core.services;

import com.dogiloki.minecraftserver.core.entities.NBTNode;
import com.dogiloki.multitaks.datastructure.Node;
import com.dogiloki.multitaks.datastructure.tree.Tree;
import java.io.File;
import java.io.IOException;
import net.querz.nbt.io.NBTDeserializer;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;
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
                Node<NBTNode> child=this.tree.add(parent,new NBTNode(name,child_tag));
                this.build(child,child_tag);
            }
        }
    }
    
    public Node<NBTNode> getNode(String path){
        String[] parts=path.split("\\.");
        Node<NBTNode> current=this.tree.rootNode();
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
    
}
