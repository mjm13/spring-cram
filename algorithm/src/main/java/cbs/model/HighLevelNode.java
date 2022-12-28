package cbs.model;

import cn.hutool.core.clone.CloneSupport;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 整体解决方案
 *
 * @Description
 * @Author MeiJM
 * @Date 2022/12/15
 **/
@Data
public class HighLevelNode implements Comparable<HighLevelNode> {
    //解决方案map，key为小车编号
    private Map<String, List<State>> solutions = new HashMap<>();
    // 冲突字典，key为小车编号
    private Map<String,Constraint> constraintDict = new HashMap<>();
    //方案所需步骤数量
    private Long cost;

    @Override
    public int compareTo(HighLevelNode o)
    {
        if (o == null) return -1;
        if (this.getCost() > o.getCost())
            return 1;
        else if (this.getCost() < this.getCost()) return -1;
        return 0;
    }
}
