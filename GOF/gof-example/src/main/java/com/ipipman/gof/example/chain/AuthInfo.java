package com.ipipman.gof.example.chain;

/**
 * Created by ipipman on 2021/4/22.
 *
 * @version V1.0
 * @Package com.ipipman.gof.example.chain
 * @Description: (用一句话描述该文件做什么)
 * @date 2021/4/22 10:11 上午
 */
public class AuthInfo {

    private String code;
    private String info = "";

    public AuthInfo(String code, String... infos) {
        this.code = code;
        for (String str : infos) {
            this.info = this.info.concat(str);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
