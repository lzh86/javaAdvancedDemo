package com.kaka.javaAdvancedDemo.spi.impl;

import com.kaka.javaAdvancedDemo.spi.DataBaseSPI;

public class DB2DataBase implements DataBaseSPI {
    @Override
    public void getConnection() {
        System.out.println("this database is db2");
    }
}
