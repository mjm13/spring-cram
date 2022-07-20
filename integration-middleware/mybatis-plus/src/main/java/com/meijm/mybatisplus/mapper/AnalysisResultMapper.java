package com.meijm.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meijm.mybatisplus.entity.AnalysisResult;
import com.meijm.mybatisplus.vo.AnalysisResultVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author mjm
 * @Date 2022/4/25 17:51
 **/
@Mapper
public interface AnalysisResultMapper extends BaseMapper<AnalysisResult> {

    List<AnalysisResultVO> findAnalysisResult();

}
