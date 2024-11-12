package pathPlanning.astart;

import pathPlanning.astart.model.Coord;
import pathPlanning.astart.model.Node;
import cn.hutool.core.util.StrUtil;

import java.util.*;
import java.util.stream.Collectors;

public class AStar {
    private List<Coord> coords;
    private Map<String, Coord> mapInfoMap;
    //待探测节点
    private Queue<Node> openList = new PriorityQueue<Node>(); // 优先队列(升序);
    //已探测过的节点
    private List<Coord> closeList = new ArrayList<Coord>();
    //结束节点
    private Node endNode;

    public Node start(List<Coord> coords, Coord start, Coord end) {
        this.coords = coords;
        this.mapInfoMap = coords.stream().collect(Collectors.toMap(Coord::getId, o -> o, (o1, o2) -> o1));
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
        Coord coord = mapInfoMap.get("01" + StrUtil.padPre(String.valueOf(x), 4, "0") + StrUtil.padPre(String.valueOf(y), 4, "0"));
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

    public void printMap() {
        Map<Integer, List<Coord>> xcoords = coords.stream().collect(Collectors.groupingBy(coord -> coord.getY()));
        String result = xcoords.keySet().stream().sorted().map(y -> {
            return xcoords.get(y).stream()
                    .sorted(Comparator.comparingInt(Coord::getX))
                    .map(coord -> coord.getId() + "(" + coord.getMark() + ")")
                    .collect(Collectors.joining("-"));
        }).collect(Collectors.joining("\n"));
        System.out.println(result);
    }
}
