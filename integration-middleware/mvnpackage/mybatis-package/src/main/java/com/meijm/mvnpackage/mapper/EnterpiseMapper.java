package com.meijm.mvnpackage.mapper;

import com.meijm.mvnpackage.entity.Enterpise;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EnterpiseMapper {
    List<Enterpise> findEnterpise();
}
