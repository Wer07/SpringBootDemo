package com.example.demo.repository;

import com.example.demo.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> selectByUid(@Param("uId")Long uId);
}