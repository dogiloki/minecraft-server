package com.dogiloki.minecraftserver.infraestructure.ui.components;

import com.dogiloki.minecraftserver.application.dao.Properties;
import com.dogiloki.minecraftserver.core.services.Instance;
import com.dogiloki.minecraftserver.core.services.MinecraftServer;
import com.dogiloki.minecraftserver.core.services.Mods;
import com.dogiloki.minecraftserver.core.services.PackagesMods;
import com.dogiloki.minecraftserver.core.services.World;
import com.dogiloki.minecraftserver.infraestructure.utils.ComboItemWrapper;
import com.dogiloki.multitaks.code.Code;
import com.dogiloki.multitaks.directory.DirectoryList;
import com.dogiloki.multitaks.directory.Storage;
import com.dogiloki.multitaks.directory.enums.DirectoryType;
import com.dogiloki.multitaks.download.DownloadDialog;
import com.dogiloki.multitaks.logger.AppLogger;
import com.dogiloki.multitaks.persistent.ExecutionObserver;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JOptionPane;

/**
 *
 * @author _dogi
 */

public final class ServerPanel extends javax.swing.JPanel{

    private final Frame parent;
    private final Instance ins;
    private final World world;
    private MinecraftServer minecraft_server;
    private Storage file_run;
    private Storage file_eula;
    private ExecutionObserver execution;
    
    public ServerPanel(Frame parent, Instance ins, World world){
        initComponents();
        this.parent=parent;
        this.minecraft_server=new MinecraftServer(ins.cfg);
        this.ins=ins;
        this.world=world;
        this.name_instance_label.setText(this.ins.cfg.name+" - "+this.world.getName());
        this.package_mods_label.setText(this.ins.cfg.mods);
        this.version_label.setText(this.ins.cfg.version);
        this.forge_version_label.setText(this.ins.cfg.forge_version);
        this.world_label.setText(this.world.toString());
        /*
        // Verificar si ya está iniciado el servidor
        RuntimeMXBean runtime=ManagementFactory.getRuntimeMXBean();
        String current_jvm=runtime.getName();
        for(VirtualMachineDescriptor vm:VirtualMachine.list()){
            if(vm.displayName().contains(this.minecraft_server.getNameServerJar())){
                if(!vm.id().equals(current_jvm.split("@")[0])){
                    this.start_server_btn.setEnabled(false);
                    break;
                }
            }
        }
        */
        this.canStart(this.isStarted());
    }
    
    public String getId(){
        return Code.encode64(this.ins.cfg.id);
    }
    
    public void canStart(boolean can){
        if(can){
            this.start_server_btn.setEnabled(false);
        }else{
            this.start_server_btn.setEnabled(true);
        }
    }
    
