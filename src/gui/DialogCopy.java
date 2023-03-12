package gui;

/**
 *
 * @author dogi_
 */

import java.io.File;
import javax.swing.JOptionPane;
import util.Storage;

public class DialogCopy extends javax.swing.JDialog implements Runnable{

    private String path_old="";
    private String path_new="";
    private String message="";
    private boolean destroy=false;
    
    public DialogCopy(java.awt.Frame parent, boolean modal, String path_old, String path_new) {
        super(parent, modal);
        initComponents();
        this.path_old=path_old;
        this.path_new=path_new;
        this.message="Exito!!";
        this.text_path.setText("Copiando "+path_old+" a "+path_new);
        new Thread(this).start();
    }
    
    public DialogCopy(java.awt.Frame parent, boolean modal, String path_old, String path_new, String message) {
        super(parent, modal);
        initComponents();
        this.path_old=path_old;
        this.path_new=path_new;
        this.message=message;
        this.text_path.setText("Copiando: "+path_old+"\nDestino: "+path_new);
        new Thread(this).start();
    }
    
    @Override
    public void run(){
        try{
            Storage.copyDirectory(this.path_old,this.path_new);
            JOptionPane.showMessageDialog(null,this.message,"Exito",JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        text_info = new javax.swing.JTextArea();
        text_path_old = new javax.swing.JLabel();
        text_path_new = new javax.swing.JLabel();
        text_path = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        text_info.setColumns(20);
        text_info.setRows(5);
        jScrollPane1.setViewportView(text_info);

        text_path_old.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        text_path_old.setText("jLabel1");

        text_path_new.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        text_path_new.setText("jLabel1");

        text_path.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        text_path.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                    .addComponent(text_path_old, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(text_path_new, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(text_path, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(text_path)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_path_old)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_path_new)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.destroy=true;
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogCopy dialog = new DialogCopy(new javax.swing.JFrame(), true, "", "", "");
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea text_info;
    public javax.swing.JLabel text_path;
    private javax.swing.JLabel text_path_new;
    private javax.swing.JLabel text_path_old;
    // End of variables declaration//GEN-END:variables
}
