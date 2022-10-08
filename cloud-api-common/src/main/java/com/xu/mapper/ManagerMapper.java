package com.xu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xu.pojo.Manager;
import com.xu.pojo.Student;
import com.xu.redis.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
public interface ManagerMapper extends BaseMapper<Manager> {
    @Select("SELECT * FROM `manager` where MName = '${MName}' and MPsw = '${Password}'")
    Manager getManagerByName(@Param("MName") String username, @Param("Password") String Password);
    @Select("SELECT count(*) FROM `manager` ")
    int getCount();
}
