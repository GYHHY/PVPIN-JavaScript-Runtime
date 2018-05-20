/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvpin.gyhhy.jsrunntime;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.bukkit.event.*;
import com.google.gson.Gson;
import java.util.logging.Level;
import org.bukkit.entity.Player;
/**
 *
 * @author Administrator
 */
public class UpdateCheck implements Runnable,Listener{
    public void adminLogin(org.bukkit.event.player.PlayerJoinEvent e){
        Player player = e.getPlayer();
        if (mess != null){
            if (player.isOp()){
                player.sendMessage(mess);
            }
        }
    }
    public static class CheckData{
        public String alpha,version,url;
    }
    public static void check(){
        UpdateCheck c = new UpdateCheck();
        new Thread(c,"PVPIN JavaScript Runtime Update Checker").start();
    }
    String mess = null;
    boolean alpha = false;
    @Override
    public void run() {
        PVPIN plugin = PVPIN.plugin;
        String version = plugin.getVersion();
        if (!plugin.isAlpha()){
            plugin.getLogger().warning("This version is an internal test version!!");
            alpha = true;
        }
        
            try {
                HttpURLConnection connection = null;
                InputStream is = null;
                BufferedReader br = null;
                String result = "";// 返回结果字符串
                URL u = new URL("https://raw.githubusercontent.com/GYHHY/PVPIN-JavaScript-Runtime/master/update_check.json");
                connection = (HttpURLConnection) u.openConnection();
                connection.setRequestMethod("GET");
                // 设置连接主机服务器的超时时间：15000毫秒
                
                connection.setConnectTimeout(15000);
                // 设置读取远程返回的数据时间：60000毫秒
                connection.setReadTimeout(60000);
                // 发送请求
                connection.connect();
                // 通过connection连接，获取输入流
                if (connection.getResponseCode() == 200) {
                    is = connection.getInputStream();
                    // 封装输入流is，并指定字符集
                    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    // 存放数据
                    StringBuffer sbf = new StringBuffer();
                    String temp = null;
                    while ((temp = br.readLine()) != null) {
                        sbf.append(temp);
                        sbf.append("\n");
                    }
                    result = sbf.toString();
                }
                Gson g = new Gson();
                CheckData data = g.fromJson(result, CheckData.class);
                boolean newest = false;
                if (version.equals(data.alpha)){
                    plugin.getLogger().log(Level.INFO, "This version is the newest alpha version");
                    newest = true;
                }
                if (version.equals(data.version)){
                    newest = true;
                }
                if (!newest){
                    plugin.getLogger().log(Level.SEVERE, "Runtime v%s not the newest version",version);
                    plugin.getLogger().log(Level.SEVERE, "Please download form %s",data.url);
                    plugin.getLogger().log(Level.SEVERE, "https://github.com/GYHHY/PVPIN-JavaScript-Runtime");
                    mess = String.format("Runtime v%s not the newest version\nPlease download form %s\nhttps://github.com/GYHHY/PVPIN-JavaScript-Runtime",
                            version,data.url);
                }
            } catch (Throwable thr) {
                thr.printStackTrace();
                mess = thr.toString();
            }
        plugin.getPluginLoader().createRegisteredListeners(this, plugin);
    }
}
