package com.weimob.socket.client.verticle;

import com.weimob.bs.utils.SerializeUtil;
import com.weimob.socket.model.Login;
import com.weimob.socket.model.base.Request;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by dexin.su on 2016/8/18.
 */
@Component
public class WebSocketClientVerticle extends AbstractVerticle {
    private final Logger logger = Logger.getLogger(WebSocketClientVerticle.class);

    @Override
    public void start() throws Exception {
        vertx.createHttpClient().websocket(9080, "0.0.0.0", "/chat", websocket -> {
            logger.info("Connected!");
            websocket.handler(buffer -> {
                logger.info("1 getMessage ==> " + buffer.toString());
            });
            Request<Login> request = new Request();
            request.setFromUser("1");
            request.setToUser("2");
            request.setStatus("1");
            request.setType("login");
            Login login = new Login();
            login.setUsername("1");
            login.setPassword("123456");
            request.setData(login);
            Buffer buffer = Buffer.buffer().appendString(SerializeUtil.objectToJson(request));
            websocket.write(buffer);
        });

        vertx.createHttpClient().websocket(9080, "0.0.0.0", "/chat/2/123456", websocket -> {
            logger.info("Connected!");
            websocket.handler(buffer -> {
                logger.info("2 getMessage ==> " + buffer.toString());
            });
            Request request = new Request();
            request.setFromUser("2");
            request.setToUser("1");
            request.setStatus("1");
            request.setType("login");
            Login login = new Login();
            login.setUsername("1");
            login.setPassword("123456");
            request.setData(login);
            Buffer buffer = Buffer.buffer().appendString(SerializeUtil.objectToJson(request));
            websocket.write(buffer);
        });
    }
}
