package com.andy.home.util;

import com.andy.home.constant.Constant;
import com.andy.home.po.User;

import javax.servlet.http.HttpServletRequest;

public class UserUntils {

    /**
     * 全局获取登录用户的ID
     * @param request
     * @return
     */
    public static Integer getUserId(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constant.SESSION_KEY_FOR_USER);
        return user.getId();
    }
}
