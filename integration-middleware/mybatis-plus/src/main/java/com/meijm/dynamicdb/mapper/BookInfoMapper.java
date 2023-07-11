package com.meijm.dynamicdb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meijm.dynamicdb.entity.BookInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookInfoMapper extends BaseMapper<BookInfo> {
}
