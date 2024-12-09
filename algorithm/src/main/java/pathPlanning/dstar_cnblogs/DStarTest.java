package pathPlanning.dstar_cnblogs;

public class DStarTest {
    public static void main(String[] args) {
        // 创建一个6x6的地图
        int size = 6;
        int[][] graph = new int[size * size][size * size];

        // 初始化所有位置为不可通行
        for (int i = 0; i < size * size; i++) {
            for (int j = 0; j < size * size; j++) {
                graph[i][j] = Integer.MAX_VALUE;
            }
        }

        // 设置可通行区域
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int current = i * size + j;
                // 向右连接
                if (j < size - 1) {
                    graph[current][current + 1] = 1;
                    graph[current + 1][current] = 1;
                }
                // 向下连接
                if (i < size - 1) {
                    graph[current][current + size] = 1;
                    graph[current + size][current] = 1;
                }
            }
        }

        // 添加障碍物
        int[] obstacles = {8, 9, 14, 15, 20, 21};
        for (int obs : obstacles) {
            for (int i = 0; i < size * size; i++) {
                graph[obs][i] = Integer.MAX_VALUE;
                graph[i][obs] = Integer.MAX_VALUE;
            }
        }

        int source = 0, target = 35;  // 从左上角到右下角
        DStar dStar = new DStar(graph);

        // 打印初始地图
        System.out.println("初始地图：");

        // 运行算法
        dStar.run(source, target);
    }
}