package com.xu.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.mapper.ManagerMapper;
import com.xu.mapper.multipleChoiceMapper;
import com.xu.pojo.MultipleChoice;
import com.xu.service.ManagerService;
import com.xu.service.MultipleChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class MultipleChoiceServiceImpl extends ServiceImpl<multipleChoiceMapper, MultipleChoice> implements MultipleChoiceService {
   @Autowired
   private multipleChoiceMapper multipleChoiceMapper;
    @Override
    public List<Integer> getIdList(int multiplenum) {
        return multipleChoiceMapper.getIdList(multiplenum);
    }

    @Override
    public void reset() {

    }

    @Override
    public List<MultipleChoice> getAllMultipleChoice() {
        return multipleChoiceMapper.getAllMultipleChoice();
    }

}
