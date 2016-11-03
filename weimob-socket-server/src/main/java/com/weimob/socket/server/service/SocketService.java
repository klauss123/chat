package com.weimob.socket.server.service;

import com.weimob.bs.utils.StringUtil;
import com.weimob.socket.server.utils.Common;
import com.weimob.socket.server.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * Created by dexin.su on 2016/10/13.
 */
@Service
public class SocketService {
    @Autowired
    private Jedis jedis;

    public void onClose(String username) {
        if (StringUtil.isNotEmpty(username)) {
            Common.instance.getUserToWebSocketMap().remove(username);
            jedis.hdel(KeyUtil.SOCKET_USER_KEY, username);
        }
    }
}
