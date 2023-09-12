package com.andy.home.service.impl;

import com.andy.home.dao.UserDao;
import com.andy.home.po.LoginHistory;
import com.andy.home.po.User;
import com.andy.home.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Value("${protocol}")
    public String protocol;

    @Value("${domain}")
    public String domain;

    @Value("${ip}")
    public String ip;

    @Value("${defaultImageSrc}")
    public String defaultImageSrc;


    @PostConstruct
    public void getIpAddr() throws UnknownHostException {
        //如果ip是腾讯云地址，则换成域名
        ip = "111.231.14.181".equals(ip) ? domain :ip;
        defaultImageSrc = protocol +ip + defaultImageSrc;
    }

    @Autowired
    private UserDao userDao;

    @Override
    public boolean checkAccount(String userName) {
        Integer count = userDao.checkAccount(userName);
        return  count > 0;
    }

    @Override
    public int toRegister(User user) {
        return this.userDao.toRegister(user);
    }

    @Override
    public User getUserInfo(String username) {
        User userInfo = this.userDao.getUserInfo(username);
        if(Objects.nonNull(userInfo) && "Y".equals(userInfo.getIsNormalUser())){
            String url =userInfo.getAvatarUrl();
            userInfo.setAvatarUrl(Objects.isNull(url) ? defaultImageSrc:protocol+ip+ File.separator +url);
        }
        return userInfo;
    }

    @Override
    public void addLoginHistory(LoginHistory history) {
        this.userDao.addLoginHistory(history);
    }

    @Override
    public void updateUserInfo(User user) {
        this.userDao.updateUserInfo(user);
    }

}
