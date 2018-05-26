/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvpin.gyhhy.jsrunntime.authority;
import com.pvpin.gyhhy.jsrunntime.PVPIN;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.entity.*;
import com.google.gson.*;
import com.pvpin.gyhhy.jsrunntime.Disable;
import com.pvpin.gyhhy.jsrunntime.LocalApi;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Administrator
 */
public class Authority {
    public static ArrayList<Authority> saves = new ArrayList();
    public static Authority loadAuthority(String name) throws FileNotFoundException{
        File data = new File(dataSave,name + ".json");
        if (data.isFile()){
            return g.fromJson(new FileReader(data), Authority.class);
        }
        return null;
    }
    
    public static final File dataSave;
    public static final Gson g = new Gson();
    private int default_id = -1;
    static {
        dataSave = new File(PVPIN.plugin.RootDirectory,"Authority");
        dataSave.mkdirs();
        LocalApi.api.registerDisable(new Disable() {
            @Override
            public void onDisable() {
                for (Authority a : saves){try {
                    a.save();
                    } catch (IOException ex) {
                        Logger.getLogger(Authority.class.getName()).log(Level.SEVERE, null, ex);
                    }
}
            }
        });
    }
    public String getName(){return name;}
    private String name;
    private HashMap<UUID,Boolean> has;
    private Authority(){
        this(null,null);
    }
    public void saveOnDisable(){
        saves.add(this);
    }
    public Authority(String name,AuthorityDefaults d){
        this.name = name;
        this.has = new HashMap();
        if (d != null){
            setDefault(d);
        }
    }
    public void setDefault(AuthorityDefaults d){
        if (d == null){
            this.default_id = -1;
            return;
        }
        this.default_id = d.getId();
    }
    public AuthorityDefaults getDefault(){
        return AuthorityDefaults.getById(default_id);
    }
    public void add(Player player){
        UUID id = player.getUniqueId();
        has.put(id, Boolean.TRUE);
    }
    public boolean has(Player player){
        UUID pl = player.getUniqueId();
        if (has.containsKey(pl)){
            return has.get(pl);
        } else {
            return defaults(player);
        }
    }
    public void save() throws IOException{
        File data = new File(dataSave,getName() + ".json");
        data.createNewFile();
        PrintStream pr = new PrintStream(new FileOutputStream(data));
        String toJson = g.toJson(this);
        pr.print(toJson);
        pr.close();
    }

    private boolean defaults(Player pl) {
        AuthorityDefaults d = getDefault();
        if (d != null){
            switch(d){
                case OP:{
                    return pl.isOp();
                }
                case NO_OP:{
                    return !pl.isOp();
                }
                case NO_AnyOne:{
                    return false;
                }
                case All:{
                    return true;
                }
            }
        }
        return false;
    }
}
