package multitaks;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import multitaks.directory.Storage;
import multitaks.enums.DirectoryType;

/**
 *
 * @author dogi_
 */

public class Download extends javax.swing.JDialog implements Runnable{

    private String folder;
    private String name;
    private String url;
    private InputStream in;
    private OutputStream out;
    private boolean eliminar=false;
    
    public Download(java.awt.Frame parent, boolean modal, String folder, String name, String url, String msg) {
        super(parent,modal);
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        if((name==null || name.trim().equals(""))){
            String[] name_tmp=folder.split("/");
            name=name_tmp[name_tmp.length-1];
            name_tmp=Arrays.copyOf(name_tmp,name_tmp.length-1);
            folder=String.join("/", name_tmp);
        }
        Storage.exists(folder,DirectoryType.FOLDER,true);
        this.folder=folder+"/"+name;
        this.name=name;
        this.url=url;
        msg=(msg==null || msg.trim().equals(" "))?this.url:msg;
        this.setTitle(name);
        this.progreso_texto.setText(msg);
        new Thread(this).start();
    }
    
    @Override
    public void run(){
        File file=new File(this.folder);
        try{
            URLConnection con=new URL(this.url).openConnection();
            con.connect();
            this.in=new BufferedInputStream(con.getInputStream());
            this.out=new FileOutputStream(file,true);
            int tamano=con.getContentLength();
            this.progreso.setMaximum(100);
            int b=0;
            while(b!=-1){
                if(this.eliminar){
                    this.in.close();
                    this.out.close();
                    //Storage.deleteFile(this.folder);
                    b=-1;
                }else{
                    b=in.read();
                    if(b!=-1){
                        out.write(b);
                        this.progreso_texto.setText("Descargando: "+Function.convertSize(file.length())+" / "+Function.convertSize(tamano));
                        this.progreso.setValue((int)(file.length()*100)/tamano);
                    }
                }
            }
            in.close();
            out.close();
        }catch(Exception ex){
            this.progreso_texto.setText(ex.toString());
        }
        dispose();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progreso = new javax.swing.JProgressBar();
        progreso_texto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progreso, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                    .addComponent(progreso_texto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progreso_texto, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progreso, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.eliminar=true;
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Download dialog = new Download(new javax.swing.JFrame(),true,null,null,null,null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar progreso;
    private javax.swing.JLabel progreso_texto;
    // End of variables declaration//GEN-END:variables
}
