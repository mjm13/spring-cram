package astart.model;


import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 路径求解计算节点
 */
@Data
@ToString(callSuper = false)
public class Node implements Comparable<Node>{

    private Integer step;
    /**
     * 上一步，用于回溯路径
     */
    private Node parent;
    /**
     * 位置信息
     */
    private Coord coord;
    /**
     * H 代价距离  到目标位置代价
     */
    private int evaluateDistance;

    /**
     * G 实际距离  移动一格实际代价 可计算转向
     */
    private int actualDistance;


    @Override
    public int compareTo(Node o)
    {
        if (o == null) return -1;
        if (actualDistance + evaluateDistance > o.getActualDistance() + o.getEvaluateDistance())
            return 1;
        else if (actualDistance + evaluateDistance < o.getActualDistance() + o.getEvaluateDistance()) return -1;
        return 0;
    }

    @Override
    public String toString(){
        return coord.toString()+"g:"+actualDistance+"-h:"+evaluateDistance;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}