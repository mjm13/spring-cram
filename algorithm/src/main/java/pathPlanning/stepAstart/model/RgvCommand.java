package pathPlanning.stepAstart.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 移动指令
 * @Author MeiJM
 * @Date 2022/12/29
 **/
@Data
public class RgvCommand {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private Date startTime;
    private Date endTime;
    private SaDirection oldDirection;
    private SaDirection newDirection;
    //移动耗时
    private long costTime;
    private  List<SaProbeNode> nodes = new ArrayList<>();

    /**
     * @Description 获取移动距离
     * @Author MeiJM
     * @Date 2022/12/29
     * @param
     * @return int
     **/
    public int getDistance() {
        int distance = 0;
        if (startX == endX) {
            distance = endY - startY;
        }else{
            distance = endX - startX;
        }
        if(distance == 0){
            distance = 1;
        }
        return distance;
    }
}
