package com.xu.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.mapper.TeacherMapper;
import com.xu.mapper.TestMapper;
import com.xu.pojo.Test;
import com.xu.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {
    @Autowired
    private TestMapper testMapper;
    @Override
    public int getbyExamIdCount(int examid) {
        return testMapper.getbyExamIdCount(examid);
    }

    @Override
    public Test getByIdAndStuid(int id, String Stuid) {
        return testMapper.getByIdAndStuid(id,Stuid);
    }

    @Override
    public List<Test> getByStuid(String Stuid) {

        return testMapper.getByStuid(Stuid);
    }

    @Override
    public List<String> getExamNameByStuid(String Stuid) {
        return testMapper.getExamNameByStuid(Stuid);
    }
}
