/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvpin.gyhhy.jsrunntime.debug_mode;

import com.pvpin.gyhhy.jsrunntime.PVPIN;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class DebugStartUp {
    public static void startUp(){
        PVPIN plugin = PVPIN.plugin;
        File f = new File(plugin.RootDirectory,"config.json");
        if (f.isFile()){
            try {
                FileReader reader = new FileReader(f);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DebugStartUp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
