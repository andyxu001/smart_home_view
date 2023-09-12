package com.andy.home.dao;

import com.andy.home.enums.DataType;
import com.andy.home.mapper.UserMapper;
import com.andy.home.po.LoginHistory;
import com.andy.home.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    public Integer checkAccount(String userName) {
        return userMapper.checkAccount(userName);
    }

    public int toRegister(User user) {
        return userMapper.toRegister(user);
    }

    public User getUserInfo(String username) {
        return userMapper.getUserInfo(username);
    }

    public void addLoginHistory(LoginHistory history) {
        this.userMapper.addLoginHistory(history);
    }

    public void updateUserInfo(User user) {
        this.userMapper.updateUserInfo(user);
    }
}
