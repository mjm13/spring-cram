package com.meijm.mybatisplus.vo;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;


/**
 * 任务分析结果
 */
@Data
public class AnalysisResultVO  {
    private Long id;
    private String projectId;
    private String taskId;
    private Long analysisTaskId;
    private Long reportScenarioId;
    private Long reportTypeId;
    private Long reportIndicatorId;
    private String reportIndicatorCode;
    private String sinkTable;
    private String execId;
    private Integer analysisStatus;
    private JSONObject analysisParam;
    private JSONArray analysisResult;
    private JSONArray analysisResultOriginal;
    private JSONObject analysisResultTitle;

}
