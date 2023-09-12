package com.andy.home.controller;


import com.alibaba.fastjson.JSONObject;
import com.andy.home.constant.Constant;
import com.andy.home.po.LoginHistory;
import com.andy.home.po.MyUserInfo;
import com.andy.home.po.MyUserInfoBean;
import com.andy.home.po.User;
import com.andy.home.service.UserService;
import com.andy.home.util.Md5Util;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Api(tags = "LoginController",description = "用户登录")
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @Value("${spring.profiles.active}")
    public String env;


    /**
     * 跳转到登录首页
     * @param model
     * @return
     */
    @RequestMapping("/login.do")
    public String login(Model model,HttpServletRequest request) {
        log.info("环境:"+env);
        model.addAttribute("env",env);
        return "login";
    }

    /**
     * 提交登录
     * @param username
     * @param password
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/toLogin.do")
    public void toLogin(String username, String password, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = userService.getUserInfo(username);
        try{
            //登录成功
            if(Objects.nonNull(user) && Md5Util.md5(password).equals(user.getPassword())) {
                request.getSession().setAttribute(Constant.SESSION_KEY_FOR_USER,user);
                //增加登录日志
                addLoginHistory(request,Constant.LOGIN);
                //设定session30分钟
                request.getSession().setMaxInactiveInterval(60*30);
                response.sendRedirect("/home/welcome.do");
            } else {
                //登录失败
                response.sendRedirect("/login.do");
            }
        }catch (Exception e){
            log.info(e.getMessage());
        }

    }

    /**
     * 退出登录
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/logout.do")
    public void logout(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException {
        Object user = request.getSession().getAttribute(Constant.SESSION_KEY_FOR_USER);
        if (Objects.nonNull(user)) {
            //记录登录的信息
            addLoginHistory(request,Constant.LOGOUT);
            request.getSession().removeAttribute(Constant.SESSION_KEY_FOR_USER);
        }
        response.sendRedirect("/login.do");
    }

    //以下为第三方登录(QQ登录)
    /**
     * QQ登录请求
     */
    @RequestMapping("/loginByQQ.do")
    public void loginByQq(HttpServletRequest request, HttpServletResponse response) {
        // html类型，以网页形式打开
        response.setContentType("text/html;charset=utf-8");
        try {
            response.sendRedirect(new Oauth().getAuthorizeURL(request));
            log.info("In the QQ login...");
        } catch (IOException | QQConnectException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * QQ登录回调方法
     * @return
     */
    @RequestMapping("/qqLogin")
    public void connection(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String accessToken = null, openID = null;
        MyUserInfoBean userInfoBean = null;
        long tokenExpireIn;
        User user = null;
        try {
            // 获取AccessToken， getAccessTokenByRequest
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
            if (StringUtils.isEmpty(accessTokenObj.getAccessToken())) {
                log.info("Login Failed, accessToken is blank");
                // 未获取到accessToken，可以考虑：跳转回登录界面附带一个参数进行错误提示
                //return "error";
            } else {
                accessToken = accessTokenObj.getAccessToken();
                // token过期时间 - 10分钟
                tokenExpireIn = accessTokenObj.getExpireIn();
                request.getSession().setAttribute("access_token", accessToken);
                request.getSession().setAttribute("token_expirein", String.valueOf(tokenExpireIn));
                log.info("有效期:"+tokenExpireIn);

                // 根据AccessToken获取OpenId
                OpenID openIDObj = new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();

                /**
                 *
                 *
                 * 检查openID是否存在数据库中，如果存在，则从数据库中取，否则调用QQ接口，然后存放到数据库
                 */
                if(this.userService.checkAccount(openID)) {
                    User info = userService.getUserInfo(openID);
                    request.getSession().setAttribute(accessToken,info);
                    request.getSession().setAttribute(Constant.SESSION_KEY_FOR_USER,info);
                    //记录登录的信息
                    addLoginHistory(request,Constant.LOGIN);
                    response.sendRedirect("/home/welcome.do?accessToken="+accessToken);
                    return;
                }
                // 获取用户信息
                MyUserInfo userInfo = new MyUserInfo(accessToken, openID);
                userInfoBean = userInfo.getUserInfo();
                // 返回码为0是正常返回，其他表示错误。
                if (userInfoBean.getRet() == 0) {
                    userInfoBean.setOpenId(openID);
                    // 获取昵称，需要去除表情、特殊符号
                    userInfoBean.setNickname(removeNonBmpUnicode(userInfoBean.getNickname()));

                    //保存到数据库中
                    user = new User();
                    user.setUserName(openID);
                    user.setNickName(userInfoBean.getNickname());
                    user.setAvatarId(0L);
                    user.setAvatarUrl(userInfoBean.getAvatarForQQ());
                    user.setPassword(null);
                    user.setIsNormalUser("N");
                    user.setIsQQUser("Y");
                    user.setIsWechatUser("N");
                    this.userService.toRegister(user);
                } else {
                    log.info("QQ Login Failed, Reason is: " + userInfoBean.getMsg());
                }
                log.info(userInfoBean.toString());
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        request.getSession().setAttribute(accessToken,user);
        request.getSession().setAttribute(Constant.SESSION_KEY_FOR_USER,user);
        //记录登录的信息
        addLoginHistory(request,Constant.LOGIN);

        response.sendRedirect("/home/welcome.do?accessToken="+accessToken);
    }

    private void addLoginHistory(HttpServletRequest request,String opType) {
        User user = (User) request.getSession().getAttribute(Constant.SESSION_KEY_FOR_USER);
        //增加登录日志
        String ip = getIp(request);
        LoginHistory history = new LoginHistory();
        history.setUserId(user.getId());
        history.setNickName(user.getNickName());
        history.setIp(ip);
        history.setOpType(opType);
        userService.addLoginHistory(history);
    }

    /**
     * 处理掉QQ网名中的特殊表情
     */
    public String removeNonBmpUnicode(String str) {
        if (str == null) {
            return "QQ登录用户";
        }
        str = str.replaceAll("[^\\u0000-\\uFFFF]", "");
        if ("".equals(str)) {
            str = "QQ登录用户";
        }
        return str;
    }


    /**
     * 获取客户端真实ip地址
     * @param request
     * @return
     */
    private String getIp(HttpServletRequest request){
        /**
         * 获取距离服务器最远的那个ip
         */
        String ip = request.getHeader("x-forwarded-for");
        if (ipIsNullOrEmpty(ip)){
            /**
             * apache http服务代理加上的ip
             */
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ipIsNullOrEmpty(ip)){
            /**
             * weblogic插件加上的头
             */
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipIsNullOrEmpty(ip)){
            /**
             * 真实ip
             */
            ip = request.getHeader("X-Real-IP");
        }
        if (ipIsNullOrEmpty(ip)){
            /**
             * 最后真实的ip
             */
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * 当前ip是否为空
     * @param ip
     * @return
     */
    private boolean ipIsNullOrEmpty(String ip){
        if(ip == null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            return true;
        }
        return false;
    }
}
