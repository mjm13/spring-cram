package pathPlanning.dstart;

import java.util.*;

public class DStarLite3D {
    // 3D Node Class
    static class Node {
        int x, y, z;
        double g, rhs;
        boolean isObstacle;

        public Node(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.g = Double.POSITIVE_INFINITY;
            this.rhs = Double.POSITIVE_INFINITY;
            this.isObstacle = false;
        }

        // Override equals and hashCode for Node comparison
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node other = (Node) obj;
            return x == other.x && y == other.y && z == other.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }

    // Priority Queue with Key
    static class PriorityNode implements Comparable<PriorityNode> {
        Node node;
        double[] key;

        public PriorityNode(Node node, double[] key) {
            this.node = node;
            this.key = key;
        }

        @Override
        public int compareTo(PriorityNode o) {
            if (this.key == null || o.key == null) {
                throw new NullPointerException("Cannot compare null keys");
            }
            int len = Math.min(this.key.length, o.key.length);
            for (int i = 0; i < len; i++) {
                int comp = Double.compare(this.key[i], o.key[i]);
                if (comp != 0) {
                    return comp;
                }
            }
            return Integer.compare(this.key.length, o.key.length);
        }
    }

    private final int[][] DIRECTIONS = {
            {1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0},
            {0, 0, 1}, {0, 0, -1}  // 6 possible 3D directions
    };

    private final Map<Node, Node> cameFrom = new HashMap<>();
    private final PriorityQueue<PriorityNode> openList = new PriorityQueue<>();
    private final Map<Node, Double> gMap = new HashMap<>();
    private final Map<Node, Double> rhsMap = new HashMap<>();

    private Node start, goal;

    public DStarLite3D(Node start, Node goal) {
        this.start = start;
        this.goal = goal;
        start.rhs = 0; // Goal has rhs = 0
        openList.add(new PriorityNode(start, calculateKey(start)));
    }

    // Calculate Key (primary and secondary keys)
    private double[] calculateKey(Node node) {
        double min = Math.min(node.g, node.rhs);
        return new double[]{min + heuristic(node, start), min};
    }

    // Heuristic function (Euclidean distance)
    private double heuristic(Node a, Node b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2) + Math.pow(a.z - b.z, 2));
    }

    // Update Vertex
    private void updateVertex(Node node) {
        if (!node.equals(goal)) {
            node.rhs = Double.POSITIVE_INFINITY;
            for (Node neighbor : getNeighbors(node)) {
                if (!neighbor.isObstacle) {
                    node.rhs = Math.min(node.rhs, gMap.getOrDefault(neighbor, Double.POSITIVE_INFINITY) + 1);
                }
            }
        }

        openList.remove(new PriorityNode(node, calculateKey(node)));
        if (node.g != node.rhs) {
            openList.add(new PriorityNode(node, calculateKey(node)));
        }
    }

    // Get neighbors in the 3D grid
    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        for (int[] dir : DIRECTIONS) {
            neighbors.add(new Node(node.x + dir[0], node.y + dir[1], node.z + dir[2]));
        }
        return neighbors;
    }

    // Compute shortest path
    public List<Node> computeShortestPath() {
        while (!openList.isEmpty() &&
                (openList.peek().key[0] < calculateKey(start)[0] ||
                        start.rhs != start.g)) {
            PriorityNode current = openList.poll();
            Node u = current.node;

            if (u.g > u.rhs) {
                u.g = u.rhs;
                for (Node neighbor : getNeighbors(u)) {
                    updateVertex(neighbor);
                }
            } else {
                u.g = Double.POSITIVE_INFINITY;
                updateVertex(u);
                for (Node neighbor : getNeighbors(u)) {
                    updateVertex(neighbor);
                }
            }
        }

        return reconstructPath();
    }

    // Reconstruct the path
    private List<Node> reconstructPath() {
        List<Node> path = new ArrayList<>();
        Node current = start;
        while (!current.equals(goal)) {
            path.add(current);
            current = cameFrom.getOrDefault(current, goal);
        }
        path.add(goal);
        return path;
    }

    public static void main(String[] args) {
        // Create a 3D grid and nodes
        Node start = new Node(0, 0, 0);
        Node goal = new Node(4, 4, 4);

        DStarLite3D dStarLite = new DStarLite3D(start, goal);

        // Add obstacles
        Node obstacle = new Node(2, 2, 2);
        obstacle.isObstacle = true;

        // Compute the shortest path
        List<Node> path = dStarLite.computeShortestPath();

        // Print the path
        for (Node node : path) {
            System.out.println("Node: (" + node.x + ", " + node.y + ", " + node.z + ")");
        }
    }
}
