package com.kaka.javaAdvancedDemo.oom;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class OOMDemo {
    private static HashMap<String, String> HASHMAP = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        hashMapOOM();
    }
    private static void hashMapOOM() throws InterruptedException {
        //准备时间，方便观察
        TimeUnit.SECONDS.sleep(10);
        int num = 0;
        while (true) {
            //往 map 中存放 1M 大小的字符串
            String big = new String(new byte[1024 * 1024]);
            HASHMAP.put(num + "", big);
            num++;
            System.out.println(num);
        }
    }
}
