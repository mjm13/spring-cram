package pathPlanning.dstarm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class DStarM {
    private Map<String, Node> nodes = new HashMap<>();
    private PriorityQueue<Node> openList = new PriorityQueue<>();
    // 设置最大步数和代价常量
    private int maxSteps = 80000;
    private double C1 = 1.0;
    private double M_SQRT2 = Math.sqrt(2.0);

    private double km = 0d;
    private Coord start;
    private Coord goal;
    private Coord current;

    public DStarM(Coord start, Coord goal, List<Coord> coords) {
        this.start = start;
        this.goal = goal;
        for (Coord coord : coords) {
            Node node = new Node(coord);
            nodes.put(coord.getId(), node);
        }
    }


}
