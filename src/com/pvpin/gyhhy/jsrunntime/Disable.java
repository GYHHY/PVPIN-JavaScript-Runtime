/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvpin.gyhhy.jsrunntime;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * Disable
 * 注：此触发时间为插件disable的时候
 * 即Java插件执行了onDisable的时候开始执行
 * @author Administrator
 */
public abstract class Disable {
/**
 * JDisable
 * 注：此触发时间为插件disable的时候
 * 即Java插件执行了onDisable的时候开始执行
 */
    public static class JDisable extends Disable{
        private final ScriptObjectMirror s;
        /**
         * 创建一个JDisabe对象
         * 执行使用函数
         * @param s 函数
         */
        public JDisable(ScriptObjectMirror s){
            this.s = s;
            if (s == null || !s.isFunction()){
                throw new java.lang.ClassCastException("object not a js function.");
            }
        }
        @Override
        public void onDisable() {
            s.call(this);
        }
        
    }
    public Disable() {
        this.enable = true;
    }
    public final void setEnable(boolean e){enable = e;}
    /**
     * 是否启动
     * @return 
     */
    public final boolean isEnable(){return enable;}
    /**
     * 在Desable时执行
     */
    public abstract void onDisable();
    private boolean enable;
}
