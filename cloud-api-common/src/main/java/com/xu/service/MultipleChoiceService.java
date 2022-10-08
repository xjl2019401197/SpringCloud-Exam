package com.xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.pojo.MultipleChoice;

import java.util.List;

public interface MultipleChoiceService  extends IService<MultipleChoice> {
    List<Integer> getIdList(int multiplenum);
    void reset();
    List<MultipleChoice> getAllMultipleChoice();
}
