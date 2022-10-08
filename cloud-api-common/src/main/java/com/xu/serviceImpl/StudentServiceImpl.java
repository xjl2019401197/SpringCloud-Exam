package com.xu.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.mapper.StudentMapper;
import com.xu.pojo.Student;
import com.xu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public int deleteByStuid(String Stuid) {
        return studentMapper.deleteByStuid(Stuid);
    }

    @Override
    public Student getStudentByName(String username, String Password) {
        return studentMapper.getStudentByName(username,Password);
    }

    @Override
    public int getCount() {
        return studentMapper.getCount();
    }


}
