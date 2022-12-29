package pathPlanning.stepAstart.model;

import lombok.Data;

import java.util.Date;

/**
 * @Description 移动障碍物
 * @Author MeiJM
 * @Date 2022/12/29
 **/
@Data
public class SaMoveObstacle {
    private SaLocation saLocation;
    private Date start;
    private Date end;
}
