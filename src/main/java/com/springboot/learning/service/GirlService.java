package com.springboot.learning.service;

import com.springboot.learning.domain.Girl;
import com.springboot.learning.enums.ResultEnum;
import com.springboot.learning.exception.GirlException;
import com.springboot.learning.repository.GirlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by coder on 2017/8/25.
 */
@Service
public class GirlService {

    @Autowired
    private GirlRepository girlRepository;

    @Transactional
    public void insertTwo(){
        Girl girlA = new Girl();
        girlA.setAge(18);
        girlA.setCupSize("A");
        girlRepository.save(girlA);

        Girl girlB = new Girl();
        girlB.setAge(19);
        girlB.setCupSize("bbbD");
        girlRepository.save(girlB);

    }

    public void getAge(Integer id) throws Exception {
        Girl girl = girlRepository.findOne(id);
        Integer age = girl.getAge();
        if(age<10){
            //返回。你还在上小学
            throw new GirlException(ResultEnum.PRIMARY_SCHOOL);
        }else if(age >10 && age <16){
            //返回。你上初中
            throw new GirlException(ResultEnum.PRIMARY_SCHOOL);
        }
    }

    /**
     * 通过ID查询一个女生的信息
     * @param id
     * @return
     */
    public Girl findOne(Integer id){
        return girlRepository.findOne(id);
    }
}
