package com.xu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xu.pojo.Choice;
import com.xu.redis.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface ChoiceMapper extends BaseMapper<Choice> {
    @Select("SELECT * FROM `choice` AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM `choice`)-(SELECT MIN(id) FROM `choice`))+(SELECT MIN(id) FROM `choice`)) AS id) AS t2\n" +
            "WHERE t1.id >= t2.id\n" +
            "ORDER BY t1.id LIMIT ${Selnum}")
    List<Integer> getIdList(int Selnum);
    @Select("alter table Choice AUTO_INCREMENT=1")
    void reset();
    @Select("select * from choice order by id")
    List<Choice> getAllChoice();
}
