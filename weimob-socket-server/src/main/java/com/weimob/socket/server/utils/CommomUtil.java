package com.weimob.socket.server.utils;

import com.alibaba.fastjson.JSONObject;
import com.weimob.bs.utils.SerializeUtil;
import com.weimob.socket.model.base.Request;

/**
 * Created by dexin.su on 2016/11/3.
 */
public class CommomUtil {
    public static <T> Request<T> getData(Request request, Class<T> clz) {
        T t = SerializeUtil.jsonToObject(((JSONObject) request.getData()).toJSONString(), clz);
        request.setData(t);
        return request;
    }
}
