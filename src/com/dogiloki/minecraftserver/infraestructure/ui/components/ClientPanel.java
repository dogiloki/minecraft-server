package com.dogiloki.minecraftserver.infraestructure.ui.components;

import com.dogiloki.minecraftserver.core.entities.NBTNode;
import com.dogiloki.minecraftserver.core.services.MinecraftClient;
import com.dogiloki.minecraftserver.core.services.NBTService;
import com.dogiloki.minecraftserver.core.services.World;
import com.dogiloki.multitaks.datastructure.tree.TreeIterator;
import com.dogiloki.multitaks.logger.AppLogger;
import java.awt.Frame;
import java.util.Iterator;
import net.querz.nbt.io.NBTSerializer;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;

/**
 *
 * @author _dogi
 */

public class ClientPanel extends javax.swing.JPanel {

    public Frame frame;
    public MinecraftClient client;
    public World world;
    
    public ClientPanel(Frame frame, MinecraftClient client, World world){
        initComponents();
        this.frame=frame;
        this.client=client;
        this.world=world;
        try{
            NBTService nbt=new NBTService(world.getLevelDatFile().getFile());
            nbt.tree().index().forEach((key,value)->{
                System.out.println(key+" -> "+value.getValue().toString());
            });
        }catch(Exception ex){
            AppLogger.error("Error al cargar").showMessage().exception(ex);
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        area = new javax.swing.JTextArea();

        area.setColumns(20);
        area.setRows(5);
        area.setText("ds");
        jScrollPane1.setViewportView(area);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(344, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(179, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea area;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
