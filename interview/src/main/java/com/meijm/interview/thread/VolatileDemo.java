package com.meijm.interview.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 *  volatile 作用
 *  1.禁止指令重排序
 *  2.保证内存可见性，内容修改后立即回写主内存，让其它线程查看到变化
 *   例子中无效代码删除后无法达到效果
 */
@Slf4j
public class VolatileDemo {
  //切换变量查看效果，增加volatile 后不会导致死循环
//  private static volatile boolean  flag = false;
  private static  boolean flag = false;
  public static void main(String[] args) {
    Map<String,String> map =  new HashMap<>();
    new Thread(new Runnable(){
      @Override
      public void run() {
        for (int i = 1; i <= 2000; i++){
          log.info("value - " + i);
          map.put("value - " + i,"value - " + i);
        }
        // changing status flag
        flag = true;
        log.info("status flag changed :{}" , flag );
      }			
    },"t1").start();
    // Thread-2
    new Thread(new Runnable(){		
      @Override
      public void run() {
        log.info("开始t2");
        int i = 1;
        while (!flag){
          i++;
        }
        log.info("Start other processing :{}" , i);
      }
    },"t2").start();
  }
}