package com.xu.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.hash.BloomFilter;
import com.xu.mapper.ExamMapper;
import com.xu.pojo.Exam;
import com.xu.redis.MybatisRedisCache;
import com.xu.service.ExamService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.redisson.misc.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {

    MybatisRedisCache mybatisRedisCache = new MybatisRedisCache("cache");

    @Resource
    private RedissonClient redissonClient;

    @Autowired
    private ExamMapper examMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

//    @Resource
    private RBloomFilter<Integer> bloomFilter;

    @Override
    public boolean save(Exam exam) {
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("lock");
        RLock rLock = readWriteLock.writeLock();
        rLock.lock(10, TimeUnit.SECONDS);
        System.out.println("exam=>"+exam);
        boolean b = SqlHelper.retBool(examMapper.insert(exam));
        List<Exam> list = (List<Exam>) mybatisRedisCache.getObject("list");
        HashMap<String,Object> map = (HashMap<String, Object>) mybatisRedisCache.getObject("map");
        System.out.println("redis=>" + list);
        if (list == null || map == null) {
            list = new ArrayList<>();
            map = new HashMap<>();
        }
        list.add(exam);
        map.put(String.valueOf(exam.getId()),exam);
        mybatisRedisCache.putObject("list", list);
        mybatisRedisCache.putObject("map",map);
        if(bloomFilter == null){
            bloomFilter = redissonClient.getBloomFilter("list");
            bloomFilter.tryInit(10000L,0.03);
            for (Exam exam1 : list) {
                map.put(String.valueOf(exam1.getId()),exam1);
                bloomFilter.add(exam1.getId());
            }
        }
        bloomFilter.add(exam.getId());
        rLock.unlock();
        return b;
    }

    @Override
    public boolean removeById(Serializable id) {
        if (bloomFilter != null) {
            if (!bloomFilter.contains((Integer) id)) {
                System.out.println("从布隆过滤器中检测到该key不存在");
                return false;
            }
        }
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("lock");
        RLock rLock = readWriteLock.writeLock();
        rLock.lock(10, TimeUnit.SECONDS);
        boolean b = SqlHelper.retBool(examMapper.deleteById(id));
        mybatisRedisCache.putObject("list",examMapper.selectList(null));
        HashMap<String,Object> map = (HashMap<String, Object>)mybatisRedisCache.getObject("map");
        map.remove(String.valueOf(id));
        mybatisRedisCache.putObject("map",map);
        rLock.unlock();
        return b;
    }

    @Override
    public boolean updateById(Exam entity) {
        if (bloomFilter != null) {
            if (!bloomFilter.contains( entity.getId())) {
                System.out.println("从布隆过滤器中检测到该key不存在");
                return false;
            }
        }
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("lock");
        RLock rLock = readWriteLock.writeLock();
        rLock.lock(10, TimeUnit.SECONDS);
        boolean b = SqlHelper.retBool(examMapper.updateById(entity));
        mybatisRedisCache.putObject("list",examMapper.selectList(null));
        HashMap<String,Object> map = (HashMap<String, Object>)mybatisRedisCache.getObject("map");
        map.put(String.valueOf(entity.getId()),entity);
        mybatisRedisCache.putObject("map",map);
        rLock.unlock();
        return b;
    }

    @Override
    public Exam getById(Serializable id) {
        if (bloomFilter != null) {
            if (!bloomFilter.contains((Integer) id)) {
                System.out.println("从布隆过滤器中检测到该key不存在");
                return null;
            }
        }
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("lock");
        RLock rLock = readWriteLock.readLock();
        Exam exam ;
        try {
            rLock.lock(10, TimeUnit.SECONDS);
            HashMap<String,Object> map = (HashMap<String, Object>)mybatisRedisCache.getObject("map");
           exam = (Exam) map.get(String.valueOf(id));
        }finally {
            rLock.unlock();
        }
        System.out.println("key"+id+"  "+"exam=>"+exam);
        return exam;
    }

    @Override
    public List<Exam> list() {
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("lock");
        //redis分布式锁之读写锁
        //读锁
        RLock rLock = readWriteLock.readLock();
        rLock.lock(10, TimeUnit.SECONDS);
//        redis缓存使用Redistemplate
        List<Exam> cache = null;
        if (redisTemplate.hasKey("list")) {
            cache = (List<Exam>) mybatisRedisCache.getObject("list");
            System.out.println(cache);
            rLock.unlock();
            return cache;
        }
        rLock.unlock();

        //写锁
        RLock rLock1 = readWriteLock.writeLock();
        rLock1.lock(10, TimeUnit.SECONDS);
        //解缓存雪崩
        cache = (List<Exam>) mybatisRedisCache.getObject("list");
        if (cache != null) {
            System.out.println(cache);
            rLock1.unlock();
            return cache;
        }
        Object o = new Object();

//        //查询数据库操作
        List<Exam> list = examMapper.selectList(null);
        if (list == null )
            //解决redis缓存穿透
        {
            int time = new Random().nextInt(4) + 1;
            mybatisRedisCache.putObject("list", null, time*60);
            mybatisRedisCache.putObject("map", null, time * 60);
        }
        else {
            mybatisRedisCache.putObject("list", list);
            HashMap<String, Object> map = new HashMap<>();
            bloomFilter = redissonClient.getBloomFilter("list");
            bloomFilter.tryInit(10000L,0.03);
            for (Exam exam : list) {
                map.put(String.valueOf(exam.getId()),exam);
                bloomFilter.add(exam.getId());
            }
            System.out.println("list=>"+list);
            System.out.println("map=>"+map);
            mybatisRedisCache.putObject("map",map);
        }

        rLock1.unlock();
        return list;
    }
}
