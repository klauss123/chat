package com.weimob.socket.model.base;

import java.io.Serializable;

/**
 * Created by dexin.su on 2016/10/11.
 */
public class Request<T> implements Serializable {
    private static final long serialVersionUID = 6051329006246828105L;
    private String fromUser;
    private String status;
    private String toUser;
    private String type;
    private T data;

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
