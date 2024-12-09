package pathPlanning.dstar_cnblogs;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class DGraph {

    /**
     * 用于映射节点标识和节点对象
     */
    private Map<Integer, DNode> nodeMap;

    /**
     * 用于映射两个节点之间的移动代价
     */
    private Map<String, Integer> costMap;

    /**
     * 用于映射节点的邻居节点
     */
    private Map<DNode, Set<DNode>> neighborsMap;


    public DGraph(int[][] graph) {
        if (Objects.isNull(graph) || graph.length == 0 || graph.length != graph[0].length) {
            throw new IllegalArgumentException("图参数出错");
        }
        neighborsMap = new HashMap<>(graph.length);
        nodeMap = new HashMap<>(graph.length);
        costMap = new HashMap<>(graph.length);
        init(graph);
    }

    /**
     * 用于完成图的初始化工作
     *
     * @param graph 图模型的邻接矩阵
     */
    private void init(int[][] graph) {
        final Integer UNGO = Integer.MAX_VALUE;
        // 用于初始化所有节点
        for (int i = 0; i < graph.length; i++) {
            nodeMap.put(i, new DNode(i));
        }
        // 用于初始化邻接节点列表和节点间的移动代价
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if (Objects.equals(UNGO, graph[i][j]) || Objects.equals(i, j)) {
                    continue;
                }
                DNode x = nodeMap.get(i);
                DNode y = nodeMap.get(j);
                // 此处调转y与x之间的位置是因为有向图中是从x指向y的，但DStar算法是从目标点向起始点进行规划的，为此需要调转方向
                costMap.put(generateCostKey(y, x), graph[i][j]);
                neighborsMap.computeIfAbsent(y, obj -> new HashSet<>()).add(x);

                //设置一个反方向的边，将其值代价设置得很大。目的是为了兼容有向图中某些节点之间没有双向边,只有单向边，
                //在有向图只有单向边的情况下，会得不到最优解
                neighborsMap.computeIfAbsent(x,obj -> new HashSet<>()).add(y);
            }
        }
    }

    /**
     * 通过节点的唯一标识获取节点对象
     *
     * @param index 节点的唯一标识
     * @return 节点对象
     */
    public DNode getDNodeByIndex(int index) {
        return nodeMap.get(index);
    }

    /**
     * 通过x节点获取其对应的邻居节点
     *
     * @param x 查找邻居节点的相关节点
     * @return 邻居节点列表
     */
    public Set<DNode> getNeighbors(DNode x) {
        return neighborsMap.getOrDefault(x,new HashSet<>());
    }

    /**
     * 得到从x节点移动到y节点的移动代价
     *
     * @param x 起始点
     * @param y 目标点
     */
    public long getCost(DNode x, DNode y) {
        Integer val = costMap.get(generateCostKey(x, y));
        // 当不存在这样的路径的移动代价时，也就是不存在对应方向的路径，为此将其设置为最大值
        return Objects.isNull(val) ? Integer.MAX_VALUE : val;
    }

    /**
     * 修改节点x移动到节点y的移动代价
     *
     * @param x    起始点
     * @param y    目标点
     * @param cost 新的移动代价
     */
    public void setCost(DNode x, DNode y, int cost) {
        costMap.put(generateCostKey(x, y), cost);
    }

    /**
     * 用于生成存储两个节点的移动代价的键
     *
     * @param x 起始点
     * @param y 目标点
     * @return 从起始点移动到目标点的键
     */
    private String generateCostKey(DNode x, DNode y) {
        final String SEPARATOR = "~";
        return x.getIndex() + SEPARATOR + y.getIndex();
    }

}