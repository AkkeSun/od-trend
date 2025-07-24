package com.odtrend.infrastructure.util;

public class SnowflakeGenerator {

    private static final long epoch = 1288834974657L; // 기준 시점 (2010-11-04 01:42:54 UTC)
    private static final long datacenterIdBits = 5L;
    private static final long workerIdBits = 5L;
    private static final long sequenceBits = 12L;
    private static final long maxDatacenterId = ~(-1L << datacenterIdBits); // 최대 31
    private static final long maxWorkerId = ~(-1L << workerIdBits);         // 최대 31
    private static final long sequenceMask = ~(-1L << sequenceBits);        // 최대 4095
    private static long datacenterId = 1;
    private static long workerId = 1;
    private static long sequence = 0L;
    private static long lastTimestamp = -1L;

    public static synchronized long nextId() {
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("Invalid Data Center ID");
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("Invalid Worker ID");
        }

        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                currentTimestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = currentTimestamp;
        return ((currentTimestamp - epoch) << 22) | (datacenterId << 17) | (workerId << 12)
            | sequence;
    }

    public static boolean isShard1(Long snowflakeId) {
        long timestamp = (snowflakeId >> 22) & 0x1FFFFFFFFFFL;
        long workerId = (snowflakeId >> 12) & 0x1F;
        return (timestamp ^ workerId) % 2 == 0;
    }

    private static long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
