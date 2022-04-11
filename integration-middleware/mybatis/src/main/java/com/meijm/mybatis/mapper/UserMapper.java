package com.meijm.mybatis.mapper;

import com.meijm.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findUsers();
}
