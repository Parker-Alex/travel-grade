package com.leo.interceptor;

import com.leo.pojo.TravelUser;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * @ClassName LoginInterceptor
 * @Description 后台登录拦截器
 * @Author li.jiawei
 * @Date 2019/5/1 15:08
 * @Version 1.0
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        得到请求路径
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
//        System.out.println(uri);
//        System.out.println(contextPath);
//        获得session
        HttpSession session = request.getSession();
        if ((uri.startsWith(contextPath + "/admin")) && !uri.startsWith(contextPath + "/admin/login") && !uri.startsWith(contextPath + "/admin/index")){
            TravelUser user = (TravelUser) session.getAttribute("user");
            if (null == user) {
                response.sendRedirect("/admin/login");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
