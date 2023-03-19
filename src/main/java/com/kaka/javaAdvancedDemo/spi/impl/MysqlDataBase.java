package com.kaka.javaAdvancedDemo.spi.impl;

import com.kaka.javaAdvancedDemo.spi.DataBaseSPI;

public class MysqlDataBase implements DataBaseSPI {
    @Override
    public void getConnection() {
        System.out.println("this is mysql database");
    }
}
