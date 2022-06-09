package com.xu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xu.pojo.Judge;
import com.xu.redis.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
//@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface JudgeMapper extends BaseMapper<Judge> {
    @Select("select id from  judge   order by RAND() limit ${Judnum}")
    List<Integer> getIdList(int Judnum);
    @Select("alter table Judge AUTO_INCREMENT=1")
    void reset();
    @Select("select * from judge order by id")
    List<Judge> getAllJudge();
    @Select("select * from judge where Content = '${content}'")
    Judge getByContent(String content);
}
