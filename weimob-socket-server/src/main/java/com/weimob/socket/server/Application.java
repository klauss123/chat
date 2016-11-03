package com.weimob.socket.server;

import com.weimob.bs.utils.SpringBeanUtils;
import com.weimob.bs.utils.SpringPropertyConfigurerUtil;
import com.weimob.socket.server.utils.KeyUtil;
import com.weimob.socket.server.utils.LoggerUtil;
import com.weimob.socket.server.verticle.NetSocketServerVerticle;
import com.weimob.socket.server.verticle.WebSocketServerVerticle;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.Jedis;

import java.util.Date;

/**
 * Created by Adam on 2016/5/24.
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ImportResource({"classpath*:META-INF/spring/applicationContext-*.xml"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
