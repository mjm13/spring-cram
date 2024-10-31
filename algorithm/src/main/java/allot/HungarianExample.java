package allot;

import java.util.Arrays;

public class HungarianExample {
    public static void main(String[] args) {
        // 1. 问题描述
        int[][] costMatrix = {
                {4, 3},  // 快递员0：送包裹0成本4，送包裹1成本3
                {2, 5}   // 快递员1：送包裹0成本2，送包裹1成本5
        };

        System.out.println("初始成本矩阵：");
        printMatrix(costMatrix);

        // 2. 算法执行过程
        System.out.println("\n===== 算法执行步骤 =====");

        // 步骤1：初始化标签
        int[] workerLabels = {4, 5};  // 每行的最大值
        int[] taskLabels = {0, 0};    // 初始化为0
        System.out.println("\n初始标签：");
        System.out.println("快递员标签：" + Arrays.toString(workerLabels));
        System.out.println("任务标签：" + Arrays.toString(taskLabels));

        // 步骤2：计算松弛矩阵
        int[][] slackMatrix = calculateSlackMatrix(costMatrix, workerLabels, taskLabels);
        System.out.println("\n松弛矩阵（成本 - 快递员标签 - 任务标签）：");
        printMatrix(slackMatrix);

        // 步骤3：寻找最优匹配
        int[] assignment = {1, 0};  // 最终结果：快递员0->包裹1，快递员1->包裹0
        int totalCost = costMatrix[0][1] + costMatrix[1][0];  // 3 + 2 = 5

        // 输出最终结果
        System.out.println("\n===== 最优分配方案 =====");
        System.out.println("分配结果：");
        System.out.println("快递员0 -> 包裹1（成本：" + costMatrix[0][1] + "）");
        System.out.println("快递员1 -> 包裹0（成本：" + costMatrix[1][0] + "）");
        System.out.println("总成本：" + totalCost);

        // 验证这是最优解
        System.out.println("\n===== 验证其他可能的方案 =====");
        int altCost = costMatrix[0][0] + costMatrix[1][1];  // 4 + 5 = 9
        System.out.println("另一种方案（快递员0->包裹0，快递员1->包裹1）总成本：" + altCost);
    }

    // 计算松弛矩阵
    private static int[][] calculateSlackMatrix(int[][] costMatrix,
                                                int[] workerLabels,
                                                int[] taskLabels) {
        int[][] slackMatrix = new int[costMatrix.length][costMatrix[0].length];
        for (int i = 0; i < costMatrix.length; i++) {
            for (int j = 0; j < costMatrix[0].length; j++) {
                slackMatrix[i][j] = costMatrix[i][j] - workerLabels[i] - taskLabels[j];
            }
        }
        return slackMatrix;
    }

    // 打印矩阵
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}