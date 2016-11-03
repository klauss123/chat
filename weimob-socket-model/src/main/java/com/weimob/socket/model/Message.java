package com.weimob.socket.model;

import com.weimob.socket.model.base.ChatMsgIn;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dexin.su on 2016/10/11.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = -2907752327915529052L;
    private Integer status;
    private List<ChatMsgIn> chatMsgInList;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ChatMsgIn> getChatMsgInList() {
        return chatMsgInList;
    }

    public void setChatMsgInList(List<ChatMsgIn> chatMsgInList) {
        this.chatMsgInList = chatMsgInList;
    }
}
