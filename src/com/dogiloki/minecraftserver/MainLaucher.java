package com.dogiloki.minecraftserver;

import com.dogiloki.minecraftserver.application.dao.Properties;
import com.dogiloki.minecraftserver.infraestructure.ui.MainForm;
import com.dogiloki.multitaks.directory.ConfigFile;
import com.dogiloki.multitaks.logger.AppLogger;
import com.dogiloki.multitaks.logger.LogEntry;
import com.dogiloki.multitaks.logger.contracts.LogListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author _dogi
 */

public class MainLaucher{
    
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Runnable(){
            public void run() {
                ConfigFile.load(Properties.class);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                MainForm main_form=new MainForm();
                main_form.setVisible(true);
                AppLogger.logger().modeDebug(Properties.mode_debug);
                AppLogger.logger().getLog().addListener(new LogListener(){
                    @Override
                    public void onLogAdded(LogEntry entry){
                        main_form.getLogInstancePane().insert(entry);
                    }
                });
                main_form.run();
            }
        });
    }
}