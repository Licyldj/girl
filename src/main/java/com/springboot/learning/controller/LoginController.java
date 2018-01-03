package com.springboot.learning.controller;

import com.springboot.learning.domain.ResponseData;
import com.springboot.learning.interceptor.Login;
import com.springboot.learning.utils.JWT;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public @ResponseBody ResponseData login(@RequestParam String userName, @RequestParam String password){

        System.out.println("userName:"+userName);
        System.out.println("password:"+password);

        Login login = new Login();
        login.setId(1);
        login.setUserName(userName);
        login.setPassword(password);
        //数据库校验

        String token = JWT.sign(login,60L*1000L*30L);
        ResponseData responseData = ResponseData.ok();
        //封装成对象返回给客户端
        responseData.putDataValue("loginId", login.getId());
        responseData.putDataValue("token", token);
        responseData.putDataValue("user", userName);
        return responseData;
    }

}
