package com.xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.pojo.Teacher;

public interface TeacherService extends IService<Teacher> {
    void reset();

    public Teacher getTeacherByIP(String id, String Password);

    int getCount();

}
