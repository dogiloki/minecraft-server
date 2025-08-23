package com.dogiloki.minecraftserver.infraestructure.utils;

import javax.swing.JOptionPane;

/**
 *
 * @author _dogi
 */

public class ConfirmHelper{
    
    public static void runWithConfirm(String message, Runnable accion){
        int opcion=JOptionPane.showConfirmDialog(null,message,"Confirmación",JOptionPane.YES_NO_OPTION);
        if(opcion==JOptionPane.YES_OPTION){
            accion.run();
        }
    }
    
}
