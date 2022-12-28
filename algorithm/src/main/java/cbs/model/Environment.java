package cbs.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.*;

@Data
public class Environment {
    //坐标集合
    public Map<String, Location> dimension;
    //障碍物
    public Set<Location> obstacles;
    //移动障碍物
    public Set<State> moveObstacles;
    //冲突信息
    public Constraint constraints = new Constraint();
    //整体冲突
    public Map<String,Constraint> constraintDict = new HashMap<>();

    public Set<State> getNeighbors(State state, Location end) {
        Set<State> neighbors = new HashSet<>();
        int nextStep = state.getStep() + 1;
        Location location = state.getLocation();
        //等待节点
        State n = new State(nextStep, state);
        n.setEvaluateDistance(calcH(end,location)+nextStep);
        n.setActualDistance(n.getActualDistance()+nextStep);
        if (stateValid(n)) {
            neighbors.add(n);
        }
        addLocation(nextStep, location.getX(), location.getY() - 1, end, state, neighbors);
        addLocation(nextStep, location.getX(), location.getY() + 1, end, state, neighbors);
        addLocation(nextStep, location.getX() - 1, location.getY(), end, state, neighbors);
        addLocation(nextStep, location.getX() + 1, location.getY(), end, state, neighbors);
        return neighbors;
    }

    private void addLocation(int nextStep, int x, int y, Location end, State state, Set<State> neighbors) {
        Location location = getLocation(x, y );
        if (location != null) {
            State n = new State(nextStep, state, location, 1, calcH(end, location));
            if (stateValid(n) && transitionValid(state, n)) {
                neighbors.add(n);
            }
        }
    }

    private Location getLocation(int x, int y) {
        return dimension.get("01" + StrUtil.padPre(String.valueOf(x), 4, "0") + StrUtil.padPre(String.valueOf(y), 4, "0"));
    }

    public Location getLocation(String id) {
        return dimension.get(id);
    }

    /**
     * 1.判断节点是否有效
     * 2.判断节点是否为障碍物
     * 3.判断节点是否为顶点冲突
     *
     * @param state
     * @return boolean
     * @Description
     * @Author MeiJM
     * @Date 2022/12/26
     **/
    public boolean stateValid(State state) {
        VertexConstraint vertexConstraint = new VertexConstraint(state);
        return !obstacles.contains(state.getLocation())&&
                !constraints.getVertexConstraints().contains(vertexConstraint);
    }

    /**
     * 边缘冲突检查
     *
     * @param state1
     * @param state2
     * @return
     */
    public boolean transitionValid(State state1, State state2) {
        EdgeConstraint edgeConstraint = new EdgeConstraint(state1, state2);
        return !constraints.getEdgeConstraints().contains(edgeConstraint);
    }

    /**
     * 计算H的估值：“曼哈顿”法，坐标分别取差值相加
     */
    private int calcH(Location end, Location coord) {
        return (Math.abs(end.getX() - coord.getX()) + Math.abs(end.getY() - coord.getY()));
    }
}
