package cbs.model;

import lombok.Data;

/**
 * @Description 小车代理
 * @Author MeiJM
 * @Date 2022/12/14
 **/
@Data
public class CbsAgent {
    //小车编号
    private String id;
    //起始坐标
    private String start;
    //终点坐标
    private String goal;
}
