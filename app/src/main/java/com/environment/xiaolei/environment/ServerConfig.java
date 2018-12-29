package com.environment.xiaolei.environment;

import android.content.Context;


/**
 * <pre>
 * 服务器访问配置类
 * author:lius
 * </pre>
 */
public class ServerConfig {

    public  enum Mode {
        UAT, TEST, RELEASE,DEV
    }

    private static Mode USE_SERVER_MODE;


    public static void init( Context context) {
        USE_SERVER_MODE = Enum.valueOf(Mode.class, DeviceUtil.getMetaValue(context, "server_mode"));
    }

    public static Mode getServerMode() {
        return USE_SERVER_MODE;
    }

    /**
     * 是否为生产环境 UAT环境
     */
    public static boolean isReleaseEM() {
        boolean isRelase = false;
        if(USE_SERVER_MODE == Mode.RELEASE || USE_SERVER_MODE == Mode.UAT){
            isRelase = true;
        }
        return  isRelase;
    }
}
