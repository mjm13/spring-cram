package pathPlanning.cbs;

import pathPlanning.cbs.model.CbsAgent;
import pathPlanning.cbs.model.Environment;
import pathPlanning.cbs.model.Location;
import pathPlanning.cbs.model.State;

import java.util.*;

public class CbsAStar {
    //缓存地图信息
    private Environment environment;
    //待遍历节点，优先队列(升序);
    private Queue<State> openList = new PriorityQueue<State>();
    //已遍历节点
    private List<State> closeList = new ArrayList<State>();
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
            if(closeList.contains(current)){
                continue;
            }
            closeList.add(current);
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
