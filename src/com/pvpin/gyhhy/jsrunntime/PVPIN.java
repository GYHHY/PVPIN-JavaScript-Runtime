package com.pvpin.gyhhy.jsrunntime;
import java.io.File;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.pvpin.mod.enhance.ParticleManager;
import com.pvpin.mod.music.Layer;
import com.pvpin.mod.music.NoteSound;
import com.pvpin.mod.music.Sequence;
import com.pvpin.mod.music.Tune;
import com.pvpin.mod.music.TunePlayer;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptException;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
/**
 *
 * @author Administrator
 */
public class PVPIN extends org.bukkit.plugin.java.JavaPlugin implements Listener{
    public static PVPIN plugin = null;
    private ScriptEngine engine;
    private ScriptEngineManager manager;
    public boolean canary = false;
    public boolean bukkit = true;
    private String NO_JAVASCRIPT_MESSAGE = "No JavaScript Engine";
    public final File RootDirectory;
    public final File DataDirectory;
    public ScriptObjectMirror eventRunner;
    public JavaScriptEventListener listener;
    public LocalApi getApi(){
        return LocalApi.api;
    }
    @Override
    public void onEnable(){
        eventRunner = null;
        Thread currentThread = Thread.currentThread();
        ClassLoader previousClassLoader = currentThread.getContextClassLoader();
        currentThread.setContextClassLoader(getClassLoader());
        plugin = this;
        manager = new ScriptEngineManager();
        engine = manager.getEngineByName("JavaScript");
        try{
        VaultAPI.onEnable();
        } catch(Exception ex){} catch(Error error){
            this.getLogger().info("No find Vault");
        }
        if (this.engine == null) {
            this.getLogger().severe(NO_JAVASCRIPT_MESSAGE);
        } else {
            try {
                Invocable inv = (Invocable) this.engine;
                this.engine.eval(new InputStreamReader(this.getResource("com/pvpin/gyhhy/jsrunntime/boot.js")));
                inv.invokeFunction("__scboot", this, engine,this.getClassLoader());
            } catch (Exception ex) {
                this.getLogger().log(Level.SEVERE, null, ex);
            }
        }
        currentThread.setContextClassLoader(previousClassLoader);
        JavaScriptEventListener l = JavaScriptEventListener.eventListener;
        this.getPluginLoader().createRegisteredListeners(l, this);
        this.listener = l;
        getLogger().info("PVPIN ok");
    }
    public String dataInit(String src){
        src = src.replaceAll("/", "\\").replaceAll("\\\\", File.separator);
        File fil = new File(DataDirectory,src);
        return fil.getAbsolutePath();
        //System.out.printf(src, args)
    };
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        return super.onCommand(sender, command, label, args);
    }
    @Override
    public void onDisable(){
        ArrayList<Disable> d = LocalApi.dis;
        for (Disable dis : d){
            if (dis.isEnable()){
                try {
                    dis.onDisable();
                } catch (Throwable e) {
                    getLogger().log(Level.WARNING, "Error in disabling {0}", dis);
                    e.printStackTrace(System.err);
                }
            }
        }
        d.clear();
    }
    public String toString(){
        return "PVPIN JavaScript Runntime.\nUpdate By GYHHY.";
    }
    public PVPIN(){
        //this.onEnable();
        this.RootDirectory = this.getDataFolder();
        File data = this.DataDirectory = new File(this.RootDirectory,"data");
        if(!data.isDirectory()){
            data.delete();
            data.mkdirs();
        }
    }
    public NashornScriptEngine getScriptEngine(){
        return (NashornScriptEngine) engine;
    }
    
    
    public void playParticle(String particleName, Location location) {
        ParticleManager.playParticle(particleName, location);
    }

    public NoteSound newNoteSound(Note n, Instrument i) {
        return new NoteSound(i, n);
    }

    public Layer newLayer(int length) {
        return new Layer(length);
    }

    public Sequence newSequence(int length) {
        return new Sequence(length);
    }

    public Tune newTune(Sequence s, int temp) {
        return new Tune(s, temp);
    }

    public TunePlayer newTunePlayer(Tune t, Player p) {
        return new TunePlayer(p, t);
    }
    public ClassLoader getLoader(){return getClassLoader();}
    public void runEvent(Event e,EventPriority level) {
        if (eventRunner != null){
            eventRunner.call(e, e,level);
        }
    }
}
