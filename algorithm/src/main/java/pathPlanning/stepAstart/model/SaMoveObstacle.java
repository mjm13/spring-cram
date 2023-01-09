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
    public SaMoveObstacle(SaLocation saLocation,Date start,Date end){
        this.saLocation = saLocation;
        this.start = start;
        this.end = end;
    }
    private SaLocation saLocation;
    private Date start;
    private Date end;
}
