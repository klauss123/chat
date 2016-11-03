package com.weimob.socket.server.task;

import com.weimob.bs.utils.SpringBeanUtils;
import com.weimob.bs.utils.SpringPropertyConfigurerUtil;
import com.weimob.socket.server.utils.KeyUtil;
import com.weimob.socket.server.utils.LoggerUtil;
import com.weimob.socket.server.verticle.NetSocketServerVerticle;
import com.weimob.socket.server.verticle.WebSocketServerVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Date;

/**
 * Created by dexin.su on 2016/10/11.
 */
@Component
public class StartRunner implements CommandLineRunner {

    @Autowired
    private Jedis jedis;

    @Override
    public void run(String... strings) throws Exception {
        final Vertx vertx = Vertx.vertx();
        DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setWorker(true);
        vertx.deployVerticle(SpringBeanUtils.getBean(WebSocketServerVerticle.class), deploymentOptions);
        vertx.deployVerticle(SpringBeanUtils.getBean(NetSocketServerVerticle.class), deploymentOptions);
        if (!"pl".equals(SpringPropertyConfigurerUtil.getContextProperty("status"))) {
            LoggerUtil.commonInfo("StartTask---->" + new Date());
            jedis.del(KeyUtil.SOCKET_USER_KEY);
        }
    }
}
