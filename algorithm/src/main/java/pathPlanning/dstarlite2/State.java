package pathPlanning.dstarlite2;

/*
 * 状态类，表示路径规划中的一个状态
 * 包含状态的坐标、代价、g 值、rhs 值等信息
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

import java.net.URLEncoder;

public class State implements Comparable, java.io.Serializable {
    public int x = 0; // 状态的 x 坐标
    public int y = 0; // 状态的 y 坐标

    public double g = 0; // 从起始状态到当前状态的实际代价
    public double rhs = 0; // 从起始状态到目标状态的预期代价
    public double cost = 0; // 状态的代价

    /**
     * 探测优先级 - 总代价
     * 值 = min(g, rhs) + h(s, s_start) + k_m
     * min(g, rhs): 取 g 值和 rhs 值中的较小值
     * h(s, s_start): 从当前节点到起点的启发式估计值
     * k_m: 路径代价修正值  
     */
    public double totalCost; // 总代价

    /**
     * 探测优先级 - 局部最小代价
     * 值 = min(g, rhs)
     */
    public double localCost; // 局部最小代价

    // 默认构造函数
    public State() {
    }

    // 重载构造函数
    public State(int x, int y, double totalCost, double localCost) {
        this.x = x; // 设置 x 坐标
        this.y = y; // 设置 y 坐标
        this.totalCost = totalCost; // 设置总代价
        this.localCost = localCost; // 设置局部最小代价
    }

    // 重载构造函数
    public State(State other) {
        this.x = other.x; // 复制 x 坐标
        this.y = other.y; // 复制 y 坐标
        this.totalCost = other.totalCost; // 复制总代价
        this.localCost = other.localCost; // 复制局部最小代价
    }

    // 判断两个状态是否相等
    public boolean eq(final State s2) {
        return ((this.x == s2.x) && (this.y == s2.y)); // 比较 x 和 y 坐标
    }

    // 判断两个状态是否不相等
    public boolean neq(final State s2) {
        return ((this.x != s2.x) || (this.y != s2.y)); // 比较 x 和 y 坐标
    }

    // 判断当前状态是否大于另一个状态
    public boolean gt(final State s2) {
        if (totalCost - 0.00001 > s2.totalCost) return true; // 如果当前状态的总代价大于另一个状态的总代价
        else if (totalCost < s2.totalCost - 0.00001) return false; // 如果当前状态的总代价小于另一个状态的总代价
        return localCost > s2.localCost; // 比较局部最小代价
    }

    // 判断当前状态是否小于或等于另一个状态
    public boolean lte(final State s2) {
        if (totalCost < s2.totalCost) return true; // 如果当前状态的总代价小于另一个状态的总代价
        else if (totalCost > s2.totalCost) return false; // 如果当前状态的总代价大于另一个状态的总代价
        return localCost < s2.localCost + 0.00001; // 比较局部最小代价
    }

    // 判断当前状态是否小于另一个状态
    public boolean lt(final State s2) {
        if (totalCost + 0.000001 < s2.totalCost) return true; // 如果当前状态的总代价小于另一个状态的总代价
        else if (totalCost - 0.000001 > s2.totalCost) return false; // 如果当前状态的总代价大于另一个状态的总代价
        return localCost < s2.localCost; // 比较局部最小代价
    }

    // CompareTo 方法，用于优先队列
    public int compareTo(Object that) {
        // 这是 gt 方法的修改版本
        State other = (State) that; // 将对象转换为 State 类型
        if (totalCost - 0.00001 > other.totalCost) return 1; // 如果当前状态的总代价大于另一个状态的总代价
        else if (totalCost < other.totalCost - 0.00001) return -1; // 如果当前状态的总代价小于另一个状态的总代价
        if (localCost > other.localCost) return 1; // 比较局部最小代价
        else if (localCost < other.localCost) return -1; // 比较局部最小代价
        return 0; // 返回 0，表示相等
    }

    // 重写 hashCode 方法
    @Override
    public int hashCode() {
        return this.x + 34245 * this.y; // 根据 x 和 y 计算哈希值
    }

    // 重写 equals 方法
    @Override
    public boolean equals(Object aThat) {
        // 检查自我比较
        if (this == aThat) return true;

        // 使用 instanceof 检查类型
        if (!(aThat instanceof State)) return false; // 如果不是 State 类型，返回 false

        // 安全地转换为 State 类型
        State that = (State) aThat;

        // 逐字段比较
        if (this.x == that.x && this.y == that.y) return true; // 如果 x 和 y 坐标相等，返回 true
        return false; // 返回 false
    }

    // 获取状态的唯一标识符
    public String getId() {
        URLEncoder.encode(""); // 编码（未使用）
        return x + "_" + y; // 返回 x 和 y 的组合字符串
    }
}