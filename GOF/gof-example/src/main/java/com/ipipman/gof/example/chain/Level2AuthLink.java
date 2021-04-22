package com.ipipman.gof.example.chain;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by ipipman on 2021/4/22.
 *
 * @version V1.0
 * @Package com.ipipman.gof.example.chain
 * @Description: (用一句话描述该文件做什么)
 * @date 2021/4/22 10:15 上午
 */
public class Level2AuthLink extends AuthLink {

    private final Date beginDate = sdf.parse("2020-06-11 00:00:00");
    private final Date endDate = sdf.parse("2020-06-20 23:59:59");

    // 初始化父类
    public Level2AuthLink(String levelUserId, String levelUserName) throws ParseException {
        super(levelUserId, levelUserName);
    }

    @Override
    public AuthInfo doAuth(String uId, String orderId, Date authDate) {
        Date date = AuthService.queryAuthInfo(levelUserId, orderId);
        if (date == null) {
            return new AuthInfo("0001", "单号：", orderId, " 状态：待二级审批负责人 ", levelUserName);
        }
        AuthLink next = super.next();
        if (null == next) {
            return new AuthInfo("0000", "单号：", orderId, " 状态：二级审批完成负责人", " 时间：", sdf.format(date), " 审批人：", levelUserName);
        }
//        if (authDate.before(beginDate) || authDate.after(endDate)){
//            return new AuthInfo("0000", "单号：", orderId, " 状态：二级审批完成负责人", " 时间：", sdf.format(date), " 审批人：", levelUserName);
//        }
        return next.doAuth(uId, orderId, authDate);
    }
}
