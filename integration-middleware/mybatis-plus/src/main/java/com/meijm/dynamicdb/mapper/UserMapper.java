package com.meijm.dynamicdb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meijm.dynamicdb.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> findUsers();
}
