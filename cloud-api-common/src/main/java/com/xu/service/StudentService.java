package com.xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.pojo.Student;

public interface StudentService extends IService<Student> {
    int deleteByStuid(String Stuid);

    Student getStudentByName(String Name,String Password);
    int getCount();
}
