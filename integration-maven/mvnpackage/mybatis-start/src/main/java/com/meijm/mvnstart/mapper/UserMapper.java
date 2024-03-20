package com.meijm.mvnstart.mapper;

import com.meijm.mvnstart.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findUsers();
}
