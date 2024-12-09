package pathPlanning.dstart;

import cn.hutool.core.util.StrUtil;
import pathPlanning.dstart.model.Coord;
import pathPlanning.dstart.model.DStarNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * D* 算法实现
 * 这是一个动态路径规划算法，能够在发现新障碍物时重新规划路径
 */
public class DStar {
    // 算法参数
    private static final int MAX_ITERATIONS = 1000;  // 最大迭代次数
    private static final double EPSILON = 0.000001;  // 浮点数比较精度

    // 算法状态变量
    private List<Coord> coords;                      // 所有坐标点
    private Map<String, Coord> mapInfoMap;           // 坐标点映射表
    private PriorityQueue<DStarNode> openList;      // 开放列表
    private Map<String, DStarNode> nodes;           // 所有节点
    private double km;                              // 启发式修正值
    private Coord goal;                             // 目标点
    private Coord start;                            // 起始点

    /**
     * 构造函数
     */
    public DStar() {
        this.openList = new PriorityQueue<>();
        this.nodes = new HashMap<>();
        this.km = 0;
    }

    /**
     * 启动路径规划
     */
    public DStarNode start(List<Coord> coords, Coord start, Coord goal) {
        this.coords = coords;
        this.mapInfoMap = coords.stream().collect(
                Collectors.toMap(Coord::getId, o -> o, (o1, o2) -> o1)
        );
        this.start = start;
        this.goal = goal;

        initialize();
        computePath();
        return reconstructPath();
    }

    /**
     * 初始化算法
     */
    private void initialize() {
        openList.clear();
        nodes.clear();
        km = 0;

        // 初始化所有节点
        for (Coord coord : coords) {
            DStarNode node = new DStarNode(coord);
            node.setG(Double.POSITIVE_INFINITY);
            node.setRhs(Double.POSITIVE_INFINITY);
            nodes.put(coord.getId(), node);
        }

        // 初始化目标节点
        DStarNode goalNode = nodes.get(goal.getId());
        goalNode.setRhs(0);
        calculateKey(goalNode);
        System.out.println("initialize添加探测点: " + goalNode.toString());
        openList.add(goalNode);

        System.out.println("初始化完成 - 节点数量: " + nodes.size());
    }

    /**
     * 计算节点的关键值
     */
    private void calculateKey(DStarNode node) {
        double minG = Math.min(node.getG(), node.getRhs());
        node.getKey()[0] = minG + heuristic(node.getCoord(), start) + km;
        node.getKey()[1] = minG;
    }

