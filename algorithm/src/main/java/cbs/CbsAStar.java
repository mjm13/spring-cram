package cbs;

import cbs.model.CbsAgent;
import cbs.model.Environment;
import cbs.model.Location;
import cbs.model.State;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import java.util.*;
import java.util.stream.Collectors;

public class CbsAStar {
    //缓存地图信息
    private Environment environment;
    //待遍历节点，优先队列(升序);
    private Queue<State> openList = new PriorityQueue<State>();
    //已遍历节点
    private List<Location> closeList = new ArrayList<Location>();
    //终点
    private Location end;
    //最大迭代次数
    private Integer maxCount;

    public List<State> searchPath(CbsAgent agent, Environment environment) {
        this.environment = environment;
        this.end = environment.getLocation(agent.getGoal());
        // 清空历史记录
        this.openList.clear();
        this.closeList.clear();
        State node = new State();
        node.setStep(0);
        node.setLocation(environment.getLocation(agent.getStart()));
        node.setParent(null);
        // 初始化开始位置
        openList.add(node);
        while (!openList.isEmpty()) {
            State current = openList.poll();
            if (end.equals(current.getLocation())) {
                return getPath(current);
            }
            Set<State> neighbors =environment.getNeighbors(current,end);
            openList.addAll(neighbors);
        }
        return null;
    }

    private List<State> getPath(State node) {
        List<State> path = new ArrayList<>();
        do {
            State nodeP = node.getParent();
            node.setParent(null);
            path.add(node);
            node = nodeP;
        } while (node != null);
        path.sort((o1, o2) -> o1.getStep().compareTo(o2.getStep()));
        return path;
    }


}
