package com.example.demo.repository;

import com.example.demo.model.Pri;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PriMapper {
    int insert(Pri record);

    int insertSelective(Pri record);
}