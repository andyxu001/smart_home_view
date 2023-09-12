package com.andy.home.controller;

import com.alibaba.fastjson.JSONObject;
import com.andy.home.constant.Constant;
import com.andy.home.po.User;
import com.andy.home.service.UserService;
import com.andy.home.util.Md5Util;
import com.andy.home.util.UserUntils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "UserController",description = "用户管理")
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到用户管理
     * @param model
     * @return
     */
    @RequestMapping("/userManage.do")
    public String userManage(Model model){
        return "userManage";
    }

    // 跳转到注册页
    @RequestMapping("/showRegister.do")
    public String showRegister(){
        return "register";
    }

    // 跳转到修改个人资料页面
    @RequestMapping("/showUpdateRegister.do")
    public String showUpdateRegister(Model model,HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(Constant.SESSION_KEY_FOR_USER);
        User userInfo = userService.getUserInfo(user.getUserName());
        model.addAttribute("userInfo",userInfo);
        return "updateRegister";
    }

    @RequestMapping("/toRegister.do")
    @ResponseBody
    public String toRegister(@RequestBody User user, Model model){
        user.setPassword(Md5Util.md5(user.getPassword()));
        user.setIsNormalUser("Y");
        user.setIsQQUser("N");
        user.setIsWechatUser("N");
        userService.toRegister(user);
        return JSONObject.toJSONString("success");
    }

    /**
     * 提交注册信息
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/updateUserInfo.do")
    @ResponseBody
    public String updateUserInfo(@RequestBody User user, Model model){
        userService.updateUserInfo(user);
        return JSONObject.toJSONString("success");
    }

    @ResponseBody
    @RequestMapping("/checkAccount.do")
    public boolean checkAccount(HttpServletResponse response, String userName){
        boolean flag = userService.checkAccount(userName);
        return (boolean) JSONObject.toJSON(flag);
    }

}
