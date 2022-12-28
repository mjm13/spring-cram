package cbs.model;


import cn.hutool.json.JSONUtil;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 路径求解计算节点
 */
@Getter
@Setter
@ToString(exclude = {"parent"} )
@EqualsAndHashCode(exclude = {"parent","evaluateDistance","actualDistance"})
public class State implements Comparable<State>{

    public State(Integer step, State waitNode){
        this.step = step;
        this.parent = waitNode;
        this.location = waitNode.getLocation();
        this.evaluateDistance = waitNode.getEvaluateDistance()+step;
        this.actualDistance = waitNode.getActualDistance();
    }

    public State(Integer step, State parent, Location location, int evaluateDistance, int actualDistance){
        this.step = step;
        this.parent = parent;
        this.location = location;
        this.evaluateDistance = evaluateDistance;
        this.actualDistance = actualDistance;
    }

    public State(){

    }

    private Integer step;
    /**
     * 上一步，用于回溯路径,获取路径时清空
     */
    private State parent;
    /**
     * 位置信息
     */
    private Location location;
    /**
     * H 代价距离  到目标位置代价
     */
    private int evaluateDistance;

    /**
     * G 实际距离  移动一格实际代价 可计算转向
     */
    private int actualDistance;


    @Override
    public int compareTo(State o)
    {
        if (o == null) return -1;
        if (actualDistance + evaluateDistance > o.getActualDistance() + o.getEvaluateDistance())
            return 1;
        else if (actualDistance + evaluateDistance < o.getActualDistance() + o.getEvaluateDistance()) return -1;
        return 0;
    }

    @Override
    public String toString(){
        Map<String,Object> temp = new HashMap<>();
        temp.put("location",location);
        temp.put("step",step);
        return JSONUtil.toJsonStr(temp);
    }

    public void setParent(State parent) {
        this.parent = parent;
    }


}