package com.meijm.interview.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    //停车场同时容纳的车辆10
    private static Semaphore parkingSpace = new Semaphore(10);

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 30; i++) {
            service.execute(new ParkingThread(parkingSpace,(i+1)+"号"));
        }
    }
}

@Slf4j
class ParkingThread implements Runnable {
    private String name;
    //空闲停车位
    private Semaphore parkingSpace;

    public ParkingThread(Semaphore parkingSpace, String name) {
        this.name = name;
        this.parkingSpace = parkingSpace;
    }

    @Override
    public void run() {
        try {
            log.info("{}来到停车场",this.getName());
            if (parkingSpace.availablePermits() == 0) {
                log.info("{}车位不足",this.getName());
            }
            parkingSpace.acquire();//获取令牌尝试进入停车场
            log.info("{}成功进入停车场",this.getName());
            TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
            log.info("{}驶出停车场",this.getName());
            parkingSpace.release();//释放令牌，腾出停车场车位
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getName(){
        return name;
    }
}