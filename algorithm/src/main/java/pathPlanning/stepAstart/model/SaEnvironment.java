package pathPlanning.stepAstart.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.*;

/**
 * @Description 运行环境
 * 获取临近节点，障碍物，移动障碍物
 * @Author MeiJM
 * @Date 2022/12/29
 **/
@Data
public class SaEnvironment {
    //坐标集合
    public Map<String, SaLocation> dimension=new HashMap<>();
    //障碍物
    public Set<SaLocation> obstacles = new LinkedHashSet<>();

    //移动障碍物
    public Set<SaProbeNode> moveObstacles = new LinkedHashSet<>();

    public Set<SaProbeNode> getNeighbors(SaProbeNode preNode, SaLocation end) {
        Set<SaProbeNode> neighbors = new HashSet<>();
        int nextStep = preNode.getStep() + 1;
        SaLocation saLocation = preNode.getSaLocation();
        //等待节点
        SaProbeNode nextNode = new SaProbeNode(nextStep, preNode);
        nextNode.setH(calcH(end, saLocation) + nextStep);
        if (stateValid(nextNode)) {
            neighbors.add(nextNode);
        }
        addLocation(nextStep, saLocation.getX(), saLocation.getY() - 1, end, preNode, neighbors);
        addLocation(nextStep, saLocation.getX(), saLocation.getY() + 1, end, preNode, neighbors);
        addLocation(nextStep, saLocation.getX() - 1, saLocation.getY(), end, preNode, neighbors);
        addLocation(nextStep, saLocation.getX() + 1, saLocation.getY(), end, preNode, neighbors);
        return neighbors;
    }

    private void addLocation(int nextStep, int x, int y, SaLocation end, SaProbeNode preNode, Set<SaProbeNode> neighbors) {
        SaDirection preDirection =  preNode.getSaDirection();
        SaLocation preLocation = preNode.getSaLocation();
        SaLocation nexLocation = getLocation(x, y);
        if (nexLocation==null) {
            return;
        }
        SaDirection nexDirection = null;
        int g = 0;
        if (preDirection.equals(SaDirection.X) && preLocation.getX() != nexLocation.getX()) {
            g+=2;
            nexDirection = SaDirection.Y;
        }else if(preDirection.equals(SaDirection.Y) && preLocation.getY() != nexLocation.getY()){
            g+=2;
            nexDirection = SaDirection.X;
        }else {
            nexDirection = preDirection;
        }

        if (nexLocation != null) {
            SaProbeNode n = new SaProbeNode(nextStep, preNode, nexLocation, calcH(end, nexLocation),g );
            n.setSaDirection(nexDirection);
            if (stateValid(n)) {
                neighbors.add(n);
            }
        }
    }

    private SaLocation getLocation(int x, int y) {
        return dimension.get(StrUtil.padPre(String.valueOf(x), 4, "0") + StrUtil.padPre(String.valueOf(y), 4, "0"));
    }

    public SaLocation getLocation(String id) {
        return dimension.get(id);
    }

    /**
     * 1.判断节点是否有效
     * 2.判断节点是否为障碍物
     * 3.判断节点是否为顶点冲突
     *
     * @param node
     * @return boolean
     * @Description
     * @Author MeiJM
     * @Date 2022/12/26
     **/
    public boolean stateValid(SaProbeNode node) {
        return !obstacles.contains(node.getSaLocation())&& !moveObstacles.contains(node);
    }


    /**
     * 计算H的估值：“曼哈顿”法，坐标分别取差值相加
     */
    private int calcH(SaLocation end, SaLocation coord) {
        return (Math.abs(end.getX() - coord.getX()) + Math.abs(end.getY() - coord.getY()));
    }
}
