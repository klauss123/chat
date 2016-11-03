package com.weimob.socket.server.service.impl;

import com.weimob.bs.utils.JBeanUtils;
import com.weimob.socket.model.Login;
import com.weimob.socket.model.Message;
import com.weimob.socket.model.base.ChatMsgIn;
import com.weimob.socket.model.base.Request;
import com.weimob.socket.server.dao.model.ChatMsg;
import com.weimob.socket.server.dao.service.ChatMsgService;
import com.weimob.socket.server.service.IChatMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dexin.su on 2016/11/3.
 */
public class ChatMsgServiceImpl implements IChatMsgService {
    @Autowired
    private ChatMsgService chatMsgService;

    @Override
    public Integer readMsg(Request<Message> messageRequest) {
        List<Integer> idList = messageRequest.getData().getChatMsgInList().stream().map(ChatMsgIn::getId).collect(Collectors.toList());
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setStatus(2);
        Example example = new Example(ChatMsg.class);
        example.createCriteria().andEqualTo("aid", messageRequest.getAid()).andEqualTo("toUser", messageRequest.getToUser()).andIn("id", idList);
        chatMsgService.updateByExampleSelective(chatMsg, example);
        return 1;
    }

    @Override
    public Request<Message> insertMsg(Request<Message> messageRequest) {
        for (ChatMsgIn chatMsgIn : messageRequest.getData().getChatMsgInList()){
            ChatMsg chatMsg = new ChatMsg();
            JBeanUtils.copyProperties(chatMsgIn,chatMsg);
            chatMsgService.insertSelective(chatMsg);
            chatMsgIn.setId(chatMsg.getId());
        }
        return messageRequest;
    }

    @Override
    public Request<Message> getUnreadMsg(Request request) {
        Message message = new Message();
        message.setStatus(1);
        List<ChatMsgIn> chatMsgInList = new ArrayList<>();
        Example example = new Example(ChatMsg.class);
        example.createCriteria().andEqualTo("aid", request.getAid()).andEqualTo("toUser", request.getFromUser()).andEqualTo("status",1);
        List<ChatMsg> chatMsgList = chatMsgService.selectByExample(example);
        for (ChatMsg chatMsg:chatMsgList){
            ChatMsgIn chatMsgIn = new ChatMsgIn();
            JBeanUtils.copyProperties(chatMsg,chatMsgIn);
            chatMsgInList.add(chatMsgIn);
        }
        message.setChatMsgInList(chatMsgInList);
        request.setData(message);
        return request;
    }
}
