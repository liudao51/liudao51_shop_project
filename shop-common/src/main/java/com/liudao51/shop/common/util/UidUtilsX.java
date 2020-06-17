package com.liudao51.shop.common.util;

/**
 * 分布式id生成工具类
 */
public class UidUtilsX {

    private final static long WORKER_ID = 1;  //工作机器ID(0~31)
    private final static long DATACENTER_ID = 1;  //数据中心ID(0~31)

    private static TwitterUidUtilsX uidGenerator;

    static {
        uidGenerator = new TwitterUidUtilsX(WORKER_ID, DATACENTER_ID);
    }

    /**
     * 分布式uuid产生方法
     *
     * @return long
     */
    public static long getUid() {
        return uidGenerator.nextId();
    }
}
