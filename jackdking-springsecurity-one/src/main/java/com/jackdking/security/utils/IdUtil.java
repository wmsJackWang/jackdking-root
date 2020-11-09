package com.jackdking.security.utils;

public class IdUtil {

    /**
     * 毫秒内计数最大值
     */
    private static final long SEQUENCE_MAX = 1000L;
    /**
     * 毫秒内计数(0~999)
     */
    private static long sequenceCount = 0L;

    /**
     * 上次生成ID的时间截
     */
    private static long lastTimesTemporary = -1L;

    /**
     * @Author: Galen
     * @Description: 获取long类型的id主键
     * @Date: 2019/3/4-11:57
     * @Param: []
     * @return: long
     **/
    public static long generateNumberId() {
        long timestamp = timeGen();
        if (timestamp < lastTimesTemporary) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimesTemporary - timestamp));
        }
        //如果是同一时间生成的，则进行毫秒内计数
        if (lastTimesTemporary == timestamp) {
            //毫秒内序列溢出
            sequenceCount++;
            System.out.println(sequenceCount);
            if (sequenceCount >= SEQUENCE_MAX) {
                timestamp = tilNextMillis(timestamp);
            }
            //上次生成ID的时间截
            lastTimesTemporary = timestamp;
            return timestamp * SEQUENCE_MAX + sequenceCount; //时间戳是13位
        } else {
            //时间戳改变，毫秒内序列重置
            sequenceCount = 0L;
            //上次生成ID的时间截
            lastTimesTemporary = timestamp;
            return timestamp * SEQUENCE_MAX + (int) (Math.random() * 900 + 100); //时间戳是13位 + 3位随机数
        }
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private static long timeGen() {
        return System.currentTimeMillis();
    }
}
