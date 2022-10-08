package com.xu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xu.pojo.Student;
import com.xu.redis.MybatisRedisCache;
import org.apache.ibatis.annotations.*;

@Mapper
@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface StudentMapper extends BaseMapper<Student> {
    @Delete("delete from student where stuid = ${Stuid}")
    int deleteByStuid(String Stuid);
    @Select("SELECT * FROM `student` where Name = '${Name}' and Password = '${Password}'")
    Student getStudentByName(@Param("Name") String username, @Param("Password") String Password);
    @Select("SELECT count(*) FROM `student` ")
    int getCount();


}
