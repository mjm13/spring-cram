package pathPlanning.dstar_cnblogs;

import lombok.Data;

/**
 * 图上的节点
 */
@Data
public class DNode {
    /**
     * 用于标识该节点
     */
    private int index;

    /**
     * 节点的h值
     */
    private long h;

    /**
     * 节点的k值
     */
    private long k;

    /**
     * 最短路径的回调节点
     */
    private DNode b;

    /**
     * 节点的状态
     */
    private STATE state;

    public DNode(int index) {
        this(index, STATE.NEW);
    }

    public DNode(int index, STATE state) {
        this.index = index;
        this.state = state;
    }

    @Override
    public int hashCode() {
        return this.index;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof DNode) {
            DNode node = (DNode) obj;
            return node.index == this.index;
        }
        return false;
    }

}
