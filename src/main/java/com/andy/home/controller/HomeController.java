package com.andy.home.controller;

import com.andy.home.constant.Constant;
import com.andy.home.po.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping("home")
@Controller
public class HomeController {

    // 访问首页
    @RequestMapping("/welcome.do")
    public String welcome(Model model,String accessToken,HttpServletRequest request) {
        Object obj = request.getSession().getAttribute(Constant.SESSION_KEY_FOR_USER);
        User user = (User) obj;
        model.addAttribute("userInfo",user);
        return "welcome";
    }

    @RequestMapping("/home.do") // 访问首页
    public String home(Model model) {
        return "homePage";
    }


}
