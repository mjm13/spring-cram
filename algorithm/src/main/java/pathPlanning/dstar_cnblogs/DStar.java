package pathPlanning.dstar_cnblogs;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * @author 学徒
 */
@Slf4j
public class DStar {
    /**
     * 开放列表
     */
    private Queue<DNode> openList;

    /**
     * 地图
     */
    private DGraph graph;

    public DGraph getGraph() {
        return graph;
    }

    public DStar(int[][] graph) {
        this(new DGraph(graph));
    }

    private DStar(DGraph graph) {
        this.graph = graph;
        openList = new PriorityQueue<>(Comparator.comparingLong(DNode::getK));
    }

    /**
     * 用于修改节点x的状态及其对应的h和k值
     *
     * @param x   节点x
     * @param val 新的h值
     */
    private void insert(DNode x, long val) {
        if (Objects.equals(x.getState(), STATE.NEW)) {
            x.setH(val);
            x.setK(val);
        } else if (Objects.equals(x.getState(), STATE.OPEN)) {
            openList.remove(x);
            x.setK(Math.min(x.getK(), val));
        } else if (Objects.equals(x.getState(), STATE.CLOSED)) {
            x.setK(Math.min(x.getH(), val));
            x.setH(val);
        }
        x.setState(STATE.OPEN);
        openList.add(x);
    }

    /**
     * 用于处理对应的节点
     *
     * @return openList中所有节点的最小k值，当openList为空时，返回-1;
     */
    private long process_state() {
        DNode x = openList.poll();
        if (Objects.isNull(x)) {
            return -1;
        }

        x.setState(STATE.CLOSED);

        // 当h值大于k值时，表示当前该节点处于h值被修改为较大的状态(raise状态)
        // 为此查找邻居节点来得到减低自身的h值
        if (x.getK() < x.getH()) {
            for (DNode y : graph.getNeighbors(x)) {
                if (y.getH() < x.getK() && x.getH() > y.getH() + graph.getCost(y, x)) {
                    x.setB(y);
                    x.setH(y.getH() + graph.getCost(y, x));
                }
            }
        }

        // 该过程类似于dijikstra，用来传播信息当前节点h值变化的信息和降低邻居节点的h值
        if (Objects.equals(x.getK(), x.getH())) {
            for (DNode y : graph.getNeighbors(x)) {
                if (Objects.equals(y.getState(), STATE.NEW)
                        || (Objects.equals(y.getB(), x) && !Objects.equals(y.getH(), x.getH() + graph.getCost(x, y)))
                        || (!Objects.equals(y.getB(), x) && y.getH() > x.getH() + graph.getCost(x, y))) {
                    y.setB(x);
                    insert(y, x.getH() + graph.getCost(x, y));
                }
            }
        } else {
            for (DNode y : graph.getNeighbors(x)) {
                if (Objects.equals(y.getState(), STATE.NEW)
                        || (Objects.equals(y.getB(), x) && !Objects.equals(y.getH(), x.getH() + graph.getCost(x, y)))) {
                    y.setB(x);
                    insert(y, x.getH() + graph.getCost(x, y));
                } else {
                    if (!Objects.equals(y.getB(), x) && y.getH() > x.getH() + graph.getCost(x, y)) {
                        insert(x, x.getH());
                    } else {
                        if (!Objects.equals(y.getB(), x)
                                && x.getH() > y.getH() + graph.getCost(y, x)
                                && Objects.equals(y.getState(), STATE.CLOSED)
                                && y.getH() > x.getK()) {
                            insert(y, y.getH());
                        }
                    }
                }
            }
        }

        return Objects.isNull(x = openList.peek()) ? -1 : x.getK();
    }

    /**
     * 用于修改某两个节点之间的移动代价，且y节点为x节点的上一节点
     *
     * @param xIndex 节点1
     * @param xIndex 节点2
     * @param val    节点的移动代价
     * @return 当前新的最小k值
     */
    public long modify_cost(int xIndex, int yIndex, int val) {
        DNode x = graph.getDNodeByIndex(xIndex);
        DNode y = graph.getDNodeByIndex(yIndex);
        // 此处调转y与x之间的位置是因为有向图中是从x指向y的，但DStar算法是从目标点向起始点进行规划的，为此需要调转方向
        graph.setCost(y, x, val);

        if (Objects.equals(y.getState(), STATE.CLOSED)) {
            insert(y, val);
        }
        DNode node = null;
        return Objects.isNull(node = openList.peek()) ? -1 : node.getK();
    }

    /**
     * @param y 需要进行重新进行路径规划的点，就是可能是障碍点，或者移除了障碍之后的点
     * @return 返回值大于-1表示调整好路径，等于-1 表示没有路径可以从当前点到达目标点
     */
    private long replan(DNode y) {
        long result = -1;
        while (true) {
            if ((result = process_state()) >= y.getH() || Objects.equals(result, -1L)) {
                break;
            }
        }
        return -1;
    }

    /**
     * 执行从start点到end点的最短路径执行过程
     *
     * @param start 起始节点
     * @param end   目标节点
     */
    public void run(int start, int end) {
        insert(graph.getDNodeByIndex(end), 0);
        // 执行路径规划
        long min_k = -1;
        while (true) {
            min_k = process_state();
            if (Objects.equals(graph.getDNodeByIndex(start).getState(), STATE.CLOSED)
                    || Objects.equals(min_k, -1L)) {
                break;
            }
        }
        // 不能找到从起始点到目标点的最短路径
        if (Objects.equals(min_k, -1)) {
            log.info("无法找到执行路径");
            return;
        }

        // 用于调试使用
        printPath(start, end);

        DNode current = graph.getDNodeByIndex(start);
        DNode next = current.getB();
        while (true) {
            while (Objects.nonNull(next) && Objects.equals(next.getState(), STATE.CLOSED)) {
                gogogo(next);
                current = next;
                next = next.getB();
            }
            if (Objects.isNull(next)) {
                break;
            }
            // 当前节点节点的上层节点不能移动了，因为某种原因(可能是障碍物)被人修改了执行代价,也就是被调用过该节点的modify_cost(current,current.b,newH)函数
            min_k = replan(current);

            // 重新规划路径后，无法找到一条有效的路
            if (Objects.equals(min_k, -1)) {
                log.info("无法找到有效执行路径");
                return;
            }

            log.info("重新规划后路径");
            // 用于调试使用
            printPath(current.getIndex(), end);
            next = current.getB();
        }
    }

    /**
     * 用于执行移动到指定节点操作
     *
     * @param node 需要移动到的对应节点
     */
    private void gogogo(DNode node) {
        log.info("移动到节点：" + node.getIndex());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    /**
     * 用于打印路径，调试使用
     *
     * @param start 起始点
     * @param end   目标点
     */
    private void printPath(int start, int end) {
        DNode s = graph.getDNodeByIndex(start);
        while (s.getIndex() != end) {
            log.info(s.getIndex() + "->");
            s = s.getB();
        }
        log.info(String.valueOf(end));
    }
}