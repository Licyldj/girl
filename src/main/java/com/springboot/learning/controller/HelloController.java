package com.springboot.learning.controller;

import com.springboot.learning.properties.GirlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by coder on 2017/8/25.
 */

@RestController
public class HelloController {

//    @Value("${cupSize}")
//    private String cupSize;
//
//    @Value("${age}")
//    private int age;

//    @Value("${content}")
//    private String content;

    @Autowired
    private GirlProperties girlProperties;

//    @RequestMapping(value = {"/hello","/hi"},method = RequestMethod.GET)
//    @RequestMappinging(value = "/hello",method = RequestMethod.GET)
    @GetMapping(value = "/hello")
    public String say(@PathVariable("id") Integer id){
        return girlProperties.getCupSize();
    }
}
