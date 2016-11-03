package com.weimob.socket.model;

import java.io.Serializable;

/**
 * Created by dexin.su on 2016/10/11.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = -2907752327915529052L;
    private Integer type;
    private String msg;
    private String msgId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
