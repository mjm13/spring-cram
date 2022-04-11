package com.meijm.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meijm.mybatisplus.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> findUsers();
}
