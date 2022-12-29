package pathPlanning.stepAstart.model;

import lombok.Data;

/**
 * @Description 代理信息
 * @Author MeiJM
 * @Date 2022/12/29
 **/
@Data
public class SaAgent {
    //初始方向
    private SaDirection direction;
    //小车编号
    private String id;
    //起始坐标
    private String start;
    //终点坐标
    private String goal;
}
