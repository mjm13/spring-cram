package pathPlanning.dstarlite2;

import java.util.*;

/**
 * D* Lite 算法实现
 * 该算法用于动态路径规划，适用于机器人导航等场景。
 * 
 * @author daniel beard
 * http://danielbeard.io
 * http://github.com/daniel-beard
 * 
 * Copyright (C) 2012 Daniel Beard
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
public class DStarLite implements java.io.Serializable {

    // 细胞哈希表，存储状态与其信息的映射
    public HashMap<String, State> cellHash = new HashMap<>();
    
    // 私有成员变量
    private List<State> path = new ArrayList<State>(); // 存储路径
    private double C1; // 常数 C1，用于计算代价
    private double k_m; // 路径代价修正值
    private State s_start = new State(); // 起始状态
    private State s_goal = new State(); // 目标状态
    private State s_last = new State(); // 上一个状态
    private int maxSteps; // 最大步骤数
    private PriorityQueue<State> openList = new PriorityQueue<State>(); // 优先队列，存储待处理状态
    private HashMap<State, Float> openHash = new HashMap<State, Float>(); // 存储打开列表的哈希表

    // 常量
    private double M_SQRT2 = Math.sqrt(2.0); // √2 的值

    // 默认构造函数
    public DStarLite() {
        maxSteps = 80000; // 设置最大步骤数
        C1 = 1; // 设置常数 C1
    }

    public static void main(String[] args) {
        DStarLite pf = new DStarLite();
        pf.init(0, 1, 8, 8); // 初始化起始和目标坐标
        pf.updateCell(2, 1, -1); // 更新障碍物
        pf.updateCell(2, 0, -1);
        pf.updateCell(2, 2, -1);
        pf.updateCell(3, 0, -1);

        System.out.println("Start node: (0,1)");
        System.out.println("End node: (3,1)");
        
        // 计时重新规划路径
        long begin = System.currentTimeMillis();
        pf.replan(); // 重新规划路径
        List<State> path = pf.getPath(); // 获取路径
        for (State i : path) {
            System.out.println("x: " + i.x + " y: " + i.y); // 输出路径坐标
        }
        
        System.out.println("updateStart: (4,5)");
        pf.updateStart(4, 5); // 更新起始位置
        System.out.println("updateGoal: (3,2)");
        pf.updateGoal(3, 2); // 更新目标位置
        long end = System.currentTimeMillis();

        System.out.println("Time: " + (end - begin) + "ms"); // 输出重新规划所用时间

        path = pf.getPath(); // 获取更新后的路径
        for (State i : path) {
            System.out.println("x: " + i.x + " y: " + i.y); // 输出更新后的路径坐标
        }
        System.out.println("updateStart: (4,5)");
        pf.updateStart(4, 5); // 更新起始位置
        System.out.println("updateGoal: (18,10)");
        pf.updateGoal(18, 10); // 更新目标位置
        pf.replan();
        path = pf.getPath(); // 获取更新后的路径
        for (State i : path) {
            System.out.println("x: " + i.x + " y: " + i.y); // 输出更新后的路径坐标
        }

        System.out.println("updateStart: (6,6)");
        pf.updateStart(6, 6); // 更新起始位置
        System.out.println("updateGoal: (22,32)");
        pf.updateGoal(22, 32); // 更新目标位置
        pf.replan();
        path = pf.getPath(); // 获取更新后的路径
        for (State i : path) {
            System.out.println("x: " + i.x + " y: " + i.y); // 输出更新后的路径坐标
        }
    }

    // 计算键值（未实现）
    public void CalculateKeys() {
        // TODO: 实现计算键值的逻辑
    }

    /*
     * 初始化方法
     * @params sX, sY 起始坐标，gX, gY 目标坐标
     */
    public void init(int sX, int sY, int gX, int gY) {
        cellHash.clear(); // 清空细胞哈希表
        path.clear(); // 清空路径
        openHash.clear(); // 清空打开列表哈希表
        while (!openList.isEmpty()) openList.poll(); // 清空优先队列

        k_m = 0; // 重置路径代价修正值

        // 设置起始和目标状态的坐标
        s_start.x = sX;
        s_start.y = sY;
        s_goal.x = gX;
        s_goal.y = gY;

        // 初始化目标状态的 g 和 rhs 值
        s_goal.g = 0;
        s_goal.rhs = 0;
        s_goal.cost = C1;

        // 将目标状态添加到细胞哈希表
        cellHash.put(s_goal.getId(), s_goal);

        // 初始化起始状态的 g 和 rhs 值
        s_start.g = s_start.rhs = heuristic(s_start, s_goal);
        s_start.cost = C1;
        cellHash.put(s_start.getId(), s_start); // 将起始状态添加到细胞哈希表
        s_start = calculateKey(s_start); // 计算起始状态的键值

        s_last = s_start; // 更新上一个状态
    }

    /*
     * 计算键值（state u）
     * 根据 [S. Koenig, 2002] 的定义
     */
    private State calculateKey(State u) {
        double val = Math.min(getRHS(u), getG(u)); // 取 g 和 rhs 中的较小值

        u.totalCost = val + heuristic(u, s_start) + k_m; // 计算总代价
        u.localCost = val; // 计算局部最小代价

        return u; // 返回更新后的状态
    }

    /*
     * 返回状态 u 的 rhs 值
     */
    private double getRHS(State u) {
        if (u == s_goal) return 0; // 如果是目标状态，返回 0

        // 如果细胞哈希表中不包含状态 u
        if (cellHash.get(u.getId()) == null)
            return heuristic(u, s_goal); // 返回启发式估计值
        return cellHash.get(u.getId()).rhs; // 返回状态 u 的 rhs 值
    }

    /*
     * 返回状态 u 的 g 值
     */
    private double getG(State u) {
        // 如果细胞哈希表中不包含状态 u
        if (cellHash.get(u.getId()) == null)
            return heuristic(u, s_goal); // 返回启发式估计值
        return cellHash.get(u.getId()).g; // 返回状态 u 的 g 值
    }

    /*
     * 启发式函数，使用 8 方向距离
     * 乘以常数 C1（应设置为 <= 最小代价）
     */
    private double heuristic(State a, State b) {
        return eightCondist(a, b) * C1; // 返回启发式估计值
    }

    /*
     * 返回状态 a 和状态 b 之间的 8 方向距离
     */
    private double eightCondist(State a, State b) {
        double temp;
        double min = Math.abs(a.x - b.x); // 计算 x 方向的距离
        double max = Math.abs(a.y - b.y); // 计算 y 方向的距离
        if (min > max) { // 确保 min 和 max 的顺序
            temp = min;
            min = max;
            max = temp;
        }
        return ((M_SQRT2 - 1.0) * min + max); // 返回 8 方向距离
    }

    public boolean replan() {
        path.clear(); // 清空路径

        int res = computeShortestPath(); // 计算最短路径
        if (res < 0) {
            System.out.println("No Path to Goal"); // 如果没有路径，输出提示
            return false;
        }

        LinkedList<State> n = new LinkedList<State>(); // 存储后继状态
        State cur = s_start; // 当前状态为起始状态

        if (getG(s_start) == Double.POSITIVE_INFINITY) {
            System.out.println("No Path to Goal"); // 如果起始状态的 g 值为无穷大，输出提示
            return false;
        }

        while (cur.neq(s_goal)) { // 当当前状态不等于目标状态时
            path.add(cur); // 将当前状态添加到路径
            n = new LinkedList<State>();
            n = getSucc(cur); // 获取当前状态的后继状态

            if (n.isEmpty()) {
                System.out.println("No Path to Goal"); // 如果没有后继状态，输出提示
                return false;
            }

            double cmin = Double.POSITIVE_INFINITY; // 初始化最小代价
            double tmin = 0; // 初始化最小代价的临时变量
            State smin = new State(); // 初始化最小状态

            for (State i : n) { // 遍历后继状态
                double val = cost(cur, i); // 计算当前状态到后继状态的代价
                double val2 = trueDist(i, s_goal) + trueDist(s_start, i); // 计算从后继状态到目标状态和起始状态的真实距离
                val += getG(i); // 加上后继状态的 g 值

                if (close(val, cmin)) { // 如果当前代价与最小代价接近
                    if (tmin > val2) { // 如果当前最小代价的临时变量大于 val2
                        tmin = val2; // 更新最小代价的临时变量
                        cmin = val; // 更新最小代价
                        smin = i; // 更新最小状态
                    }
                } else if (val < cmin) { // 如果当前代价小于最小代价
                    tmin = val2; // 更新最小代价的临时变量
                    cmin = val; // 更新最小代价
                    smin = i; // 更新最小状态
                }
            }
            n.clear(); // 清空后继状态列表
            cur = new State(smin); // 更新当前状态
        }
        path.add(s_goal); // 将目标状态添加到路径
        return true; // 返回成功
    }

    /*
     * 根据 [S. Koenig, 2002] 的定义计算最短路径
     * 1. 在达到最大步骤数后停止规划
     * 2. 懒惰地从打开列表中移除状态，避免遍历整个列表
     */
    private int computeShortestPath() {
        LinkedList<State> s = new LinkedList<State>(); // 存储状态列表

        if (openList.isEmpty()) return 1; // 如果打开列表为空，返回 1

        int k = 0; // 步骤计数器
        while ((!openList.isEmpty()) &&
                (openList.peek().lt(s_start = calculateKey(s_start))) ||
                (getRHS(s_start) != getG(s_start))) {

            if (k++ > maxSteps) { // 如果超过最大步骤数
                System.out.println("At maxsteps"); // 输出提示
                return -1; // 返回 -1
            }

            State u; // 当前状态

            boolean test = (getRHS(s_start) != getG(s_start)); // 检查是否需要更新

            // 懒惰移除
            while (true) {
                if (openList.isEmpty()) return 1; // 如果打开列表为空，返回 1
                u = openList.poll(); // 从打开列表中取出状态

                if (!isValid(u)) continue; // 如果状态无效，继续
                if (!(u.lt(s_start)) && (!test)) return 2; // 如果状态不小于起始状态且不需要更新，返回 2
                break; // 退出循环
            }

            openHash.remove(u); // 从打开哈希表中移除状态

            State k_old = new State(u); // 复制当前状态

            if (k_old.lt(calculateKey(u))) { // 如果状态过期
                insert(u); // 插入状态
            } else if (getG(u) > getRHS(u)) { // 如果 g 值大于 rhs 值
                setG(u, getRHS(u)); // 更新 g 值
                s = getPred(u); // 获取前驱状态
                for (State i : s) {
                    updateVertex(i); // 更新前驱状态
                }
            } else { // g <= rhs，状态变差
                setG(u, Double.POSITIVE_INFINITY); // 将 g 值设为无穷大
                s = getPred(u); // 获取前驱状态

                for (State i : s) {
                    updateVertex(i); // 更新前驱状态
                }
                updateVertex(u); // 更新当前状态
            }
        } // while
        return 0; // 返回 0
    }

    /*
     * 返回状态 u 的后继状态列表
     * 由于这是一个 8 方向图，该列表包含所有相邻单元格
     * 如果单元格被占用，则没有后继状态
     */
    private LinkedList<State> getSucc(State u) {
        LinkedList<State> s = new LinkedList<State>(); // 存储后继状态
        State tempState; // 临时状态

        if (occupied(u)) return s; // 如果状态被占用，返回空列表

        // 生成后继状态，从右侧开始，顺时针移动
        tempState = new State(u.x + 1, u.y, -1.0, -1.0);
        s.addFirst(tempState);
        tempState = new State(u.x + 1, u.y + 1, -1.0, -1.0);
        s.addFirst(tempState);
        tempState = new State(u.x, u.y + 1, -1.0, -1.0);
        s.addFirst(tempState);
        tempState = new State(u.x - 1, u.y + 1, -1.0, -1.0);
        s.addFirst(tempState);
        tempState = new State(u.x - 1, u.y, -1.0, -1.0);
        s.addFirst(tempState);
        tempState = new State(u.x - 1, u.y - 1, -1.0, -1.0);
        s.addFirst(tempState);
        tempState = new State(u.x, u.y - 1, -1.0, -1.0);
        s.addFirst(tempState);
        tempState = new State(u.x + 1, u.y - 1, -1.0, -1.0);
        s.addFirst(tempState);

        return s; // 返回后继状态列表
    }

    /*
     * 返回状态 u 的前驱状态列表
     * 由于这是一个 8 方向连接图，列表包含状态 u 的所有邻居
     * 被占用的邻居不会被添加到列表中
     */
    private LinkedList<State> getPred(State u) {
        LinkedList<State> s = new LinkedList<State>(); // 存储前驱状态
        State tempState; // 临时状态

        // 生成前驱状态
        tempState = new State(u.x + 1, u.y, -1.0, -1.0);
        if (!occupied(tempState)) s.addFirst(tempState);
        tempState = new State(u.x + 1, u.y + 1, -1.0, -1.0);
        if (!occupied(tempState)) s.addFirst(tempState);
        tempState = new State(u.x, u.y + 1, -1.0, -1.0);
        if (!occupied(tempState)) s.addFirst(tempState);
        tempState = new State(u.x - 1, u.y + 1, -1.0, -1.0);
        if (!occupied(tempState)) s.addFirst(tempState);
        tempState = new State(u.x - 1, u.y, -1.0, -1.0);
        if (!occupied(tempState)) s.addFirst(tempState);
        tempState = new State(u.x - 1, u.y - 1, -1.0, -1.0);
        if (!occupied(tempState)) s.addFirst(tempState);
        tempState = new State(u.x, u.y - 1, -1.0, -1.0);
        if (!occupied(tempState)) s.addFirst(tempState);
        tempState = new State(u.x + 1, u.y - 1, -1.0, -1.0);
        if (!occupied(tempState)) s.addFirst(tempState);

        return s; // 返回前驱状态列表
    }

    /*
     * 更新代理/机器人位置
     * 这不会强制重新规划路径
     */
    public void updateStart(int x, int y) {
        s_start.x = x; // 更新起始状态的 x 坐标
        s_start.y = y; // 更新起始状态的 y 坐标

        k_m += heuristic(s_last, s_start); // 更新路径代价修正值

        s_start = calculateKey(s_start); // 计算起始状态的键值
        s_last = s_start; // 更新上一个状态
    }

    /*
     * 更新目标位置
     * 先保存所有非空节点，清空地图，移动目标并重新添加非空单元
     */
    public void updateGoal(int x, int y) {
        List<State> toAdd = new ArrayList<>(cellHash.values()); // 保存所有非空节点

        cellHash.clear(); // 清空细胞哈希表
        openHash.clear(); // 清空打开列表哈希表

        while (!openList.isEmpty())
            openList.poll(); // 清空优先队列

        k_m = 0; // 重置路径代价修正值

        s_goal.x = x; // 更新目标状态的 x 坐标
        s_goal.y = y; // 更新目标状态的 y 坐标
        s_goal.g = s_goal.rhs = 0; // 初始化目标状态的 g 和 rhs 值
        s_goal.cost = C1; // 设置目标状态的代价

        cellHash.put(s_goal.getId(), s_goal); // 将目标状态添加到细胞哈希表

        s_start.g = s_start.rhs = heuristic(s_start, s_goal); // 更新起始状态的 g 和 rhs 值
        s_start.cost = C1; // 设置起始状态的代价
        cellHash.put(s_start.getId(), s_start); // 将起始状态添加到细胞哈希表
        s_start = calculateKey(s_start); // 计算起始状态的键值

        s_last = s_start; // 更新上一个状态

        // 重新添加非空单元
        toAdd.forEach(state -> {
            updateCell(state.x, state.y, state.cost); // 更新每个非空单元
        });
        System.out.println("cellHash.size():"+cellHash.size());
    }

    /*
     * 根据 [S. Koenig, 2002] 的定义更新状态 u
     */
    private void updateVertex(State u) {
        LinkedList<State> s = new LinkedList<State>(); // 存储后继状态

        if (u.neq(s_goal)) { // 如果状态不等于目标状态
            s = getSucc(u); // 获取后继状态
            double tmp = Double.POSITIVE_INFINITY; // 初始化临时变量
            double tmp2; // 临时变量

            for (State i : s) { // 遍历后继状态
                tmp2 = getG(i) + cost(u, i); // 计算从状态 u 到后继状态 i 的代价
                if (tmp2 < tmp) tmp = tmp2; // 更新临时变量
            }
            if (!close(getRHS(u), tmp)) setRHS(u, tmp); // 如果 rhs 值不等于临时变量，更新 rhs 值
        }

        if (!close(getG(u), getRHS(u))) insert(u); // 如果 g 值不等于 rhs 值，插入状态
    }

    /*
     * 返回状态 u 是否在打开列表中
     */
    private boolean isValid(State u) {
        if (openHash.get(u) == null) return false; // 如果状态不在打开哈希表中，返回 false
        if (!close(keyHashCode(u), openHash.get(u))) return false; // 如果键值不相等，返回 false
        return true; // 返回 true
    }

    /*
     * 设置状态 u 的 g 值
     */
    private void setG(State u, double g) {
        makeNewCell(u); // 确保状态 u 在哈希表中
        cellHash.get(u.getId()).g = g; // 更新状态 u 的 g 值
    }

    /*
     * 设置状态 u 的 rhs 值
     */
    private void setRHS(State u, double rhs) {
        makeNewCell(u); // 确保状态 u 在哈希表中
        cellHash.get(u.getId()).rhs = rhs; // 更新状态 u 的 rhs 值
    }

    /*
     * 检查状态 u 是否在哈希表中，如果不在则添加
     */
    private void makeNewCell(State u) {
        if (cellHash.get(u.getId()) != null) return; // 如果状态已存在，返回
        u.g = u.rhs = heuristic(u, s_goal); // 初始化 g 和 rhs 值
        u.cost = C1; // 设置代价
        cellHash.put(u.getId(), u); // 将状态添加到哈希表
    }

    /*
     * 更新单元格
     */
    public void updateCell(int x, int y, double val) {
        State u = new State(); // 创建新状态
        u.x = x; // 设置 x 坐标
        u.y = y; // 设置 y 坐标

        if ((u.eq(s_start)) || (u.eq(s_goal))) return; // 如果是起始或目标状态，返回

        makeNewCell(u); // 确保状态在哈希表中
        cellHash.get(u.getId()).cost = val; // 更新状态的代价
        updateVertex(u); // 更新状态
    }

    /*
     * 将状态 u 插入到打开列表和打开哈希表中
     */
    private void insert(State u) {
        float csum; // 存储键值

        u = calculateKey(u); // 计算状态的键值
        csum = keyHashCode(u); // 获取键值

        // 如果状态已在列表中，返回
        openHash.put(u, csum); // 将状态添加到打开哈希表
        openList.add(u); // 将状态添加到打开列表
    }

    /*
     * 返回状态 u 的键值哈希码
     */
    private float keyHashCode(State u) {
        return (float) (u.totalCost + 1193 * u.localCost); // 返回键值哈希码
    }

    /*
     * 返回状态 u 是否被占用（不可通行）
     */
    private boolean occupied(State u) {
        // 如果细胞哈希表中不包含状态 u
        if (cellHash.get(u.getId()) == null)
            return false; // 返回 false
        return (cellHash.get(u.getId()).cost < 0); // 返回状态的代价是否小于 0
    }

    /*
     * 计算状态 a 和状态 b 之间的欧几里得距离
     */
    private double trueDist(State a, State b) {
        float x = a.x - b.x; // 计算 x 方向的距离
        float y = a.y - b.y; // 计算 y 方向的距离
        return Math.sqrt(x * x + y * y); // 返回欧几里得距离
    }

    /*
     * 返回从状态 a 到状态 b 的移动代价
     */
    private double cost(State a, State b) {
        int xd = Math.abs(a.x - b.x); // 计算 x 方向的距离
        int yd = Math.abs(a.y - b.y); // 计算 y 方向的距离
        double scale = 1; // 初始化比例

        if (xd + yd > 1) scale = M_SQRT2; // 如果是对角移动，使用 √2 的比例

        if (cellHash.containsKey(a) == false) return scale * C1; // 如果状态不在哈希表中，返回代价
        return scale * cellHash.get(a).cost; // 返回移动代价
    }

    /*
     * 返回 x 和 y 是否在 10E-5 的范围内
     */
    private boolean close(double x, double y) {
        if (x == Double.POSITIVE_INFINITY && y == Double.POSITIVE_INFINITY) return true; // 如果两个值都是无穷大，返回 true
        return (Math.abs(x - y) < 0.00001); // 返回两个值的差是否小于 0.00001
    }

    public List<State> getPath() {
        return path; // 返回路径
    }
}