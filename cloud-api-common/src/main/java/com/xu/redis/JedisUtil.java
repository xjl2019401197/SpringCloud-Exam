package com.xu.redis;

import redis.clients.jedis.Jedis;

public class JedisUtil {

    public static Jedis getJedis() {
        Jedis jedis = new Jedis("124.223.182.14", 6399);
        jedis.auth("xjl2550908862xjl011025.");
        return jedis;
    }
}
