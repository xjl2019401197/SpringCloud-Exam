package com.xu.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.mapper.ChoiceMapper;
import com.xu.pojo.Choice;
import com.xu.service.ChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ChoiceServiceImpl extends ServiceImpl<ChoiceMapper, Choice> implements ChoiceService {
    @Autowired
    private ChoiceMapper choiceMapper;
    @Override
    public List<Integer> getIdList(int Selnum) {
        return choiceMapper.getIdList(Selnum);
    }
    public List<Choice> getAllChoice(){
        return choiceMapper.getAllChoice();
    }
    @Override
    public void reset() {
        choiceMapper.reset();
    }
}
