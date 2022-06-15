package com.meijm.basis.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        List<String> combinations = solution.letterCombinations("88");
        System.out.println(combinations.toString());
    }

    public List<String> letterCombinations(String digits) {
        List<String> combinations = new ArrayList<String>();
        if (digits == null || digits.isEmpty()) {
            return combinations;
        }
        Map<Character, String> numberMap = new HashMap<>();
        numberMap.put('2', "abc");
        numberMap.put('3', "def");
        numberMap.put('4', "ghi");
        numberMap.put('5', "jkl");
        numberMap.put('6', "mno");
        numberMap.put('7', "pqrs");
        numberMap.put('8', "tuv");
        numberMap.put('9', "wxyz");
        dfs(numberMap,0,digits,new StringBuilder(),combinations);

        return combinations;
    }

    public void dfs(Map<Character, String> numberMap,int index,String digits,StringBuilder combination,List<String> combinations) {
        if(index == digits.length()){
            combinations.add(combination.toString());
            return;
        }
        Character key = digits.charAt(index);
        String letter = numberMap.get(key);
        for (int i = 0; i < letter.length(); i++) {
            combination.append(letter.charAt(i));
            dfs(numberMap,index+1,digits,combination,combinations);
            combination.deleteCharAt(combination.length()-1);
        }
        //递归操作
    }

}