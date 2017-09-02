package com.springboot.learning.repository;

import com.springboot.learning.domain.Girl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by coder on 2017/8/25.
 */

public interface GirlRepository extends JpaRepository<Girl,Integer>{

    public List<Girl> findByAge(Integer age);
}
