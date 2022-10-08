package com.xu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xu.pojo.Teacher;
import com.xu.redis.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface TeacherMapper extends BaseMapper<Teacher> {
    @Select("alter table Teacher AUTO_INCREMENT=1")
    void reset();

    @Select("SELECT * FROM `teacher` where Name = '${Name}' and Password = '${Password}'")
    public Teacher getTeacherByName(@Param("Name") String username, @Param("Password") String Password);

    @Select("SELECT count(*) FROM `teacher` ")
    int getCount();

}
