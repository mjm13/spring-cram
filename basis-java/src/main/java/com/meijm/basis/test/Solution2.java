package com.meijm.basis.test;

import java.util.ArrayList;
import java.util.List;

public class Solution2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        List<String> combinations = solution.letterCombinations("88");
        System.out.println(combinations.toString());
    }

    public List<String> letterCombinations(String digits) {
        List<String> combinations = new ArrayList<String>();
        if (digits == null || digits.isEmpty()) {
            return combinations;
        }
        Point a = new Point("a");
        Point b = new Point("b");
        Point c = new Point("c");
        Point d = new Point("d");
        Point e = new Point("e");
        a.nodes.add(e);

        return combinations;
    }

    public void dfs() {

    }


}
class Point{
    public String name ;
    public int path;
    public List<Point> nodes=new ArrayList<>();

    public Point(String name){
        this.name = name;
    }
    public Point(String name,int path){
        this.name = name;
        this.path = path;
    }
}