package com.weimob.socket.server.utils;

import com.weimob.bs.utils.LogUtil;
import org.apache.log4j.Logger;

/**
 * Created by dexin.su on 2016/10/11.
 */
public class LoggerUtil {

    private static final String chatLogKey = "chatLog";
    private static final String commonLogKey = "commonLog";

    public static final Logger chatLogger = Logger.getLogger(chatLogKey);
    public static final Logger commonLogloggger = Logger.getLogger(commonLogKey);

    public static void chatError(String msg, Throwable throwable, boolean showOnline) {
        LogUtil.error(chatLogger, msg, throwable, showOnline);
    }

    public static void chatInfo(String msg) {
        LogUtil.info(chatLogger, msg, true);
    }

    public static void chatError(String msg, Throwable throwable) {
        LogUtil.error(chatLogger, msg, throwable, true);
    }

    public static void commonInfo(String msg, boolean showOnline) {
        LogUtil.info(commonLogloggger, msg, showOnline);
    }

    public static void commonError(String msg, Throwable throwable, boolean showOnline) {
        LogUtil.error(commonLogloggger, msg, throwable, showOnline);
    }

    public static void commonInfo(String msg) {
        LogUtil.info(commonLogloggger, msg, true);
    }

    public static void commonError(String msg, Throwable throwable) {
        LogUtil.error(commonLogloggger, msg, throwable, true);
    }
}
