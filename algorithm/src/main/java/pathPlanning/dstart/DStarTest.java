package pathPlanning.dstart;

import pathPlanning.dstart.model.Coord;
import pathPlanning.dstart.model.DStarNode;

import java.util.ArrayList;
import java.util.List;

/**
 * D*算法测试类
 */
public class DStarTest {
    private static final int GRID_SIZE = 10;
    private DStar dstar;
    private List<Coord> coords;
    private char[][] grid;

    public DStarTest() {
        this.dstar = new DStar();
        this.coords = new ArrayList<>();
        this.grid = new char[GRID_SIZE][GRID_SIZE];
    }

    /**
     * 初始化网格
     */
    private void initializeGrid() {
        // 清空之前的数据
        coords.clear();
        
        // 初始化网格和坐标点
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = '.';  // 用'.'表示空白格子
                Coord coord = new Coord(i, j);
                
                // 设置每个坐标点的可移动方向
                if (i > 0) coord.setLeft(true);
                if (i < GRID_SIZE - 1) coord.setRight(true);
                if (j > 0) coord.setUp(true);
                if (j < GRID_SIZE - 1) coord.setDown(true);
                
                coords.add(coord);
            }
        }
        System.out.println("网格初始化完成，大小: " + GRID_SIZE + "x" + GRID_SIZE);
    }

    /**
     * 添加障碍物
     */
    private void addObstacles() {
        // 添加一些固定的障碍物进行测试
        addObstacle(2, 2);
        addObstacle(2, 3);
        addObstacle(2, 4);
        addObstacle(3, 4);
        addObstacle(4, 4);
        addObstacle(5, 4);
        addObstacle(6, 4);
        
        System.out.println("已添加障碍物");
    }

    /**
     * 添加单个障碍物
     */
    private void addObstacle(int x, int y) {
        if (x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE) {
            System.out.println("警告: 障碍物坐标 (" + x + "," + y + ") 超出网格范围");
            return;
        }
        
        grid[x][y] = '#';  // 用'#'表示障碍物
        
        // 获取障碍物位置的坐标点
        Coord obstacleCoord = getCoord(x, y);
        if (obstacleCoord != null) {
            // 禁止所有方向的移动
            obstacleCoord.setUp(false);
            obstacleCoord.setDown(false);
            obstacleCoord.setLeft(false);
            obstacleCoord.setRight(false);
            
            // 更新相邻格子的可通行方向
            updateNeighborDirections(x, y);
        }
    }

    /**
     * 更新邻居节点的可通行方向
     */
    private void updateNeighborDirections(int x, int y) {
        // 更新上方格子
        if (y > 0) {
            Coord up = getCoord(x, y-1);
            if (up != null) {
                up.setDown(false);
            }
        }

        // 更新下方格子
        if (y < GRID_SIZE-1) {
            Coord down = getCoord(x, y+1);
            if (down != null) {
                down.setUp(false);
            }
        }

        // 更新左侧格子
        if (x > 0) {
            Coord left = getCoord(x-1, y);
            if (left != null) {
                left.setRight(false);
            }
        }

        // 更新右侧格子
        if (x < GRID_SIZE-1) {
            Coord right = getCoord(x+1, y);
            if (right != null) {
                right.setLeft(false);
            }
        }
    }

    /**
     * 获取指定坐标的Coord对象
     */
    private Coord getCoord(int x, int y) {
        return coords.stream()
                .filter(c -> c.getX() == x && c.getY() == y)
                .findFirst()
                .orElse(null);
    }

    /**
     * 打印网格
     */
    private void printGrid() {
        System.out.println("\n当前网格状态：");
        // 打印列号
        System.out.print("  ");
        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println("\n -----------");

        for (int i = 0; i < GRID_SIZE; i++) {
            // 打印行号
            System.out.print(i + "|");
            for (int j = 0; j < GRID_SIZE; j++) {
                char gridChar = grid[i][j];
                if (gridChar == 'S' || gridChar == 'G' || gridChar == '#' || gridChar == '*') {
                    System.out.print(gridChar + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * 运行简单测试
     */
    public void runSimpleTest() {
        System.out.println("运行简单测试...");

        // 初始化网格和障碍物
        initializeGrid();
        addObstacles();

        // 设置起点和终点
        Coord start = getCoord(1, 1);
        Coord goal = getCoord(8, 8);
        
        if (start == null || goal == null) {
            System.out.println("错误：无法设置起点或终点");
            return;
        }
        
        grid[start.getX()][start.getY()] = 'S';
        grid[goal.getX()][goal.getY()] = 'G';

        // 打印初始网格状态
        System.out.println("初始网格状态：");
        printGrid();

        // 运行算法
        System.out.println("开始寻路...");
        DStarNode path = dstar.start(coords, start, goal);

        // 打印结果
        if (path != null) {
            System.out.println("找到路径！");
            
            // 更新网格显示路径
            DStarNode current = path;
            int pathLength = 0;
            while (current != null && current.getParent() != null) {
                Coord coord = current.getCoord();
                if (grid[coord.getX()][coord.getY()] != 'S' && 
                    grid[coord.getX()][coord.getY()] != 'G') {
                    grid[coord.getX()][coord.getY()] = '*';
                }
                current = current.getParent();
                pathLength++;
            }
            
            // 打印最终网格
            System.out.println("\n最终路径（路径长度: " + pathLength + "）：");
            printGrid();
            
            // 打印详细路径信息
            dstar.printDebugInfo();
        } else {
            System.out.println("未找到路径！");
            printGrid();
        }
    }

    /**
     * 运行动态更新测试
     */
    public void runDynamicTest() {
        System.out.println("\n运行动态更新测试...");

        // 初始化网格和基本障碍物
        initializeGrid();
        addObstacles();

        // 设置起点和终点
        Coord start = getCoord(1, 1);
        Coord goal = getCoord(8, 8);
        grid[start.getX()][start.getY()] = 'S';
        grid[goal.getX()][goal.getY()] = 'G';

        // 打印初始网格状态
        System.out.println("初始网格状态：");
        printGrid();

        // 第一次路径规划
        System.out.println("开始第一次寻路...");
        DStarNode path = dstar.start(coords, start, goal);
        if (path != null) {
            updateGridWithPath(path);
            System.out.println("\n第一次路径：");
            printGrid();
        }

        // 添加新的动态障碍物
        System.out.println("\n添加新的动态障碍物...");
        addObstacle(1, 4);  // 在初始路径上添加障碍物
        addObstacle(2, 1);  // 在初始路径上添加障碍物
        printGrid();

        // 更新地图并重新规划路径
        System.out.println("重新规划路径...");
        Coord changedCoord = getCoord(1, 4);
        dstar.updateMap(changedCoord);
        path = dstar.start(coords, start, goal);
        
        if (path != null) {
            updateGridWithPath(path);
            System.out.println("\n更新后的路径：");
            printGrid();
        }
    }

    /**
     * 更新网格显示路径
     */
    private void updateGridWithPath(DStarNode pathStart) {
        // 清除之前的路径标记（保留起点、终点和障碍物）
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == '*') {
                    grid[i][j] = '.';
                }
            }
        }

        // 标记新路径
        DStarNode current = pathStart;
        int pathLength = 0;
        while (current != null && current.getParent() != null) {
            Coord coord = current.getCoord();
            if (grid[coord.getX()][coord.getY()] != 'S' && 
                grid[coord.getX()][coord.getY()] != 'G') {
                grid[coord.getX()][coord.getY()] = '*';
            }
            current = current.getParent();
            pathLength++;
        }
        System.out.println("路径长度: " + pathLength);
    }

    public static void main(String[] args) {
        DStarTest test = new DStarTest();

        // 运行简单测试
//        test.runSimpleTest();

        System.out.println("\n=================================\n");

        // 运行动态更新测试
        test.runDynamicTest();
    }
}