/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvpin.gyhhy.jsrunntime;

import com.pvpin.mod.extend.TitleAPI;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.ScriptObject;
import org.bukkit.Server;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
/**
 * 内置API<br>
 * js变量: pvpin_api<br>
 * 属于实例对象
 * @author Administrator
 */
public class LocalApi {
    public static final ArrayList<Disable> dis = new ArrayList();
    /**
     * 创建一个Disable
     * @param func js函数
     * @return Disable
     */
    public Disable createDisable(ScriptObjectMirror func){
        return new Disable.JDisable(func);
    }
    /**
     * 注册在Disable时调用的命令
     * Register a disable
     * @param disable
     */
    public void registerDisable(Disable disable){
        disable.setEnable(true);
        dis.add(disable);
    }
    public String[] funcall(){
        return new String[]{
"§eString§r[] §3funcalls§r() §areturns all functron",
"§eString§r[] §3funcall§r() §areturns all functron with color",
"§eStaticClass§r §3c§r() §areturn class of StaticClass",
"§eScriptObjectMirror§r §3getOBC§r() §areturn §e\"§5Packages§r.§1org.bukkit.craftbukkit.§c[version]§e\"",
"§eScriptObjectMirror§r §3getNMS§r() §areturn §e\"§5Packages§r.§1net.minecraft.server.§c[version]§e\"",
"§eStaticClass§r §3getOBC§r(§eString§r) §aget class of OBC",
"§eStaticClass§r §3getNMS§r(§eString§r) §aget class of NMS",
"§eConsoleCommandSender§r §3getConsole§r() §areturn console.",
"§5void §3broadcastCommandMessage§r(§eCommandSender§r, §eString§r) §asame as \n    §1org.bukkit.command.Command.broadcastCommandMessage\n      §r(§eCommandSender§r, §eString§r)",
"§5void §3broadcastCommandMessage§r(§eCommandSender§r, §eString§r, §5boolean§r) §asame as\n    §1org.bukkit.command.Command.broadcastCommandMessage\n      §r(§eCommandSender§r, §eString§r, §5boolean§r)",
"§eStaticClass§r §3classToStatic§r(§eClass§r) §agetJsClass",
"§eClass§r §3staticToClass§r(§eStaticClass§r) §agetJavaClass",
"§eClass§r §3getClass§r(§eObject§r) §aget Object's class by Java",
"§eString§r §3getBukkitVersion§r() §areturn version",
"§eString§r §3getVersion§r() §asame as §3getBukkitVersion§r()",
"§5void §3sendJsonChat§r(§ePlayer§r, §eString§r) §asend json package."
        };
    }
    public String[] funcalls(){
        return new String[]{
            "String[] funcalls() returns all functron",
            "String[] funcall() returns all functron with color",
            "StaticClass c() return class of StaticClass",
            "ScriptObjectMirror getOBC() return \"Packages.org.bukkit.craftbukkit.[version]\"",
            "ScriptObjectMirror getNMS() return \"Packages.net.minecraft.server.[version]\"",
            "StaticClass getOBC(String) get class of OBC",
            "StaticClass getNMS(String) get class of NMS",
            "ConsoleCommandSender getConsole() return console.",
            "void broadcastCommandMessage(CommandSender, String) same as \n    org.bukkit.command.Command.broadcastCommandMessage\n      (CommandSender, String)",
            "void broadcastCommandMessage(CommandSender, String, boolean) same as\n    org.bukkit.command.Command.broadcastCommandMessage\n      (CommandSender, String, boolean)",
            "StaticClass classToStatic(Class) getJsClass",
            "Class staticToClass(StaticClass) getJavaClass",
            "Class getClass(Object) get Object's class by Java",
            "String getBukkitVersion() return version",
            "String getVersion() same as getBukkitVersion()",
            "void sendJsonChat(Player, String) send json package."
        };
    }
    public final static String version,
            obc,nms;
    public final static ScriptObjectMirror
            obc_,nms_;
    /**
     * 获取js class对象<br>
     * 与 <code>classToStatic(getClass())</code><br>
     * 相同
     * @return 
     */
    public StaticClass c(){
        return classToStatic(LocalApi.class);
    }
    static {
        PVPIN plugin = PVPIN.plugin;
        Server server = plugin.getServer();
        String name = server.getClass().getName();
        String[] classPath = name.split("\\.");
        version = classPath[3];
        obc = "org.bukkit.craftbukkit." + version + ".";
        nms = "net.minecraft.server."+version+".";
        ScriptObjectMirror s = null;
        try {
            s = (ScriptObjectMirror) plugin.getScriptEngine().eval("org.bukkit.craftbukkit."+version);
        } catch (ScriptException ex) {
            Logger.getLogger(LocalApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        obc_ = s;
        s = null;
        try {
            s = (ScriptObjectMirror) plugin.getScriptEngine().eval("Packages.net.minecraft.server."+version);
        } catch (ScriptException ex) {
            Logger.getLogger(LocalApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        nms_ = s;
    }
    /**
     * 获取js对象 org.bukkit.craftbukkit.[version]
     * @return 
     */
    public ScriptObjectMirror getOBC(){
        return obc_;
    }
    /**
     * 获取js对象 net.minecraft.server.[version]
     * @return 
     */
    public ScriptObjectMirror getNMS(){
        return nms_;
    }
    /**
     * 返回控制台
     * @return 
     */
    public ConsoleCommandSender getConsole(){
        ConsoleCommandSender console = PVPIN.plugin.getServer().getConsoleSender();
        return console;
    }
    /**
     * org.bukkit.command.Command.broadcastCommandMessage(source, message);
     * @param source
     * @param message
     */
    public void broadcastCommandMessage(CommandSender source, String message){
        org.bukkit.command.Command.broadcastCommandMessage(source, message);
    }
    /**
     * org.bukkit.command.Command.broadcastCommandMessage(source, message,toSource);
     * @param source
     * @param message
     * @param toSource
     */
    public void broadcastCommandMessage(CommandSender source, String message,boolean toSource){
        org.bukkit.command.Command.broadcastCommandMessage(source, message,toSource);
    }
    private LocalApi(){}
    /**
     * API对象
     */
    public static final LocalApi api = new LocalApi();
    /**
     * class对象转js class
     * @param c
     * @return 
     */
    public StaticClass classToStatic(Class c){
        return StaticClass.forClass(c);
    }
    /**
     * js class对象转java class
     * @param c
     * @return 
     */
    public Class staticToClass(StaticClass c){
        return c.getRepresentedClass();
    }
    /**
     * 获取一个对象的class
     * 找不到返回null
     * @param o
     * @return 
     */
    public Class getClass(Object o){
        Class c = null;
        if (o != null){
            c = o.getClass();
        }
        return c;
    }
    public String getBukkitVersion(){
        return version;
    }
    public String toString(){
        return "PVPIN API v1.5, saws the api use \".funcalls()\"";
    }
    /**
     * 向玩家发送一个json数据包
     * @param player
     * @param json
     * @throws java.lang.NoSuchMethodException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws java.lang.InstantiationException
     */
    public void sendJsonChat(Player player,String json)
            throws
            NoSuchMethodException,
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException,
            InstantiationException {
        Class c = getNMS("PacketPlayOutChat").getRepresentedClass();
        Class b = ((StaticClass)nms_.get("IChatBaseComponent$ChatSerializer")).getRepresentedClass();
        Class e = getNMS("IChatBaseComponent").getRepresentedClass();
        Constructor constructor = c.getConstructor(e);
        Method method = b.getMethod("a", String.class);
        Object invoke = method.invoke(null, json);
        Object newInstance = constructor.newInstance(invoke);
        TitleAPI.sendPacket(player,newInstance);
    }
    public String getVersion(){return getBukkitVersion();}
    /**
     * 返回 net.minecraft.server.[version].[name]
     * 使用 Class.forName
     * @param name
     * @return 
     */
    public StaticClass getNMS(String name){
        name = nms+name;
        Class c = null;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException ex) {}
        if (c == null){
            return null;
        }
        return classToStatic(c);
    }
    /**
     * 返回 org.bukkit.craftbukkit.[version].[name]
     * 使用 Class.forName
     * @param name
     * @return 
     */
    public StaticClass getOBC(String name){
        name = obc+name;
        Class c = null;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException ex) {}
        if (c == null){
            return null;
        }
        return classToStatic(c);
    }
    public PVPIN getPlugin(){return PVPIN.plugin;}
}
