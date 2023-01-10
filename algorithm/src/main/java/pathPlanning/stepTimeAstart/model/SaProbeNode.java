package pathPlanning.stepTimeAstart.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description 探测节点
 * @Author MeiJM
 * @Date 2022/12/29
 **/
@Getter
@Setter
@ToString(exclude = {"parent","saDirection"})
@EqualsAndHashCode(exclude = {"parent", "h", "g"})
public class SaProbeNode implements Comparable<SaProbeNode> {
    private SaProbeNode parent;
    //执行方向
    private SaDirection saDirection;
    //移动步骤
    private Integer step;
    //探测坐标
    private SaLocation saLocation;
    //移动代价
    private int g;
    //预估距离
    private int h;

    public SaProbeNode() {

    }

    public SaProbeNode(Integer step, SaProbeNode waitNode) {
        this.step = step;
        this.parent = waitNode;
        this.saLocation = waitNode.getSaLocation();
        this.saDirection = waitNode.getSaDirection();
        this.h = waitNode.getH()+10;
        this.g = waitNode.getG() ;
    }

    public SaProbeNode(Integer step, SaProbeNode parent, SaLocation saLocation, int h, int g) {
        this.step = step;
        this.parent = parent;
        this.saLocation = saLocation;
        this.h = h;
        this.g = g;
    }

    @Override
    public int compareTo(SaProbeNode o) {
        if (o == null) return -1;
        if (g + h > o.getG() + o.getH()) {
            return 1;
        } else if (g + h < o.getG() + o.getH()) {
            return -1;
        }
        return 0;
    }
}
