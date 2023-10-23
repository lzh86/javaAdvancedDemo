package com.kaka.javaAdvancedDemo.proxy;


public class UserServiceImpl implements UserService{
    @Override
    public void add() {
        System.out.println("实现类方法被调用");
    }
}
