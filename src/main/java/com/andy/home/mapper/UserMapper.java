package com.andy.home.mapper;

import com.andy.home.po.LoginHistory;
import com.andy.home.po.User;
import java.util.List;

public interface UserMapper {

     Integer checkAccount(String userName);

     int toRegister(User user);

     User getUserInfo(String username);

    void addLoginHistory(LoginHistory history);

    void updateUserInfo(User user);
}
