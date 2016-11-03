package com.weimob.socket.client;

import com.weimob.bs.utils.SpringBeanUtils;
import com.weimob.socket.client.verticle.NetSocketClientVerticle;
import com.weimob.socket.client.verticle.WebSocketClientVerticle;
import io.vertx.core.Vertx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by Adam on 2016/5/24.
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ImportResource({"classpath*:META-INF/spring/applicationContext-*.xml"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(SpringBeanUtils.getBean(WebSocketClientVerticle.class));
        vertx.deployVerticle(SpringBeanUtils.getBean(NetSocketClientVerticle.class));
    }
}
