package pathPlanning.stepTimeAstart;

import lombok.extern.slf4j.Slf4j;
import pathPlanning.stepTimeAstart.model.*;

import java.util.*;

/**
 * @Description 计算步骤的A* 当移动障碍物存在时可计算等待的
 * @Author MeiJM
 * @Date 2022/12/29
 **/
@Slf4j
public class StepAstart {
    //待遍历节点，优先队列(升序);
    private Queue<SaProbeNode> openList = new PriorityQueue<SaProbeNode>();
    //已遍历节点
    private List<SaProbeNode> closeList = new ArrayList<SaProbeNode>();
    //终点
    private SaLocation end;

    public List<SaProbeNode> searchPath(SaAgent saAgent, SaEnvironment environment) {
        this.end = environment.getLocation(saAgent.getGoal());
        // 清空历史记录
        this.openList.clear();
        this.closeList.clear();
        SaProbeNode node = new SaProbeNode();
        node.setSaDirection(saAgent.getDirection());
        node.setStep(0);
        node.setSaLocation(environment.getLocation(saAgent.getStart()));
        node.setParent(null);
        // 初始化开始位置
        openList.add(node);
        while (!openList.isEmpty()) {
            log.info("开始探测新节点:{}",openList);
            SaProbeNode current = openList.poll();
            if(closeList.contains(current)){
                continue;
            }
            closeList.add(current);
            if (end.equals(current.getSaLocation())) {
                return getPath(current);
            }
            Set<SaProbeNode> neighbors =environment.getNeighbors(current,end);
            openList.addAll(neighbors);
        }
        return null;
    }

    private List<SaProbeNode> getPath(SaProbeNode node) {
        List<SaProbeNode> path = new ArrayList<>();
        do {
            SaProbeNode nodeP = node.getParent();
            node.setParent(null);
            path.add(node);
            node = nodeP;
        } while (node != null);
        path.sort((o1, o2) -> o1.getStep().compareTo(o2.getStep()));
        return path;
    }


}
