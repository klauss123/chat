package com.weimob.socket.server.utils;

import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.net.NetSocket;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dexin.su on 2016/10/11.
 */
public enum Common {
    instance;

    Common() {
        this.userToWebSocketMap = new ConcurrentHashMap<>();
        this.userToNetSocketMap = new ConcurrentHashMap<>();
        this.webSocketToUserMap = new ConcurrentHashMap<>();
        this.netSocketToUserMap = new ConcurrentHashMap<>();
    }

    private ConcurrentHashMap<String, ServerWebSocket> userToWebSocketMap;

    private ConcurrentHashMap<String, NetSocket> userToNetSocketMap;

    private ConcurrentHashMap<ServerWebSocket, String> webSocketToUserMap;

    private ConcurrentHashMap<NetSocket, String> netSocketToUserMap;

    public ConcurrentHashMap<String, ServerWebSocket> getUserToWebSocketMap() {
        return userToWebSocketMap;
    }

    public ConcurrentHashMap<String, NetSocket> getUserToNetSocketMap() {
        return userToNetSocketMap;
    }

    public ConcurrentHashMap<ServerWebSocket, String> getWebSocketToUserMap() {
        return webSocketToUserMap;
    }

    public ConcurrentHashMap<NetSocket, String> getNetSocketToUserMap() {
        return netSocketToUserMap;
    }
}
