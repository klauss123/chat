package com.weimob.socket.server.service;

import com.weimob.socket.model.Login;
import com.weimob.socket.model.Message;
import com.weimob.socket.model.base.Request;

/**
 * Created by dexin.su on 2016/11/3.
 */
public interface IChatMsgService {
    public Integer readMsg(Request<Message> messageRequest);
    public Request<Message> insertMsg(Request<Message> messageRequest);
    public Request<Message> getUnreadMsg(Request<Login> loginRequest);
}
