package com.meijm.mybatisplus.entity;

import lombok.Data;

import java.util.Date;


/**
 * 数据分析-分析结果
 */
@Data
public class AnalysisResult {

    protected Long id;

    private String creator;

    private String creatorName;

    private Date createTime;
    private String modifier;

    private String modifierName;

    private Date modifyTime;

    private String enterpriseId;
    //项目主键
    private String projectId;
    //任务主键
    private Long analysisTaskId;
    //场景主键
    private Long reportScenarioId;
    //类型主键
    private Long reportTypeId;
    //指标主键
    private Long reportIndicatorId;
    //数据表标识
    private String sinkTable;
    //大数据端执行id，用于回查，默认为空，二次计算生成
    private String execId;
    //任务id大数据端生成
    private String taskId;
    //分析状态 0：未开始，1：进行中，2：已完成，3：处理异常
    private Integer analysisStatus;
    //分析参数
    private String analysisParam;
    //分析结果
    private String analysisResult;
    //分析结果原始数据
    private String analysisResultOriginal;
    //分析结果表头
    private String analysisResultTitle;

}
