package com.example.demo.repository;

import com.example.demo.model.RolePri;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RolePriMapper {
    int insert(RolePri record);

    int insertSelective(RolePri record);
}