package com.andy.home.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.andy.home.enums.DataType;

public class SnowFlakeUtil {

    private long machineId;
    private long dataCenterId;

    public static long genaraterId(){
        Snowflake snowflake = IdUtil.getSnowflake();
        return snowflake.nextId();
    }

    public static void main(String[] args) {
       for (int i = 1;i<=37;i++) {
           Snowflake snowflake = IdUtil.getSnowflake();
           System.out.println(i+"===="+snowflake.nextId());
       }
    }
}
