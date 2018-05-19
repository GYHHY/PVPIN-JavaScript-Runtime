/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvpin.gyhhy.jsrunntime;
import org.bukkit.event.*;
/**
 * Java 监听处理器
 * @author Administrator
 */
public class JavaScriptEventListener implements Listener{
    /**
     * 获取已经实例化的监听
     */
    public static JavaScriptEventListener eventListener = new JavaScriptEventListener();
    private JavaScriptEventListener(){}
    @EventHandler(priority=EventPriority.HIGHEST)
    public void onEvent(Event e){
        PVPIN.plugin.runEvent(e,EventPriority.HIGHEST);
    }
    @EventHandler(priority=EventPriority.HIGH)
    public void onEventHigh(Event e){
        PVPIN.plugin.runEvent(e,EventPriority.HIGH);
    }
    @EventHandler(priority=EventPriority.LOW)
    public void onEventLow(Event e){
        PVPIN.plugin.runEvent(e,EventPriority.LOW);
    }
    @EventHandler(priority=EventPriority.LOWEST)
    public void onEventLowest(Event e){
        PVPIN.plugin.runEvent(e,EventPriority.LOWEST);
    }
    @EventHandler(priority=EventPriority.MONITOR)
    public void onEventMonitor(Event e){
        PVPIN.plugin.runEvent(e,EventPriority.MONITOR);
    }
    @EventHandler(priority=EventPriority.NORMAL)
    public void onEventNormal(Event e){
        PVPIN.plugin.runEvent(e,EventPriority.NORMAL);
    }
}
