package com.xu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xu.pojo.Choice;
import com.xu.pojo.Manager;
import com.xu.pojo.MultipleChoice;
import com.xu.redis.MybatisRedisCache;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
public interface multipleChoiceMapper  extends BaseMapper<MultipleChoice> {
    @Select("alter table multiplechoice AUTO_INCREMENT=1")
    void reset();
    @Select("select * from multiplechoice order by id")
    List<MultipleChoice> getAllMultipleChoice();
    @Select("SELECT * FROM `judge` AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM `judge`)-(SELECT MIN(id) FROM `judge`))+(SELECT MIN(id) FROM `judge`)) AS id) AS t2\n" +
            "WHERE t1.id >= t2.id\n" +
            "ORDER BY t1.id LIMIT ${multiplenum}")
    List<Integer> getIdList(int multiplenum);

}