    /**
     * 计算启发式值
     */
    private double heuristic(Coord a, Coord b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    /**
     * 计算路径
     */
    private void computePath() {
        int iterations = 0;
        DStarNode startNode = nodes.get(start.getId());

        while (!openList.isEmpty() && iterations < MAX_ITERATIONS) {
            iterations++;
            DStarNode u = openList.peek();

            if (u == null) break;

            double kOld = u.getKey()[0];
            calculateKey(u);
            double kNew = u.getKey()[0];

            // 打印当前处理的节点信息
            System.out.println("探测节点: " + u.toString());

            if (kOld > kNew) {
                // key值变小了，重新插入
                openList.poll();
                System.out.println("computePath添加探测节点: " + u.toString());
                openList.add(u);
                continue;
            }

            // 如果起点的rhs值已经收敛，且openList中最小key值大于起点的key值，说明找到最优路径
            if (u.getKey()[0] >= calculateTopKey(startNode) && 
                Math.abs(startNode.getG() - startNode.getRhs()) <= EPSILON) {
                System.out.println("找到路径！起点g值=" + startNode.getG() + 
                    " rhs值=" + startNode.getRhs());
                break;
            }

            openList.poll();

            if (u.getG() > u.getRhs()) {
                // 过度估计情况
                u.setG(u.getRhs());
                for (DStarNode s : getNeighbors(u)) {
                    updateVertex(s);
                }
            } else {
                // 低估情况
                double oldG = u.getG();
                u.setG(Double.POSITIVE_INFINITY);
                updateVertex(u);
                for (DStarNode s : getNeighbors(u)) {
                    updateVertex(s);
                }
            }
        }

        if (iterations >= MAX_ITERATIONS) {
            System.out.println("警告: 达到最大迭代次数限制");
        }
    }

    /**
     * 计算节点的顶部键值
     */
    private double calculateTopKey(DStarNode node) {
        if (node == null) return Double.POSITIVE_INFINITY;
        calculateKey(node);
        return node.getKey()[0];
    }

    /**
     * 获取相邻节点
     */
    private List<DStarNode> getNeighbors(DStarNode node) {
        List<DStarNode> neighbors = new ArrayList<>();
        Coord info = node.getCoord();

        // 检查四个方向的相邻节点
        if (info.isDown()) addNeighbor(neighbors, info.getX(), info.getY() + 1);
        if (info.isUp()) addNeighbor(neighbors, info.getX(), info.getY() - 1);
        if (info.isRight()) addNeighbor(neighbors, info.getX() + 1, info.getY());
        if (info.isLeft()) addNeighbor(neighbors, info.getX() - 1, info.getY());

        return neighbors;
    }

    /**
     * 添加相邻节点
     */
    private void addNeighbor(List<DStarNode> neighbors, int x, int y) {
        String id = "01" + StrUtil.padPre(String.valueOf(x), 4, "0") +
                StrUtil.padPre(String.valueOf(y), 4, "0");
        DStarNode neighbor = nodes.get(id);
        if (neighbor != null) {
            neighbors.add(neighbor);
        }
    }

    /**
     * 更新节点
     */
    private void updateVertex(DStarNode node) {
        if (!node.getCoord().equals(goal)) {
            double minRhs = Double.POSITIVE_INFINITY;
            DStarNode bestSuccessor = null;

            // 遍历所有邻居节点，找到最小的rhs值
            for (DStarNode succ : getNeighbors(node)) {
                double cost = getCost(node, succ);  // 从当前节点到邻居节点的代价
                double tempRhs = succ.getG() + cost;
                if (tempRhs < minRhs) {
                    minRhs = tempRhs;
                    bestSuccessor = succ;
                }
            }

            node.setRhs(minRhs);
            node.setParent(bestSuccessor);
        }

        // 如果节点在openList中，先移除
        openList.remove(node);

        // 如果g值和rhs值不一致，需要重新加入openList
        if (Math.abs(node.getG() - node.getRhs()) > EPSILON) {
            calculateKey(node);
            System.out.println("updateVertex添加探测点: " + node.toString());
            openList.add(node);
        }
    }

    // 添加一个新的方法来计算两个节点之间的代价
    private double getCost(DStarNode from, DStarNode to) {
        Coord fromCoord = from.getCoord();
        Coord toCoord = to.getCoord();
        
        // 检查是否相邻
        int dx = Math.abs(fromCoord.getX() - toCoord.getX());
        int dy = Math.abs(fromCoord.getY() - toCoord.getY());
        if (dx + dy != 1) {
            return Double.POSITIVE_INFINITY;
        }
        
        // 检查方向是否允许移动
        if (fromCoord.getX() < toCoord.getX() && !fromCoord.isRight()) return Double.POSITIVE_INFINITY;
        if (fromCoord.getX() > toCoord.getX() && !fromCoord.isLeft()) return Double.POSITIVE_INFINITY;
        if (fromCoord.getY() < toCoord.getY() && !fromCoord.isDown()) return Double.POSITIVE_INFINITY;
        if (fromCoord.getY() > toCoord.getY() && !fromCoord.isUp()) return Double.POSITIVE_INFINITY;

        // 计算移动代价
        double cost = 1.0;

        // 优先选择与目标点同向的移动
        if ((toCoord.getX() != fromCoord.getX() && Math.abs(toCoord.getX() - goal.getX()) < Math.abs(fromCoord.getX() - goal.getX())) ||
            (toCoord.getY() != fromCoord.getY() && Math.abs(toCoord.getY() - goal.getY()) < Math.abs(fromCoord.getY() - goal.getY()))) {
            cost *= 0.9;  // 朝向目标方向的移动给予奖励
        }

        // 检查是否需要转向
        if (from.getParent() != null) {
            Coord parentCoord = from.getParent().getCoord();
            boolean currentMoveIsVertical = (toCoord.getX() == fromCoord.getX());
            boolean previousMoveWasVertical = (fromCoord.getX() == parentCoord.getX());
            
            if (currentMoveIsVertical != previousMoveWasVertical) {
                cost *= 1.5;  // 转向惩罚
            }
        }
        
        return cost;
    }

    /**
     * 重建路径
     */
    private DStarNode reconstructPath() {
        DStarNode startNode = nodes.get(start.getId());
        DStarNode goalNode = nodes.get(goal.getId());

        if (startNode == null || goalNode == null) {
            System.out.println("起点或终点无效");
            return null;
        }

        if (Math.abs(startNode.getG() - startNode.getRhs()) > EPSILON) {
            System.out.println("起点的g值未收敛，无法找到有效路径");
            return null;
        }

        // 从起点开始，沿着最小代价方向到终点
        DStarNode current = startNode;
        List<DStarNode> path = new ArrayList<>();
        path.add(current);

        while (current != null && !current.getCoord().equals(goal)) {
            // 找到代价最小的邻居节点
            DStarNode bestNeighbor = null;
            double minCost = Double.POSITIVE_INFINITY;

            for (DStarNode neighbor : getNeighbors(current)) {
                double cost = getCost(current, neighbor);
                if (cost < Double.POSITIVE_INFINITY) {  // 确保可以移动到这个邻居
                    double totalCost = neighbor.getRhs() + cost;  // 使用rhs值而不是g值
                    if (totalCost < minCost) {
                        minCost = totalCost;
                        bestNeighbor = neighbor;
                    }
                }
            }

            if (bestNeighbor == null) {
                System.out.println("无法找到下一个节点");
                return null;
            }

            current = bestNeighbor;
            path.add(current);

            // 防止无限循环
            if (path.size() > nodes.size()) {
                System.out.println("路径重建过程中检测到循环");
                return null;
            }
        }

        // 设置父节点关系
        for (int i = 0; i < path.size() - 1; i++) {
            path.get(i).setParent(path.get(i + 1));
        }

        // 打印路径
        System.out.println("找到路径：");
        for (DStarNode node : path) {
            System.out.println(node.getCoord());
        }

        return path.get(0);  // 返回起点节点
    }

    /**
     * 更新地图
     */
    public void updateMap(Coord changedCoord) {
        km += heuristic(start, changedCoord);

        DStarNode node = nodes.get(changedCoord.getId());
        if (node != null) {
            updateVertex(node);
            for (DStarNode neighbor : getNeighbors(node)) {
                updateVertex(neighbor);
            }
        }
    }

    /**
     * 获取指定坐标的节点
     */
    public DStarNode getNode(Coord coord) {
        return nodes.get(coord.getId());
    }

    /**
     * 打印调试信息
     */
    public void printDebugInfo() {
        System.out.println("\n=== 调试信息 ===");
        System.out.println("OpenList大小: " + openList.size());
        System.out.println("节点总数: " + nodes.size());
        System.out.println("起点: " + start.getId());
        System.out.println("终点: " + goal.getId());

        // 打印所有节点的g值和rhs值
        nodes.values().forEach(node -> {
            System.out.printf("节点 %s: g=%.2f, rhs=%.2f%n",
                    node.getCoord().getId(), node.getG(), node.getRhs());
        });
    }
    
    
}