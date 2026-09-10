package com.dogiloki.minecraftserver.infraestructure.ui.components;

import com.dogiloki.minecraftserver.core.entities.NBTNode;
import static com.dogiloki.minecraftserver.core.entities.enums.NBTType.BYTE;
import com.dogiloki.minecraftserver.core.services.MinecraftClient;
import com.dogiloki.minecraftserver.core.services.NBTService;
import com.dogiloki.minecraftserver.core.services.World;
import com.dogiloki.multitaks.datastructure.Node;
import com.dogiloki.multitaks.datastructure.tree.TreeNodeWrapper;
import com.dogiloki.multitaks.directory.HashFields;
import com.dogiloki.multitaks.directory.ListFields;
import com.dogiloki.multitaks.logger.AppLogger;
import java.awt.Frame;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
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

public class ClientPanel extends javax.swing.JPanel {

    public Frame parent;
    public MinecraftClient client;
    public World world;
    private NBTService nbt;
    
    public ClientPanel(Frame parent, MinecraftClient client, World world){
        initComponents();
        this.parent=parent;
        this.client=client;
        this.world=world;
        try{
            this.nbt=new NBTService(this.world.getLevelDatFile().getFile());
        }catch(Exception ex){
            AppLogger.error("Error al cargar").showMessage().exception(ex);
        }
        this.name_instance_label.setText(client.getName()+" - "+this.world.getName());
        this.loadLevelDat();
    }
    
    public void loadLevelDat(){
        if(this.nbt==null) return;
        Node<NBTNode> root_node=this.nbt.tree().rootNode();
        NBTNode root_value=root_node.getValue();
        DefaultMutableTreeNode root=new TreeNodeWrapper(root_node,root_value.getName()+" ("+root_value.getType()+")");
        for(Node<NBTNode> child:root_node.childNodes()){
            this.buildTreeNode(root,child);
        }
        this.level_tree.setModel(new DefaultTreeModel(root));
    }
    
    private void buildTreeNode(DefaultMutableTreeNode root, Node<NBTNode> value){
        NBTNode nbt=value.getValue();
        DefaultMutableTreeNode node=new TreeNodeWrapper(value,nbt.getName()+" ("+nbt.getType()+")");
        root.add(node);
        for(Node<NBTNode> child:value.childNodes()){
            this.buildTreeNode(node,child);
        }
        // Agregar el calor como nodo final usando el mismo Node<NBTNode>
        if(value.childNodes().isEmpty()){
            DefaultMutableTreeNode value_node=new TreeNodeWrapper(value,nbt.getValue());
            node.add(value_node);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nbt_popup = new javax.swing.JPopupMenu();
        nbt_popup_new = new javax.swing.JMenuItem();
        jScrollPane2 = new javax.swing.JScrollPane();
        level_tree = new javax.swing.JTree();
        name_instance_label = new javax.swing.JLabel();

        nbt_popup_new.setText("Nuevo");
        nbt_popup.add(nbt_popup_new);

        level_tree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                level_treeMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(level_tree);

        name_instance_label.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        name_instance_label.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(name_instance_label, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(name_instance_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void level_treeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_level_treeMousePressed
        TreePath path=this.level_tree.getPathForLocation(evt.getX(),evt.getY());
        if(path==null) return;
        DefaultMutableTreeNode root_node=(DefaultMutableTreeNode)path.getLastPathComponent();
        Node<NBTNode> node=(Node<NBTNode>)root_node.getUserObject();
        NBTNode value=node.getValue();
        this.level_tree.setSelectionPath(path);
        if(SwingUtilities.isRightMouseButton(evt)){
            this.nbt_popup.show(this.level_tree,evt.getX(),evt.getY());
            return;
        }
        if(!root_node.isLeaf()) return;
        String old_value=value.getValue().toString();
        String new_value=JOptionPane.showInputDialog(this.parent,"Nuevo valor: ",old_value);
        if(new_value==null) return;
        try{
            Tag<?> new_tag;
            switch(value.getType()){
                case BYTE: new_tag=new ByteTag(Byte.parseByte(new_value)); break;
                case SHORT: new_tag=new ShortTag(Short.parseShort(new_value)); break;
                case INT: new_tag=new IntTag(Integer.parseInt(new_value)); break;
                case LONG: new_tag=new LongTag(Long.parseLong(new_value)); break;
                case FLOAT: new_tag=new FloatTag(Float.parseFloat(new_value)); break;
                case DOUBLE: new_tag=new DoubleTag(Double.parseDouble(new_value)); break;
                case STRING: new_tag=new StringTag(new_value); break;
                default: AppLogger.alert("Es tipo de NBT ("+value.getPath()+") no se puede editar aquí").showMessage(); return;
            }
            value.setTag(new_tag);
            // Sincronizar cambios
            Node<NBTNode> parent=node.parent();
            if(parent==null){
                AppLogger.debug("El nodo no tiene padre "+node.toString());
                return;
            }
            NBTNode parent_value=parent.getValue();
            Tag<?> parent_tag=parent_value.getTag();
            if(parent_tag instanceof CompoundTag){
                CompoundTag compound=(CompoundTag)parent_tag;
                compound.put(value.getName(),new_tag);
            }else if(parent_tag instanceof ListTag){
                ListTag list=(ListTag)parent_tag;
                int index=Integer.parseInt(value.getName());
                list.set(index,new_tag);
            }else if(parent_tag instanceof ByteArrayTag){
                ByteArrayTag array=(ByteArrayTag)parent_tag;
                int index=Integer.parseInt(value.getName());
                array.getValue()[index]=((ByteTag)new_tag).asByte();
            }else if(parent_tag instanceof IntArrayTag){
                IntArrayTag array=(IntArrayTag)parent_tag;
                int index=Integer.parseInt(value.getName());
                array.getValue()[index]=((IntTag)new_tag).asInt();
            }else if(parent_tag instanceof LongArrayTag){
                LongArrayTag array=(LongArrayTag)parent_tag;
                int index=Integer.parseInt(value.getName());
                array.getValue()[index]=((LongTag)new_tag).asLong();
            }else{
                AppLogger.alert("No se puede modificar el padre tipo "+parent_tag.getClass().getSimpleName()).showMessage();
                return;
            }
            // Guardar cambios
            this.nbt.save();
            this.loadLevelDat();
            AppLogger.info("Se cambio el valor "+value.getName()+": de "+old_value+" a "+new_value);
        }catch(NumberFormatException ex){
            AppLogger.error("El valor no es válido para "+value.getTag().getClass()).exception(ex).showMessage();
        }catch(IOException ex){
            AppLogger.error("Error al guardar la datos \n"+ex.getMessage()).exception(ex).showMessage();
        }
    }//GEN-LAST:event_level_treeMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTree level_tree;
    private javax.swing.JLabel name_instance_label;
    private javax.swing.JPopupMenu nbt_popup;
    private javax.swing.JMenuItem nbt_popup_new;
    // End of variables declaration//GEN-END:variables
}
