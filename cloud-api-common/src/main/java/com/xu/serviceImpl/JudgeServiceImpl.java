package com.xu.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.mapper.JudgeMapper;
import com.xu.pojo.Judge;
import com.xu.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgeServiceImpl extends ServiceImpl<JudgeMapper, Judge> implements JudgeService {
    @Autowired
    private JudgeMapper judgeMapper;

    @Override
    public List<Integer> getIdList(int Judnum) {
        return judgeMapper.getIdList(Judnum);
    }
    public List<Judge> getAllJudge(){
        return judgeMapper.getAllJudge();
    }
    @Override
    public void reset() {
        judgeMapper.reset();
    }

    @Override
    public Judge getByContent(String content) {
        return judgeMapper.getByContent(content);
    }
}
