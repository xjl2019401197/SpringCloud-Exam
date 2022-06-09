package com.xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.pojo.Judge;

import java.util.List;

public interface JudgeService extends IService<Judge> {
    List<Integer> getIdList(int Judnum);
    List<Judge> getAllJudge();

    void reset();
    Judge getByContent(String content);
}
