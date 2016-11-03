package com.weimob.socket.server.service;

import com.alibaba.fastjson.JSONObject;
import com.weimob.bs.utils.SerializeUtil;
import com.weimob.bs.utils.SpringBeanUtils;
import com.weimob.bs.utils.StringUtil;
import com.weimob.socket.model.Login;
import com.weimob.socket.model.base.Request;
import com.weimob.socket.model.base.Response;
import com.weimob.socket.server.utils.Common;
import com.weimob.socket.server.utils.KeyUtil;
import com.weimob.socket.server.utils.LoggerUtil;
import io.vertx.core.buffer.Buffer;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dexin.su on 2016/10/13.
 */
public enum RequestProcessor {
    msg {
        @Override
        public Response deal(Request request) {
            Buffer msg = Buffer.buffer().appendString(SerializeUtil.objectToJson(request));
            String user = request.getToUser();
            if (Common.instance.getUserToWebSocketMap().get(user) != null) {
                Common.instance.getUserToWebSocketMap().get(user).write(msg);
            }
            if (Common.instance.getUserToNetSocketMap().get(user) != null) {
                Common.instance.getUserToNetSocketMap().get(user).write(msg);
            }
            return this.response(request);
        }
    },
    login {
        @Override
        public Response deal(Request request) {
            Login login = SerializeUtil.jsonToObject(((JSONObject) request.getData()).toJSONString(), Login.class);
            request.setData(login);
            String user = request.getFromUser();
            LoggerUtil.commonInfo("user connect success ==> " + user);
            long time = System.currentTimeMillis();
//            hbaseTemplate.put("test", time + user, "user", user, SerializeUtil.objectToJson(request).getBytes());
//            Map<String, Object> resultMap = hbaseTemplate.get("test", time + user, (result, i) -> {
//                List<Cell> ceList = result.listCells();
//                Map<String, Object> map = new HashMap<>();
//                if (ceList != null && ceList.size() > 0) {
//                    for (Cell cell : ceList) {
//                        map.put(Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength()) +
//                                        "_" + Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength()),
//                                Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
//                    }
//                }
//                return map;
//            });
//            LoggerUtil.commonInfo("hbase info ==> " + resultMap.toString());
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
            return this.response(request);
        }
    },
    heartbeat {
        @Override
        public Response deal(Request request) {
            return this.response(request);
        }
    };

    protected Jedis jedis;


    RequestProcessor() {
        this.jedis = SpringBeanUtils.getBean(Jedis.class);
    }

    public abstract Response deal(Request request);

    protected Response response(Request request) {
        Response response = new Response();
        response.setReturnCode("000000");
        response.setReturnMsg("操作成功");
        response.setRequest(request);
        return response;
    }
}
