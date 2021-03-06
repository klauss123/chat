package com.weimob.socket.server.verticle;

import com.weimob.bs.utils.ReturnCodeManagerUtil;
import com.weimob.bs.utils.SerializeUtil;
import com.weimob.socket.model.base.Request;
import com.weimob.socket.model.base.Response;
import com.weimob.socket.server.service.IChatMsgService;
import com.weimob.socket.server.service.RequestProcessor;
import com.weimob.socket.server.service.SocketService;
import com.weimob.socket.server.utils.Common;
import com.weimob.socket.server.utils.LoggerUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dexin.su on 2016/8/18.
 */
@Component
public class NetSocketServerVerticle extends AbstractVerticle {
    @Autowired
    private SocketService socketService;
    @Autowired
    private IChatMsgService chatMsgService;

    @Override
    public void start() throws Exception {
        vertx.createNetServer().connectHandler(socket -> {
            socket.handler(buffer -> {
                Response response;
                try {
                    String input = buffer.toString();
                    LoggerUtil.commonInfo("get msg" + input);
                    Request request = SerializeUtil.jsonToObject(input, Request.class);
                    RequestProcessor requestProcessor = RequestProcessor.valueOf(request.getType());
                    response = requestProcessor.deal(request);
                    if ("000000".equals(response.getReturnCode())&&request.getType().equals(RequestProcessor.login.name())) {
                        Common.instance.getUserToNetSocketMap().put(request.getFromUser(),socket);
                    }
                } catch (Exception e) {
                    response = new Response();
                    response.setReturnCode(ReturnCodeManagerUtil.RuntimeErrorCode);
                    response.setReturnMsg(ExceptionUtils.getFullStackTrace(e));
                }
                Buffer msg = Buffer.buffer().appendString(SerializeUtil.objectToJson(response));
                socket.write(msg);
            });
            socket.closeHandler(event -> {
                String username = Common.instance.getNetSocketToUserMap().get(socket);
                socketService.onClose(username);
            });
        }).listen(9090);
        LoggerUtil.commonInfo("start NetSocketServerVerticle success");
    }
}
