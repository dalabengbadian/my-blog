package com.lh.myblog.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器配置
 */
public class LoginInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getSession().getAttribute("user") == null){
            request.setAttribute("message","请登录管理员账号");
            request.getRequestDispatcher("/admin").forward(request,response);//对所有/admin下的请求进行拦截，验证是否已登录
            return false;
        }else {
            return true;
        }
    }
}
