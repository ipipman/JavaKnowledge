package com.ipipman.gof.example.chain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ipipman on 2021/4/22.
 *
 * @version V1.0
 * @Package com.ipipman.gof.example.chain
 * @Description: (用一句话描述该文件做什么)
 * @date 2021/4/22 10:03 上午
 */
public abstract class AuthLink {

    // 时间格式化
    protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 级别人员ID
    protected String levelUserId;
    // 级别人员姓名
    protected String levelUserName;
    // 下一个责任链
    private AuthLink next;

    // 构造方法
    public AuthLink(String levelUserId, String levelUserName) {
        this.levelUserId = levelUserId;
        this.levelUserName = levelUserName;
    }

    // 返回下一个责任链条
    public AuthLink next() {
        return this.next;
    }

    // 加入下一个责任链
    public AuthLink appendNext(AuthLink next) {
        this.next = next;
        return this;
    }

    // 审核
    public abstract AuthInfo doAuth(String uId, String orderId, Date authDate);

}
