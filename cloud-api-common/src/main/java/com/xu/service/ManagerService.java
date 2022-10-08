package com.xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.pojo.Manager;

public interface ManagerService extends IService<Manager> {
    public Manager getManagerByName(String username, String Password) ;
    int getCount();

}
