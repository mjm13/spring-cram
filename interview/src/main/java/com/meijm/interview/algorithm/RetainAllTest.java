package com.meijm.interview.algorithm;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * https://www.cnblogs.com/coderzhw/p/12150677.html
 * 大文件求交集
 * TODO 需增加两个大型文件/数字集合
 * Created by GeekBoy on 2020/1/4.
 */
public class RetainAllTest {
    public static void main(String[] args) {
        retainAllByGuava();
        retainAllByBitSet();
        retainAllByTwoPoint();
        retainAllByStream();
        retainAllByJDK();

    }
    /**
     * 用JDK方法求交集
     */
    private static void retainAllByJDK() {
        List<Integer> txtList = getIntegerList("e:\\a.txt");
        List<Integer> txtList2 = getIntegerList("e:\\b.txt");
        long begin = System.currentTimeMillis();
        txtList.retainAll(txtList2);
        long end = System.currentTimeMillis();
        System.out.println("JDK方法耗时:" + (end - begin));
        System.out.println("交集的个数为:" + txtList.size());
    }

    /**
     * 利用guava集合求交集
     */
    private static void retainAllByGuava() {
        List<Integer> txtList = getIntegerList("e:\\a.txt");
        List<Integer> txtList2 = getIntegerList("e:\\b.txt");
        long begin = System.currentTimeMillis();
        Set<Integer> list = new HashSet<>(txtList);
        Set<Integer> list2 = new HashSet<>(txtList2);
        Sets.SetView<Integer> intersection = Sets.intersection(list, list2);
        long end = System.currentTimeMillis();
        System.out.println("guava方法耗时:" + (end - begin));
        System.out.println("交集的个数为:" + intersection.size());
    }

    /**
     * java8 stream流求交集，实质上底层是用的多线程fork/join框架
     */
    private static void retainAllByStream() {
        List<Integer> txtList = getIntegerList("e:\\a.txt");
        List<Integer> txtList2 = getIntegerList("e:\\b.txt");
        long begin = System.currentTimeMillis();
        long count = txtList.parallelStream().
                filter(item -> txtList2.contains(item)).count();
        long end = System.currentTimeMillis();
        System.out.println("stream流求交集方法耗时:" + (end - begin));
        System.out.println("交集的个数为:" + count);
    }


    /**
     * 双指针法求两个list的交集
     */
    private static void retainAllByTwoPoint() {
        List<Integer> txtList = getIntegerList("e:\\a.txt");
        List<Integer> txtList2 = getIntegerList("e:\\b.txt");
        long begin = System.currentTimeMillis();
        Collections.sort(txtList);
        Collections.sort(txtList2);
        int count = 0;
        int m = 0;
        int n = 0;
        int length = txtList.size() + txtList2.size();
        for (int i = 0; i < length; i++) {
            if (m < txtList.size() && n < txtList2.size()) {
                if (txtList.get(m).equals(txtList2.get(n))) {
                    count++;
                    m++;
                    n++;
                } else if (txtList.get(m).compareTo(txtList2.get(n)) > 0) {
                    n++;
                } else {
                    m++;
                }
            } else if (m < txtList.size()) {
                if (txtList.get(m).equals(txtList2.get(n - 1))) {
                    count++;
                }
                m++;

            } else if (n < txtList2.size()) {
                if (txtList.get(m - 1).equals(txtList2.get(n))) {
                    count++;
                }
                n++;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("双指针方法耗时:" + (end - begin));
        System.out.println("交集的个数为:" + count);
    }

    /**
     * 利用bitmap求两个list的交集
     */
    private static void retainAllByBitSet() {
        List<Integer> txtList = getIntegerList("e:\\a.txt");
        List<Integer> txtList2 = getIntegerList("e:\\b.txt");
        long begin = System.currentTimeMillis();
        BitSet bitSet = new BitSet(Collections.max(txtList));
        BitSet bitSet1 = new BitSet(Collections.max(txtList2));
        for (int i = 0; i < txtList.size(); i++) {
            bitSet.set(txtList.get(i));
        }
        for (int i = 0; i < txtList2.size(); i++) {
            bitSet1.set(txtList2.get(i));
        }
        bitSet.and(bitSet1);
        long end = System.currentTimeMillis();
        System.out.println("bitSet方法耗时:" + (end - begin));
        System.out.println("交集的个数为:" + bitSet.cardinality());
    }

    /**
     * 从文件读取两个list<Integer>
     *
     * @param filePath
     * @return
     */
    private static List<Integer> getIntegerList(String filePath) {
        InputStream inputStream = null;
        InputStreamReader is = null;
        BufferedReader br = null;
        Set<Integer> txtList = new HashSet<>();
        try {
            File txtFile = new File(filePath);
            if (txtFile.exists()) {
                inputStream = new FileInputStream(txtFile);
                is = new InputStreamReader(inputStream, "UTF-8");
                br = new BufferedReader(is);
                String str = null;
                while ((str = br.readLine()) != null) {
                    if (StrUtil.isNotBlank(str)) {
                        txtList.add(Integer.valueOf(str));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (is != null) {
                    is.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>(txtList);
    }
}