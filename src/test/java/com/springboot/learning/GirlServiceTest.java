package com.springboot.learning;

import com.springboot.learning.domain.Girl;
import com.springboot.learning.service.GirlService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by coder on 2017/8/26.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class GirlServiceTest {

    @Autowired
    private GirlService girlSerivce;

    @Test
    public void findOneTest(){
        Girl girl = girlSerivce.findOne(7);
        Assert.assertEquals(new Integer(15),girl.getAge());
    }
}
