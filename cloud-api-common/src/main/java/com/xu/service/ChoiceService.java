package com.xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.pojo.Choice;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChoiceService extends IService<Choice> {
    List<Integer> getIdList(int Selnum);
    void reset();
    List<Choice> getAllChoice();

}
