package com.xu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xu.pojo.Test;
import com.xu.redis.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface TestMapper extends BaseMapper<Test> {
    @Select("SELECT Count(*) FROM `test` where id = ${examid} ")
    int getbyExamIdCount(@Param("examid")int examid );
    @Select("SELECT * FROM `test` where id = ${id} and Stuid = '${Stuid}'")
    Test getByIdAndStuid(@Param("id")int id,@Param("Stuid")String Stuid);
    @Select("SELECT * FROM `test` where  Stuid = '${Stuid}'")
    List<Test> getByStuid(@Param("Stuid")String Stuid);
    @Select("SELECT exam.Examname FROM `exam`,`test` where exam.id = test.id")
    List<String> getExamNameByStuid(@Param("Stuid") String Stuid);
}
