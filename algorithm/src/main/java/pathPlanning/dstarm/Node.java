package pathPlanning.dstarm;

import lombok.Data;

@Data
public class Node implements Comparable<Node> {
    public Node(Coord coord) {
        this.coord = coord;
    }
    // 坐标信息
    private Coord coord;
    // 实际代价
    private double g = Double.POSITIVE_INFINITY;
    // 和相邻节点计算的预估代价
    private double rhs= Double.POSITIVE_INFINITY;
    
    /**
     * 探测优先级-总代价
     * 值=min(g, rhs) + h(s, sstart) + km
     * min(g, rhs): 取g值和rhs值中的较小值
     * h(s, sstart): 从当前节点到起点的启发式估计值
     * km 路径代价修正值  
     */
    private double totalCost;
    /**
     * 探测优先级-局部最小代价
     * 值=min(g, rhs)
     */
    private double localCost;
    // 父节点
    private Node parent;       

    // 计算cost值，注意这里的start是起点
    public void calculateKeys(Coord start) {
        double minValue = Math.min(g, rhs);
        // h(s, sstart): 从当前节点到起点的启发式估计
        totalCost = minValue + heuristic(start) ;
        localCost = minValue;
    }

    // 启发式函数计算到起点的估计距离
    private double heuristic(Coord start) {
        return Math.abs(coord.getX() - start.getX()) 
             + Math.abs(coord.getY() - start.getY());
    }     

    @Override
    public int compareTo(Node o) {
        int result = Double.compare(totalCost, o.getTotalCost());
        if (result != 0) {
            return result;
        }
        return Double.compare(localCost, o.getLocalCost());
    }
}
