    package com.xu.redis;

    import org.redisson.Redisson;
    import org.redisson.api.RBloomFilter;
    import org.redisson.api.RedissonClient;
    import org.redisson.client.RedisClient;
    import org.redisson.config.Config;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    import java.io.IOException;
    @Configuration
    public class MyRedissonConfig {



        // redission通过redissonClient对象使用 // 如果是多个redis集群，可以配置
        @Bean(destroyMethod = "shutdown")
        public RedissonClient redisson() {
            //1、创建配置
            Config config = new Config();
            // 创建单例模式的配置
            config.useSingleServer().setAddress("redis://" + "124.223.182.14" + ":6399").setPassword("xjl2550908862xjl011025.");//rediss代表安全模式
            //2、根据Config创建出RedissonClient示例
            RedissonClient redissonClient = Redisson.create(config);
            System.out.println(redissonClient+"client88888888888888888");
            return Redisson.create(config);
        }
//        @Bean
//        public RBloomFilter<Integer> bloomFilter(){
//            //1、创建配置
//            Config config = new Config();
//            // 创建单例模式的配置
//            config.useSingleServer().setAddress("redis://" + "124.223.182.14" + ":6399").setPassword("xjl2550908862xjl011025.");//rediss代表安全模式
//            //2、根据Config创建出RedissonClient示例
//            RedissonClient redissonClient = Redisson.create(config);
//            RBloomFilter<Integer> bloomFilter = redissonClient.getBloomFilter("list");
//            bloomFilter.tryInit(1000,0.1);
//            return bloomFilter;
//        }

    }
