package com.xu.serviceImpl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.mapper.ManagerMapper;
import com.xu.mapper.StudentMapper;
import com.xu.pojo.Manager;
import com.xu.pojo.Student;
import com.xu.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {
    @Autowired
    private ManagerMapper managerMapper;
    @Override
    public Manager getManagerByName(String username, String Password) {
        return managerMapper.getManagerByName(username,Password);
    }

    @Override
    public int getCount() {
        return managerMapper.getCount();
    }
}
