# A* 算法介绍

A*算法是一种常用的路径规划算法,它可以找到从起点到终点的最短路径
与Dijkstra算法的核心区别是增加了启发算法，提升搜索速度

# 说明

## 数据对象

* 点位信息：记录点位坐标，坐标属性等(此处可扩展用于在复杂环境中进行路径规划)
* 地图信息：使用Map或二维数组等结构存储地图每个点位信息
* OpenList：来存储待检查的节点,需要支持快速查找和删除操作,可以使用优先队列
* ClosedList：来存储已检查过的节点,使用Set存储
* 探测节点：类似链表结构存储上一节点以及当前节点，用于回溯路径

## 计算过程
- 将起点放入OpenList和ClosedList
- 从OpenList中取出f值最小的节点current
- 如果current是终点,返回路径
- 将current加入ClosedList
- 将current的相邻节点计算g值、h值和f值并加入OpenList(如果不在ClosedList中)
- 重复上述步骤直到找到终点


## 代码

* 节点信息

```java

@Data
public class Coord {
    //坐标集合
    private String id;
    private Integer x;
    private Integer y;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
}
```

> 点位中记录了是否可关联上下左右，便于节点探测
> id为x，y拼接结果

* 探测节点

```java

@Data
@ToString(callSuper = false)
public class Node implements Comparable<Node> {
    /**
     * 上一步，用于回溯路径
     */
    private Node parent;
    /**
     * 位置信息
     */
    private Coord coord;
    /**
     * H 代价距离 到目标位置代价
     */
    private int evaluateDistance;
    /**
     * G 实际距离 移动一格实际代价 可计算地形变化
     */
    private int actualDistance;

    @Override
    public int compareTo(Node o) {
        if (o == null) return -1;
        if (actualDistance + evaluateDistance > o.getActualDistance() + o.getEvaluateDistance())
            return 1;
        else if (actualDistance + evaluateDistance < o.getActualDistance() + o.getEvaluateDistance()) return -1;
        return 0;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
```

> 实现Comparable接口通过启发函数+优先级队列实现优先探测

* 算法实现

```Java
public class AStar {
    private Map<String, Coord> mapInfoMap;
    private final Queue<Node> openList = new PriorityQueue<Node>(); // 优先队列(升序);
    private final List<Coord> closeList = new ArrayList<Coord>();
    private Node endNode;

    public Node start(Map<String, Coord> mapInfoMap, Coord start, Coord end) {
        this.mapInfoMap = mapInfoMap;
        this.endNode = new Node();
        this.endNode.setCoord(mapInfoMap.get(end.getId()));
// clean
        this.openList.clear();
        this.closeList.clear();
        Node node = new Node();
        node.setCoord(mapInfoMap.get(start.getId()));
        node.setParent(node);
// 开始搜索
        openList.add(node);
        return searchPath();
    }

    public Node searchPath() {
        while (!openList.isEmpty()) {
            Node current = openList.poll();
            Coord info = current.getCoord();
            if (closeList.contains(info)) {
                continue;
            }
            if (this.endNode.getCoord().equals(current.getCoord())) {
                return current;
            }
            closeList.add(info);
            if (info.isDown()) {
                addCoord(current, info.getX(), info.getY() + 1);
            }
            if (info.isUp()) {
                addCoord(current, info.getX(), info.getY() - 1);
            }
            if (info.isRight()) {
                addCoord(current, info.getX() + 1, info.getY());
            }
            if (info.isLeft()) {
                addCoord(current, info.getX() - 1, info.getY());
            }
        }
        return null;
    }

    public void addCoord(Node current, int x, int y) {
        Coord coord = mapInfoMap.get(StrUtil.padPre(String.valueOf(x), 4, "0") + StrUtil.padPre(String.valueOf(y), 4, "0"));
        if (coord == null) {
            return;
        } else if (closeList.contains(coord)) {
            return;
        }
        Node node = new Node();
        node.setCoord(coord);
        node.setParent(current);
        node.setEvaluateDistance(calcH(this.endNode.getCoord(), coord));
        node.setActualDistance(current.getActualDistance() + 1);
        openList.add(node);
    }

    /**
     * 计算H的估值：“曼哈顿”法，坐标分别取差值相加
     */
    private int calcH(Coord end, Coord coord) {
        return (Math.abs(end.getX() - coord.getX()) + Math.abs(end.getY() - coord.getY()));
    }

}
```
> * H此处使用曼哈顿距离,也可通过其它方式计算，也可以使用欧几里得距离，斜率距离等，
> * G此处使用的是固定+1，可依据节点属性不同进行调整，例如平地为+1沙地为+2等