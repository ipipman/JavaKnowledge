package com.ipipman.gof.example.proxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ipipman on 2021/4/14.
 *
 * @version V1.0
 * @Package com.ipipman.gof.example.proxy
 * @Description: (用一句话描述该文件做什么)
 * @date 2021/4/14 11:55 上午
 */
public class PersonServiceImpl implements IPersonService{
    @Override
    public void doing() {
        System.out.println("PersonServiceImpl doing on ...");

    }
}