    public boolean isStarted(){
        try{
            Process process=Runtime.getRuntime().exec("cmd /c wmic process where \"name='cmd.exe'\" get CommandLine");
            BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while((line=reader.readLine())!=null){
                // Si la línea contine el título (id) ya esta abierto
                if(line.contains(this.getId())){
                    return true;
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            AppLogger.error(ex.getMessage());
        }
        return false;
    }
    
    // Verificar que la versión del servidor este descargada
    public void createMinecraftServer(){
        Storage manifest_version=MinecraftServer.VERSION_MANIFEST;
        Storage server_json=this.minecraft_server.server_json;
        Storage server_jar=this.minecraft_server.server_jar;
        if(!manifest_version.exists()){
            DownloadDialog download=new DownloadDialog(null,true,Properties.downloads.version_manifest_url,manifest_version.getSrc());
            download.setVisible(true);
        }
        if(!server_json.exists()){
            DownloadDialog download=new DownloadDialog(null,true,this.minecraft_server.getUrlMetaJson(),server_json.getSrc());
            download.setVisible(true);
        }
        if(!server_jar.exists()){
            DownloadDialog download=new DownloadDialog(null,true,this.minecraft_server.getUrlServerJar(),server_jar.getSrc());
            download.setVisible(true);
        }
        this.createFiles();
    }
    
    public void createFiles(){
        this.file_run=new Storage(this.ins.getSrc()+"/"+Properties.folders.instances_server+"/start.bat");
        this.file_eula=new Storage(this.file_run.getFolder()+"/eula.txt");
        String game_dir=this.file_run.getFile().getParentFile().getAbsolutePath();
        StringBuilder user_jvm_args=new StringBuilder();
        user_jvm_args.append("-Xms").append(this.ins.cfg.memory_min)
                .append(" -Xmx").append(this.ins.cfg.memory_max)
                .append(" -XX:+UseG1GC")
                .append(" -XX:+UseG1GC")
                .append(" -XX:+UnlockExperimentalVMOptions")
                .append(" -XX:G1NewSizePercent=20")
                .append(" -XX:G1MaxNewSizePercent=40")
                .append(" -XX:G1HeapRegionSize=8M")
                .append(" -XX:G1ReservePercent=20")
                .append(" -XX:MaxGCPauseMillis=50")
                .append(" -XX:+DisableExplicitGC");
        String command="\""+this.ins.cfg.java_path+"\""+
                " -jar "+user_jvm_args.toString()+
                " ../../../"+this.minecraft_server.server_jar.getSrc()+
                " nogui";
        if(this.ins.cfg.usedForge()){
            Storage file_args=new Storage(this.minecraft_server.forge_jar.getFolder()+"/user_jvm_args.txt",DirectoryType.FILE).notExists();
            file_args.write(user_jvm_args.toString());
            file_args.flush();
            file_args.close();
            command=new Storage(this.minecraft_server.forge_jar.getSrc()).read();
            command=command.replace("java -jar ","\""+this.ins.cfg.java_path+"\" -jar ");
            command=command.replace("java","\""+this.ins.cfg.java_path+"\"");
            if(command==null || command.equals("")){
                DirectoryList jar_files=new Storage(minecraft_server.forge_jar.getFolder()).listFiles();
                Path jar_file;
                while((jar_file=jar_files.next())!=null){
                    String name=jar_file.getFileName().toString();
                    if(!name.contains("universal") && !name.contains("forge")) continue;
                    command="\""+this.ins.cfg.java_path+"\""+
                        " -jar "+user_jvm_args.toString()+
                        " ../../../"+jar_file.toString()+
                        " nogui";
                }
            }else{
                AppLogger.logger().showMessage();
                AppLogger.debug("No compatible la versión de forge");
                return;
            }
            try{
                /*
                // Librerias para forge
                Storage.deleteFile(Storage.getDir()+"/"+this.ins.getSrc()+"/"+Properties.folders.instances_server+"/libraries");
                Files.createSymbolicLink(
                    Paths.get(Storage.getDir()+"/"+this.ins.getSrc()+"/"+Properties.folders.instances_server+"/libraries"),
                    Paths.get(Storage.getDir()+"/"+this.minecraft_server.forge_jar.getFolder()+"/libraries")
                );
                */
                // Mods
                Mods mods=new Mods(Properties.folders.instances_mods+"/"+this.ins.cfg.mods);
                if(mods!=null){
                    DirectoryList mods_files=mods.listFiles();
                    int index=0;
                    while(mods_files.hasNext()){
                        Storage existing=new Storage(mods_files.next().toString());
                        Storage link=new Storage(this.ins.getSrc()+"/"+Properties.folders.instances_server+"/"+Properties.folders.instances_mods+"/"+existing.getName());
                        if(!existing.exists()){
                            AppLogger.debug("No existe "+existing.getSrc());
                            continue;
                        }
                        if(index==0){
                            Storage.deleteFolder(link.getFolder());
                            Storage.createFolder(link.getFolder());
                        }
                        if(!existing.hashing().equals(link.hashing())){
                            Files.createLink(link.asPath(),existing.asPath());
                        }
                        index++;
                    }
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        // -Djava.awt.headless=true
        this.file_run.write(command);
        this.file_eula.write("#https://account.mojang.com/documents/minecraft_eula \neula=true");
        this.ins.server_properties.level_name=Properties.folders.instances_worlds+"/"+this.world.getName();
        this.ins.server_properties.save();
        this.file_eula.flush();
        this.file_run.flush();
        this.start();
    }
    
    public void start(){
        try{
            this.execution=new ExecutionObserver(
                    "cmd /c start cmd /k \"title "+this.getId()+" && call \""+this.file_run.getFile().getAbsolutePath()+"\"",
                    //this.minecraft_server.forge_jar.getFolder()
                    this.file_run.getFolder()
            );
            this.execution.start();
            this.canStart(true);
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error al iniciar servidor\n"+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        name_instance_label = new javax.swing.JLabel();
        start_server_btn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        version_label = new javax.swing.JLabel();
        forge_version_label = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        world_label = new javax.swing.JLabel();
        package_mods_label = new javax.swing.JLabel();

        name_instance_label.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        name_instance_label.setText("jLabel1");

        start_server_btn.setText("Iniciar servidor");
        start_server_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                start_server_btnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Paquete de Mods");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Versión Minecraft");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Versión Forge");

        version_label.setText("jLabel4");

        forge_version_label.setText("jLabel4");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Mundo");

        world_label.setText("jLabel4");

        package_mods_label.setText("jLabel4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(name_instance_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(start_server_btn))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(49, 49, 49)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(forge_version_label)
                                    .addComponent(world_label)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(version_label)
                                    .addComponent(package_mods_label))))
                        .addGap(0, 314, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(name_instance_label)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(package_mods_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(version_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(forge_version_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(world_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(start_server_btn)
                .addContainerGap(153, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void start_server_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_server_btnActionPerformed
        this.createMinecraftServer();
    }//GEN-LAST:event_start_server_btnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel forge_version_label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel name_instance_label;
    private javax.swing.JLabel package_mods_label;
    private javax.swing.JButton start_server_btn;
    private javax.swing.JLabel version_label;
    private javax.swing.JLabel world_label;
    // End of variables declaration//GEN-END:variables
}
