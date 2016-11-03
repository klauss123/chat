package com.weimob.socket.client.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import org.springframework.stereotype.Component;

/**
 * Created by dexin.su on 2016/8/18.
 */
@Component
public class NetSocketClientVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        vertx.createNetClient().connect(9090, "0.0.0.0", res -> {
            System.out.println("Connected!");
            Buffer buffer = Buffer.buffer().appendString("woca");
            res.result().write(buffer);
        });
    }
}
