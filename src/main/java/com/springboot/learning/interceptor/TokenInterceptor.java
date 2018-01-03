package com.springboot.learning.interceptor;

import com.springboot.learning.domain.ResponseData;

import com.alibaba.fastjson.JSONObject;
import com.springboot.learning.utils.JWT;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("----------TokenInterceptor-----------");
        response.setCharacterEncoding("utf-8");
        String token = request.getParameter("token");
        ResponseData responseData = ResponseData.ok();
        //token不存在
        if(null != token) {
            Login login = JWT.unsign(token, Login.class);
            String loginId = request.getParameter("loginId");
            //解密token后的loginId与用户传来的loginId不一致，一般都是token过期
            if(null != loginId && null != login) {
                if(Integer.parseInt(loginId) == login.getId()) {
                    return true;
                }
                else{
                    responseData = ResponseData.forbidden();
                    responseMessage(response, response.getWriter(), responseData);
                    return false;
                }
            }else {
                responseData = ResponseData.forbidden();
                responseMessage(response, response.getWriter(), responseData);
                return false;
            }
        } else {
            responseData = ResponseData.forbidden();
            responseMessage(response, response.getWriter(), responseData);
            return false;
        }


    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    //请求不通过，返回错误信息给客户端
    private void responseMessage(HttpServletResponse response, PrintWriter out, ResponseData responseData) {
        responseData = ResponseData.forbidden();
        response.setContentType("application/json; charset=utf-8");
        String json = JSONObject.toJSONString(responseData);
        out.print(json);
        out.flush();
        out.close();
    }
}
