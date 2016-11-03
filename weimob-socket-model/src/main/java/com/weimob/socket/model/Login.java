package com.weimob.socket.model;

import java.io.Serializable;

/**
 * Created by dexin.su on 2016/10/13.
 */
public class Login implements Serializable{
    private static final long serialVersionUID = -3247426730139689485L;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
