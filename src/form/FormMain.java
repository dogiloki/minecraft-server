package form;



/**
 *
 * @author dogi_
 */

import dto.Instance;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.Storage;
import util.Config;
import util.Design;
import util.Function;

public class FormMain extends javax.swing.JFrame {
    
    private ArrayList<Instance> instances=new ArrayList<>();
    private Config cfg_global;

    public FormMain() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        this.getInstances();
    }
    
    public void getInstances(){
        this.cfg_global=new Config(this.getClass(),"minecraftServer.cfg",Config.JSON);
        String path_instances=this.cfg_global.getConfigJson("folders").getJson("instances").getValue("folder");
        String file_instance=this.cfg_global.getConfigJson("files").getValue("instance");
        String[] folders=Storage.listDirectory(path_instances,true,true,null);
        // Valores del panel en cada instancia
        int ancho=210, alto=80;
        int ancho_total=this.scroll_instances.getWidth()-25;
        int total_columnas=(int)(Math.floorDiv(ancho_total,ancho))-1;
        int x=0, y=0, count=0, filas=0;
        for(String folder:folders){
            folder=path_instances+"/"+folder;
            if(!Storage.exists(folder+"/"+file_instance)){
                continue;
            }
            // Obtener datos de la instancia
            Instance ins=new Instance(folder+"/"+file_instance);
            this.instances.add(ins);
            
            // Panel principal
            JPanel panel=new JPanel();
            panel.setLayout(null);
            panel.setBounds(x,y,ancho,alto);
            panel.setOpaque(false);
            panel.setToolTipText(ins.name+" ( "+ins.version+" ) ");
            panel.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
            
            // Calculo para posicionas componentes
            if(x==0){
                filas++;
            }
            if(count<total_columnas){
                x+=ancho;
                count++;
            }else{
                y+=alto;
                x=0;
                count=0;
            }
            
            // Compoente Versión
            JLabel label_vesion=new JLabel("Versión: "+ins.version);
            label_vesion.setBounds(0,0,ancho,alto/3);
            label_vesion.setFont(new Font("arial",Font.PLAIN,12));
            label_vesion.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            panel.add(label_vesion);
            
            // Compoente Nombre
            JLabel label_nombre=new JLabel(ins.name);
            label_nombre.setBounds(0,alto/3,ancho,(alto/3)*2);
            label_nombre.setFont(new Font("arial",Font.PLAIN,16));
            label_nombre.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            panel.add(label_nombre);
            
            // Diseño
            Design.margen(panel,10);
            
            // Agregar a la GUI
            this.panel_instances.add(panel);
        }
        // Adaptar panel de las instancias
        this.panel_instances.updateUI();
        this.panel_instances.setPreferredSize(Function.createDimencion(ancho_total,filas*alto));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll_instances = new javax.swing.JScrollPane();
        panel_instances = new javax.swing.JPanel();
        btn_crear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout panel_instancesLayout = new javax.swing.GroupLayout(panel_instances);
        panel_instances.setLayout(panel_instancesLayout);
        panel_instancesLayout.setHorizontalGroup(
            panel_instancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 915, Short.MAX_VALUE)
        );
        panel_instancesLayout.setVerticalGroup(
            panel_instancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 439, Short.MAX_VALUE)
        );

        scroll_instances.setViewportView(panel_instances);

        btn_crear.setText("Crear instancia");
        btn_crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_crear)
                    .addComponent(scroll_instances, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(410, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_crear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_instances, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearActionPerformed
        //new FormInstance(this,true,this.config_global).setVisible(true);
    }//GEN-LAST:event_btn_crearActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_crear;
    private javax.swing.JPanel panel_instances;
    private javax.swing.JScrollPane scroll_instances;
    // End of variables declaration//GEN-END:variables
}
