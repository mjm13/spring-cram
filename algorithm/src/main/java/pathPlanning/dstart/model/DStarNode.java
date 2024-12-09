package pathPlanning.dstart.model;

/**
 * D*算法节点类
 */
public class DStarNode implements Comparable<DStarNode> {
    private final Coord coord;           // 坐标信息
    private double g;                    // g值
    private double rhs;                  // rhs值
    private final double[] key;          // 关键值
    private DStarNode parent;            // 父节点

    public DStarNode(Coord coord) {
        this.coord = coord;
        this.g = Double.POSITIVE_INFINITY;
        this.rhs = Double.POSITIVE_INFINITY;
        this.key = new double[2];
        this.parent = null;
    }

    public Coord getCoord() {
        return coord;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getRhs() {
        return rhs;
    }

    public void setRhs(double rhs) {
        this.rhs = rhs;
    }

    public double[] getKey() {
        return key;
    }

    public DStarNode getParent() {
        return parent;
    }

    public void setParent(DStarNode parent) {
        this.parent = parent;
    }

    @Override
    public int compareTo(DStarNode other) {
        // 比较key[0]
        if (this.key[0] < other.key[0]) return -1;
        if (this.key[0] > other.key[0]) return 1;
        
        // 如果key[0]相等，比较key[1]
        return Double.compare(this.key[1], other.key[1]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DStarNode dStarNode = (DStarNode) o;
        return coord.equals(dStarNode.coord);
    }

    @Override
    public int hashCode() {
        return coord.hashCode();
    }

    @Override
    public String toString() {
        return coord.getId()+"-g:"+g+"-rhs:"+rhs+"-key:["+key[0]+","+key[1]+"]";
    }
}