package com.xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.pojo.Test;

import java.util.List;

public interface TestService extends IService<Test> {
     int getbyExamIdCount(int examid);
     Test getByIdAndStuid(int id,String Stuid);
     List<Test> getByStuid(String Stuid);
}
