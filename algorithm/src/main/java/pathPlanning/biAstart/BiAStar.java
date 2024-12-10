package pathPlanning.biAstart;

import java.util.*;

public class BiAStar {
    // 地图定义
    private static final int[][] GRID = {
            {0, 0, 0, 0, 0, 0},
            {0, 1, 0, 1, 0, 0},
            {1, 0, 1, 0, 0, 1},
            {0, 1, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 1},
            {0, 0, 0, 0, 0, 0}
    };

    // 八个方向移动
    private static final int[][] DIRECTIONS = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    // 欧几里得距离启发式函数
    private static double heuristic(Node a, Node b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    // 检查节点是否有效
    private static boolean isValidNode(int x, int y) {
        return x >= 0 && x < GRID.length &&
                y >= 0 && y < GRID[0].length &&
                GRID[x][y] == 0;
    }

    // 主寻路方法
    public static List<Node> findPath(Node start, Node goal) {
        PriorityQueue<Node> openForward = new PriorityQueue<>();
        PriorityQueue<Node> openBackward = new PriorityQueue<>();
        Set<Node> closedForward = new HashSet<>();
        Set<Node> closedBackward = new HashSet<>();

        // 初始化起点和终点
        start.g = 0;
        start.h = heuristic(start, goal);
        start.f = start.g + start.h;

        goal.g = 0;
        goal.h = heuristic(goal, start);
        goal.f = goal.g + goal.h;

        openForward.offer(start);
        openBackward.offer(goal);

        while (!openForward.isEmpty() && !openBackward.isEmpty()) {
            // 正向搜索
            Node currentForward = exploreDirection(openForward, closedForward, goal);
            if (currentForward == null) break;

            // 反向搜索
            Node currentBackward = exploreDirection(openBackward, closedBackward, start);
            if (currentBackward == null) break;

            // 检查是否找到连接点
            for (Node forward : closedForward) {
                for (Node backward : closedBackward) {
                    if (forward.equals(backward)) {
                        return reconstructPath(forward, backward);
                    }
                }
            }
        }

        return null; // 未找到路径
    }

    // 探索一个方向
    private static Node exploreDirection(PriorityQueue<Node> openList,
                                         Set<Node> closedList,
                                         Node goal) {
        if (openList.isEmpty()) return null;

        Node current = openList.poll();
        closedList.add(current);

        for (int[] dir : DIRECTIONS) {
            int newX = current.x + dir[0];
            int newY = current.y + dir[1];

            if (isValidNode(newX, newY)) {
                Node neighbor = new Node(newX, newY);

                // 避免重复探索
                if (closedList.contains(neighbor)) continue;

                double moveCost = (dir[0] != 0 && dir[1] != 0) ? 1.414 : 1.0;
                double tentativeG = current.g + moveCost;

                // 如果是新节点或发现更好的路径
                if (!openList.contains(neighbor)) {
                    neighbor.parent = current;
                    neighbor.g = tentativeG;
                    neighbor.h = heuristic(neighbor, goal);
                    neighbor.f = neighbor.g + neighbor.h;
                    openList.offer(neighbor);
                } else {
                    // 更新更优路径
                    Node existingNode = findNodeInQueue(openList, neighbor);
                    if (tentativeG < existingNode.g) {
                        existingNode.parent = current;
                        existingNode.g = tentativeG;
                        existingNode.f = existingNode.g + existingNode.h;
                    }
                }
            }
        }

        return current;
    }

    // 在队列中查找节点
    private static Node findNodeInQueue(PriorityQueue<Node> queue, Node node) {
        for (Node n : queue) {
            if (n.equals(node)) return n;
        }
        return null;
    }

    // 重建路径
    private static List<Node> reconstructPath(Node meetingPointForward, Node meetingPointBackward) {
        List<Node> path = new ArrayList<>();
        Node current;

        // 重建正向路径
        current = meetingPointForward;
        while (current != null) {
            path.add(0, current);
            current = current.parent;
        }

        // 重建反向路径（跳过重复的连接点）
        current = meetingPointBackward.parent;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }

        return path;
    }

    public static void main(String[] args) {
        System.out.println("----------------");
        System.out.println(
                "x  0, 1, 2, 3, 4, 5\n" +
                "0 {0, 0, 0, 0, 0, 0}\n" +
                "1 {0, 1, 0, 1, 0, 0}\n" +
                "2 {1, 0, 1, 0, 0, 1}\n" +
                "3 {0, 1, 1, 0, 1, 0}\n" +
                "4 {0, 0, 1, 0, 0, 0}\n" +
                "5 {0, 0, 0, 0, 0, 0}");
        Node start = new Node(0, 0);
        Node goal = new Node(5, 5);

        List<Node> path = findPath(start, goal);

        if (path != null) {
            System.out.println("找到路径:");
            for (Node node : path) {
                System.out.println("(" + node.x + ", " + node.y + ")");
            }
        } else {
            System.out.println("未找到路径");
        }
    }
}