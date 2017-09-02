package com.springboot.learning.controller;

import com.springboot.learning.aspect.HttpAspect;
import com.springboot.learning.domain.Girl;
import com.springboot.learning.domain.Result;
import com.springboot.learning.repository.GirlRepository;
import com.springboot.learning.service.GirlService;
import com.springboot.learning.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by coder on 2017/8/25.
 */
@RestController
public class GirlController {


    private final static Logger logger = LoggerFactory.getLogger(GirlController.class);

    @Autowired
    private GirlRepository girlRepository;

    @Autowired
    private GirlService girlService;

    /**
     * 查询所有列表
     * @return
     */
    @GetMapping(value = "/girls")
    public List<Girl> girlList(){
        logger.info("girlList");
        return girlRepository.findAll();
    }

//    /**
//     * 添加一个对象
//     * @param cupSize
//     * @param age
//     * @return
//     */
//    @PostMapping(value = "/girls")
//    public Girl girlAdd(@RequestParam("cupSize") String cupSize,
//                          @RequestParam("age") Integer age){
//        Girl girl = new Girl();
//        girl.setAge(age);
//        girl.setCupSize(cupSize);
//        return girlRepository.save(girl);
//    }

    /**
     * 添加一个对象
     * @param girl
     * @return
     */
    @PostMapping(value = "/girls")
    public Result<Girl> girlAdd(@Valid Girl girl, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResultUtil.errot(1,bindingResult.getFieldError().getDefaultMessage());

        }
        return ResultUtil.success(girlRepository.save(girl));
    }

    @GetMapping(value = "/girl/{id}")
    public Girl girlFindOne(@PathVariable("id") Integer id){
        return girlRepository.findOne(id);
    }

    @PutMapping(value = "/girl/{id}")
    public Girl girlUpdate(@PathVariable("id") Integer id,
                           @RequestParam("cupSize") String cupSize,
                           @RequestParam("age") Integer age){
        Girl girl = new Girl();
        girl.setId(id);
        girl.setCupSize(cupSize);
        girl.setAge(age);
        return girlRepository.save(girl);
    }


    @DeleteMapping(value = "/girl/{id}")
    public void girlDelete(@PathVariable("id") Integer id){

        girlRepository.delete(id);
    }

    @GetMapping(value = "/girl/age/{age}")
    public List<Girl> girlListByAge(@PathVariable("age") Integer id){
        return girlRepository.findByAge(id);
    }

    @PostMapping(value = "/girl/two")
    public void insertTwo(){
        girlService.insertTwo();
    }

    @GetMapping(value = "/girls/getAge/{id}")
    public void getAge(@PathVariable("id") Integer id) throws Exception {
        girlService.getAge(id);
    }
}
