package com.meijm.interview.javase;

import cn.hutool.core.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * HashMap  demo
 *  1.多线程下数据异常：wrongData()
 *  2. 计算下标逻辑：keyIndex();
 *  3. 转红黑树条件 单个链表长度大于8 且数组长度小于64 treeifyBin()   TREEIFY_THRESHOLD
 *  红黑树树长度小于6会转为链表
 *
 *  默认长度为16 每次扩容为2的N次方，负载因子为 0.75
 * 阈值=新容量*负载因子
 * 新容量=旧容量*2
 *
 * 1.7 时多线程扩容可能会导致环形连表, get时会发生死循环
 */
@Slf4j
public class HashMapDemo {
    public static void main(String[] args) throws InterruptedException {

        // 多线程数据异常
//        wrongData();
        //计算下逻辑
        keyIndex();
    }

    /**
     * 计算下标逻辑
     * https://blog.csdn.net/weixin_43842753/article/details/105927912
     * 1.获得hashcode
     * 2.右移16位 记录高位数据特征  2进制中的1
     * 3.hashcode ^ hashcode高16位 记录所有数据特征，减少碰撞
     * 4 结果 & (2N次方-1) 计算出具体索引
     *  数组长度为2的N次方原因： num % 2N次方 = num &  (2N次方-1)
     *  位操作符效率高于取模
     */
    public static void keyIndex(){
        HashMap a = new HashMap();
        String  key = "edfadsfasdfasdfa";
        int hashCode  = key.hashCode();
        log.info("hashCode:{},Binary:{}",hashCode,NumberUtil.getBinaryStr(hashCode));
        int temp = hashCode>>>16;
        log.info("temp:{},Binary:{}",temp, NumberUtil.getBinaryStr(temp));
        int result = hashCode^temp;
        log.info("result:{},Binary:{}",result, NumberUtil.getBinaryStr(result));
        int length = 8;
        int index = (length-1)&result;
        int index2 = result&(length-1);
        log.info("index:{},Binary:{}",index, NumberUtil.getBinaryStr(index));
        log.info("index2:{},Binary:{}",index2, NumberUtil.getBinaryStr(index2));
        log.info("101%8:{}:Binary:{}",101%8,NumberUtil.getBinaryStr(101%8));
    }

    /**
     * 线程不安全导致数据错误
     */
    public static void wrongData() throws InterruptedException {
        int count = 100;
        CountDownLatch cdl = new CountDownLatch(count);
        HashMap<Integer, String> map = new HashMap();
        for (int i = 0; i < count; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep((int) (Math.random() * 10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.put(finalI, "");
                cdl.countDown();
            }).start();
        }
        cdl.await();
        log.info("map.size():{}", map.size());
        List<Integer> lost = new ArrayList();
        for (int i = 0; i < count; i++) {
            if (map.get(i) == null) {
                lost.add(i);
            }
        }
        // 断点表达式
        //  lost.size()>0||map.size()!=100
        log.info("lost.size():{}", lost.size());
    }
}
