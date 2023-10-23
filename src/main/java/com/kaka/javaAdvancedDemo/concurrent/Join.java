package com.kaka.javaAdvancedDemo.concurrent;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.TreeBidiMap;

public class Join {
    public static void main(String[] args)  {
        BidiMap<String, String> bidi = new TreeBidiMap<>();
        bidi.put("One", "1");
        bidi.put("Two", "2");
        bidi.put("Three", "3");
        System.out.println(bidi.get("One"));
        System.out.println(bidi.getKey("1"));
        System.out.println("Original Map: " + bidi);
        bidi.removeValue("1");
        System.out.println("Modified Map: " + bidi);
        BidiMap<String, String> inversedMap = bidi.inverseBidiMap();
        System.out.println("Inversed Map: " + inversedMap);


        Object oo = new Object();

        MyThread thread1 = new MyThread("thread1 -- ");
        //oo = thread1;
        thread1.setOo(oo);
        thread1.start();

        synchronized (oo) {  // 这里用oo或thread1/this
            for (int i = 0; i < 100; i++) {
                if (i == 20) {
                    try {
                        oo.wait(0);
                        thread1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " -- " + i);
            }
        }
    }

}


class MyThread extends Thread {

    private String name;
    private Object oo;

    public void setOo(Object oo) {
        this.oo = oo;
    }

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        synchronized (this) { // 这里用oo或this，效果不同
            for (int i = 0; i < 100; i++) {
                System.out.println(name + i);
            }
        }
    }
}
