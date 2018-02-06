package com.example.demo.repository;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    void save(User user);
    User selectByUsername(@Param("username")String username);
}