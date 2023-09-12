package com.andy.home.filter;

import com.andy.home.constant.Constant;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

//对所有请求拦截
@WebFilter(filterName = "",urlPatterns = "/*")
@Component
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter初始化。。。。");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //System.out.println("请求的地址:"+request.getRequestURI());
        String requestURI = request.getRequestURI();
        /**
         * 放行的请求(跳转到登录页面,普通登录,登出,调用QQ登录,静态资源访问)
         */
        if (requestURI.contains("/login.do")
        || requestURI.contains("/toLogin.do")
        || requestURI.contains("/logout.do")
        || requestURI.contains("/user/showRegister.do")
        || requestURI.contains("/user/checkAccount.do")
        || requestURI.contains("/user/toRegister.do")
        || requestURI.contains("/product/uploadFile.do")
        || requestURI.contains("/loginByQQ.do")
        || requestURI.contains("/qqLogin")
        || requestURI.contains("/oauth2.0")
        || requestURI.contains("/user/get_user_info")
        || requestURI.contains("/css/")
        || requestURI.contains("/images/")
        || requestURI.contains("/icon/")
        || requestURI.contains("/favicon.icon")
        || requestURI.contains("/js/")
        || (requestURI.startsWith("/") && requestURI.length() == 1)) {
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            //检查是否用户登录
            Object user = request.getSession().getAttribute(Constant.SESSION_KEY_FOR_USER);
            //System.out.println("被拦截请求的地址:"+request.getRequestURI());
            if(Objects.isNull(user)) {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.write("<script type='text/javascript'>alert('请重新登录');parent.location.href='/login.do'</script>");
                return;
            }
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
