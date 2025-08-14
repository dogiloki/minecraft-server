package com.dogiloki.minecraftserver.infraestructure.ui.components;

import com.dogiloki.minecraftserver.application.dao.ServerProperties;
import com.dogiloki.minecraftserver.core.services.Instance;
import java.awt.event.ItemEvent;

/**
 *
 * @author dogi_
 */

public final class ServerPropertiesPanel extends javax.swing.JPanel{

    private Instance ins=null;
    
    public ServerPropertiesPanel(Instance ins){
        initComponents();
        this.ins=ins;
        this.getProperties();
    }
    
    public void getProperties(){
        ServerProperties proper=this.ins.server_properties;
        
        if(this.gamemode.getItemCount()<=0){
            this.gamemode.addItem(com.dogiloki.minecraftserver.application.dao.ServerProperties.SPECTATOR);
            this.gamemode.addItem(com.dogiloki.minecraftserver.application.dao.ServerProperties.ADVENTURE);
            this.gamemode.addItem(com.dogiloki.minecraftserver.application.dao.ServerProperties.CREATIVE);
            this.gamemode.addItem(com.dogiloki.minecraftserver.application.dao.ServerProperties.SURVIVAL);
        }
        if(this.difficulty.getItemCount()<=0){
            this.difficulty.addItem(com.dogiloki.minecraftserver.application.dao.ServerProperties.PEACEFUL);
            this.difficulty.addItem(com.dogiloki.minecraftserver.application.dao.ServerProperties.EASY);
            this.difficulty.addItem(com.dogiloki.minecraftserver.application.dao.ServerProperties.NORMAL);
            this.difficulty.addItem(com.dogiloki.minecraftserver.application.dao.ServerProperties.HARD);
        }
        
        this.gamemode.setSelectedItem(proper.gamemode);
        this.difficulty.setSelectedItem(proper.difficulty);
        this.max_players.setText(proper.max_players);
        this.white_list.setSelected(proper.white_list.equals("true"));
        this.online_mode.setSelected(proper.online_mode.equals("true"));
        this.pvp.setSelected(proper.pvp.equals("true"));
        this.enable_command_block.setSelected(proper.enable_command_block.equals("true"));
        this.allow_flight.setSelected(proper.allow_flight.equals("true"));
        this.spawn_animals.setSelected(proper.spawn_animals.equals("true"));
        this.spawn_mosters.setSelected(proper.spawn_mosters.equals("true"));
        this.spawn_npcs.setSelected(proper.spawn_npcs.equals("true"));
        this.allow_nether.setSelected(proper.allow_nether.equals("true"));
        this.force_gamemode.setSelected(proper.force_gamemode.equals("true"));
        this.spawn_protection.setText(proper.spawn_protection);
        this.require_resorce_pack.setSelected(proper.require_resorce_pack.equals("true"));
        this.resource_pack.setText(proper.resource_pack);
        this.resource_pack_promp.setText(proper.resource_pack_promp);
        this.port.setText(proper.port);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        require_resorce_pack = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        difficulty = new javax.swing.JComboBox<>();
        allow_nether = new javax.swing.JCheckBox();
        white_list = new javax.swing.JCheckBox();
        btn_reset_properties = new javax.swing.JButton();
        online_mode = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        spawn_protection = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        port = new javax.swing.JTextField();
        pvp = new javax.swing.JCheckBox();
        allow_flight = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        max_players = new javax.swing.JTextField();
        enable_command_block = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        resource_pack = new javax.swing.JTextField();
        spawn_animals = new javax.swing.JCheckBox();
        spawn_mosters = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        gamemode = new javax.swing.JComboBox<>();
        spawn_npcs = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        resource_pack_promp = new javax.swing.JTextField();
        force_gamemode = new javax.swing.JCheckBox();

        require_resorce_pack.setText("Paquete de recursos requerido");
        require_resorce_pack.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                require_resorce_packItemStateChanged(evt);
            }
        });

        jLabel3.setText("Dificultad");

        difficulty.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                difficultyItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(difficulty, 0, 150, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(difficulty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        allow_nether.setText("Inframundo");
        allow_nether.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                allow_netherItemStateChanged(evt);
            }
        });

        white_list.setText("Lista blanca");
        white_list.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                white_listItemStateChanged(evt);
            }
        });

        btn_reset_properties.setText("Restaurar configuración");
        btn_reset_properties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reset_propertiesActionPerformed(evt);
            }
        });

        online_mode.setText("Comprobar cuenta");
        online_mode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                online_modeItemStateChanged(evt);
            }
        });

        jLabel5.setText("Protección de spawn");

        spawn_protection.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                spawn_protectionKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(spawn_protection))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spawn_protection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setText("Puerto");

        port.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                portKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(port, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pvp.setText("PVP");
        pvp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pvpItemStateChanged(evt);
            }
        });

        allow_flight.setText("Volar");
        allow_flight.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                allow_flightItemStateChanged(evt);
            }
        });

        jLabel1.setText("Máximo de jugadores");

        max_players.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                max_playersKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(max_players))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(max_players, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        enable_command_block.setText("Bloques de comandos");
        enable_command_block.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                enable_command_blockItemStateChanged(evt);
            }
        });

        jLabel6.setText("Paquete de recursos");

        resource_pack.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                resource_packKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(resource_pack, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resource_pack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        spawn_animals.setText("Animales");
        spawn_animals.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                spawn_animalsItemStateChanged(evt);
            }
        });

        spawn_mosters.setText("Mostruos");
        spawn_mosters.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                spawn_mostersItemStateChanged(evt);
            }
        });

        jLabel2.setText("Modo de juego");

        gamemode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                gamemodeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gamemode, 0, 150, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gamemode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        spawn_npcs.setText("Aldeanos");
        spawn_npcs.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                spawn_npcsItemStateChanged(evt);
            }
        });

        jLabel7.setText("Mensaje de paquete de recursos");

        resource_pack_promp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                resource_pack_prompKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(resource_pack_promp, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resource_pack_promp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        force_gamemode.setText("Forzar modo de juego");
        force_gamemode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                force_gamemodeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(white_list)
                    .addComponent(force_gamemode)
                    .addComponent(pvp)
                    .addComponent(allow_flight)
                    .addComponent(online_mode))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(allow_nether)
                    .addComponent(spawn_npcs)
                    .addComponent(spawn_animals)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(spawn_mosters)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_reset_properties, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(enable_command_block)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(require_resorce_pack)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(white_list)
                    .addComponent(enable_command_block)
                    .addComponent(require_resorce_pack))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(force_gamemode)
                    .addComponent(allow_nether))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pvp)
                    .addComponent(spawn_npcs))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(allow_flight)
                    .addComponent(spawn_animals))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(online_mode)
                    .addComponent(spawn_mosters)
                    .addComponent(btn_reset_properties))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void require_resorce_packItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_require_resorce_packItemStateChanged
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.require_resorce_pack=this.require_resorce_pack.isSelected()?"true":"false";
        }
    }//GEN-LAST:event_require_resorce_packItemStateChanged

    private void difficultyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_difficultyItemStateChanged
        if(this.ins!=null && this.ins.server_properties!=null && evt.getStateChange()==ItemEvent.DESELECTED){
            this.ins.server_properties.difficulty=this.difficulty.getSelectedItem().toString();
        }
    }//GEN-LAST:event_difficultyItemStateChanged

    private void allow_netherItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_allow_netherItemStateChanged
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.allow_nether=this.allow_nether.isSelected()?"true":"false";
        }
    }//GEN-LAST:event_allow_netherItemStateChanged

    private void white_listItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_white_listItemStateChanged
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.white_list=this.white_list.isSelected()?"true":"false";
        }
    }//GEN-LAST:event_white_listItemStateChanged

    private void btn_reset_propertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reset_propertiesActionPerformed
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.reset();
            this.getProperties();
        }
    }//GEN-LAST:event_btn_reset_propertiesActionPerformed

    private void online_modeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_online_modeItemStateChanged
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.online_mode=this.online_mode.isSelected()?"true":"false";
        }
    }//GEN-LAST:event_online_modeItemStateChanged

    private void spawn_protectionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spawn_protectionKeyReleased
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.spawn_protection=this.spawn_protection.getText();
        }
    }//GEN-LAST:event_spawn_protectionKeyReleased

    private void portKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_portKeyReleased
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.port=this.port.getText();
        }
    }//GEN-LAST:event_portKeyReleased

    private void pvpItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pvpItemStateChanged
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.pvp=this.pvp.isSelected()?"true":"false";
        }
    }//GEN-LAST:event_pvpItemStateChanged

    private void allow_flightItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_allow_flightItemStateChanged
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.allow_flight=this.allow_flight.isSelected()?"true":"false";
        }
    }//GEN-LAST:event_allow_flightItemStateChanged

    private void max_playersKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_max_playersKeyReleased
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.max_players=this.max_players.getText();
        }
    }//GEN-LAST:event_max_playersKeyReleased

    private void enable_command_blockItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_enable_command_blockItemStateChanged
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.enable_command_block=this.enable_command_block.isSelected()?"true":"false";
        }
    }//GEN-LAST:event_enable_command_blockItemStateChanged

    private void resource_packKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resource_packKeyReleased
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.resource_pack=this.resource_pack.getText();
        }
    }//GEN-LAST:event_resource_packKeyReleased

    private void spawn_animalsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_spawn_animalsItemStateChanged
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.spawn_animals=this.spawn_animals.isSelected()?"true":"false";
        }
    }//GEN-LAST:event_spawn_animalsItemStateChanged

    private void spawn_mostersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_spawn_mostersItemStateChanged
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.spawn_mosters=this.spawn_mosters.isSelected()?"true":"false";
        }
    }//GEN-LAST:event_spawn_mostersItemStateChanged

    private void gamemodeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_gamemodeItemStateChanged
        if(this.ins!=null && this.ins.server_properties!=null && evt.getStateChange()==ItemEvent.DESELECTED){
            this.ins.server_properties.gamemode=this.gamemode.getSelectedItem().toString();
        }
    }//GEN-LAST:event_gamemodeItemStateChanged

    private void spawn_npcsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_spawn_npcsItemStateChanged
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.spawn_npcs=this.spawn_npcs.isSelected()?"true":"false";
        }
    }//GEN-LAST:event_spawn_npcsItemStateChanged

    private void resource_pack_prompKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resource_pack_prompKeyReleased
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.resource_pack_promp=this.resource_pack_promp.getText();
        }
    }//GEN-LAST:event_resource_pack_prompKeyReleased

    private void force_gamemodeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_force_gamemodeItemStateChanged
        if(this.ins!=null && this.ins.server_properties!=null){
            this.ins.server_properties.force_gamemode=this.force_gamemode.isSelected()?"true":"false";
        }
    }//GEN-LAST:event_force_gamemodeItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox allow_flight;
    private javax.swing.JCheckBox allow_nether;
    private javax.swing.JButton btn_reset_properties;
    private javax.swing.JComboBox<String> difficulty;
    private javax.swing.JCheckBox enable_command_block;
    private javax.swing.JCheckBox force_gamemode;
    private javax.swing.JComboBox<String> gamemode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField max_players;
    private javax.swing.JCheckBox online_mode;
    private javax.swing.JTextField port;
    private javax.swing.JCheckBox pvp;
    private javax.swing.JCheckBox require_resorce_pack;
    private javax.swing.JTextField resource_pack;
    private javax.swing.JTextField resource_pack_promp;
    private javax.swing.JCheckBox spawn_animals;
    private javax.swing.JCheckBox spawn_mosters;
    private javax.swing.JCheckBox spawn_npcs;
    private javax.swing.JTextField spawn_protection;
    private javax.swing.JCheckBox white_list;
    // End of variables declaration//GEN-END:variables
}
