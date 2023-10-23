package com.kaka.javaAdvancedDemo.concurrent;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {
    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(4, 8, 5000L,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5000), new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("test1", "hello");
        map.put("test2", "hello");

        ArrayList<String> list = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(map.size());

        if (StringUtils.isNotBlank(map.get("test1"))) {
            poolExecutor.execute(() -> {
                list.add("123");
                countDownLatch.countDown();
            });
        }

        if (StringUtils.isNotBlank(map.get("test2"))) {
            poolExecutor.execute(() -> {
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                list.add("456");
                countDownLatch.countDown();
            });
        }

        countDownLatch.await(5, TimeUnit.SECONDS);
        System.out.println(list);

        poolExecutor.shutdown();
    }


}
