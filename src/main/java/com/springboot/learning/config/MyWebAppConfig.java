package com.springboot.learning.config;

import com.springboot.learning.interceptor.MyInterceptor1;
import com.springboot.learning.interceptor.MyInterceptor2;
import com.springboot.learning.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
//        registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/**");
//        registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/**");

        registry.addInterceptor(new TokenInterceptor()).excludePathPatterns("/login");

        super.addInterceptors(registry);
    }
}
