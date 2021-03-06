package com.weimob.socket.model.base;

import java.io.Serializable;

/**
 * Created by dexin.su on 2016/10/11.
 */
public class Response<T> implements Serializable{
    private static final long serialVersionUID = 7149415550364551673L;
    private String returnCode;
    private String returnMsg;
    private String type;
    private Long requestId;
    private T data;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
