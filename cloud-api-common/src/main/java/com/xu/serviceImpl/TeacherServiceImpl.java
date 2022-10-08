package com.xu.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.mapper.TeacherMapper;
import com.xu.pojo.Student;
import com.xu.pojo.Teacher;
import com.xu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Override
    public void reset() {
        teacherMapper.reset();
    }
    @Override
    public Teacher getTeacherByName(String username, String Password) {
        return teacherMapper.getTeacherByName(username,Password);
    }

    @Override
    public int getCount() {
        return teacherMapper.getCount();
    }
}
