/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvpin.gyhhy.jsrunntime.authority;

/**
 *
 * @author Administrator
 */
public enum AuthorityDefaults {
    OP(0),
    NO_OP(1),
    NO_AnyOne(2),
    All(3);
    public static AuthorityDefaults getById(int id){
        AuthorityDefaults[] values = AuthorityDefaults.values();
        for (AuthorityDefaults a : values){
            if (a.id == id){
                return a;
            }
        }
        return null;
    }
    private AuthorityDefaults(int id){
        this.id = id;
    }
    private final int id;
    public int getId(){
        return id;
    }
}
