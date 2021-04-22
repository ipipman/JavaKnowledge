package com.ipipman.gof.example.chain;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ipipman on 2021/4/22.
 *
 * @version V1.0
 * @Package com.ipipman.gof.example.chain
 * @Description: (用一句话描述该文件做什么)
 * @date 2021/4/22 10:18 上午
 */
public class AuthService {

    private static final Map<String, Date> authMap = new ConcurrentHashMap<>();

    public static Date queryAuthInfo(String uId, String orderId) {
        return authMap.get(uId.concat(orderId));
    }

    public static void auth(String uId, String orderId) {
        authMap.put(uId.concat(orderId), new Date());
    }
}
