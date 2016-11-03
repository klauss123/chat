package com.weimob.socket.server.service;

import com.alibaba.fastjson.JSONObject;
import com.weimob.bs.utils.SerializeUtil;
import com.weimob.bs.utils.SpringBeanUtils;
import com.weimob.bs.utils.StringUtil;
import com.weimob.socket.model.Login;
import com.weimob.socket.model.Message;
import com.weimob.socket.model.base.Request;
import com.weimob.socket.model.base.Response;
import com.weimob.socket.server.utils.CommomUtil;
import com.weimob.socket.server.utils.Common;
import com.weimob.socket.server.utils.KeyUtil;
import com.weimob.socket.server.utils.LoggerUtil;
import io.vertx.core.buffer.Buffer;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

/**
 * Created by dexin.su on 2016/10/13.
 */
public enum RequestProcessor {
    msg {
        @Override
        public Response deal(Request request) {
            Request<Message> messageRequest = CommomUtil.getData(request, Message.class);
            if (messageRequest.getData().getStatus() == 1) {
                Buffer msg = Buffer.buffer().appendString(SerializeUtil.objectToJson(chatMsgService.insertMsg(messageRequest)));
                String user = request.getToUser();
                if (Common.instance.getUserToWebSocketMap().get(user) != null) {
                    Common.instance.getUserToWebSocketMap().get(user).write(msg);
                }
                if (Common.instance.getUserToNetSocketMap().get(user) != null) {
                    Common.instance.getUserToNetSocketMap().get(user).write(msg);
                }
            } else if (messageRequest.getData().getStatus() == 2) {
                chatMsgService.readMsg(messageRequest);
            }
            return this.response(request);
        }
    },
    login {
        @Override
        public Response deal(Request request) {
            Request<Login> loginRequest = CommomUtil.getData(request,Login.class);
            request.setData(loginRequest.getData());
            String user = loginRequest.getFromUser();
            LoggerUtil.commonInfo("user connect success ==> " + user);
            if (StringUtil.isNotEmpty(jedis.hget(KeyUtil.SOCKET_USER_KEY, user))) {
                if (Common.instance.getUserToWebSocketMap().get(user) != null) {
                    Common.instance.getUserToWebSocketMap().get(user).close();
                    Common.instance.getUserToWebSocketMap().remove(user);
                }
                if (Common.instance.getUserToNetSocketMap().get(user) != null) {
                    Common.instance.getUserToNetSocketMap().get(user).close();
                    Common.instance.getUserToNetSocketMap().remove(user);
                }
            }
            jedis.hset(KeyUtil.SOCKET_USER_KEY, user, "1");
            Response response = this.response(request);
            response.setData(chatMsgService.getUnreadMsg(loginRequest).getData());
            return response;
        }
    },
    heartbeat {
        @Override
        public Response deal(Request request) {
            return this.response(request);
        }
    };

    protected Jedis jedis;

    protected IChatMsgService chatMsgService;


    RequestProcessor() {
        this.jedis = SpringBeanUtils.getBean(Jedis.class);
        this.chatMsgService = SpringBeanUtils.getBean(IChatMsgService.class);
    }

    public abstract Response deal(Request request);

    protected Response response(Request request) {
        Response response = new Response();
        response.setReturnCode("000000");
        response.setReturnMsg("操作成功");
        response.setRequestId(request.getRequestId());
        return response;
    }
}
