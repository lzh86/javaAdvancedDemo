package com.kaka.javaAdvancedDemo;

import com.kaka.javaAdvancedDemo.spi.DataBaseSPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.*;

@SpringBootApplication
public class JavaAdvancedDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaAdvancedDemoApplication.class, args);

		// Spring SPI
		List<DataBaseSPI> dataBaseSPIs = SpringFactoriesLoader.loadFactories(DataBaseSPI.class, Thread.currentThread().getContextClassLoader());

		for (DataBaseSPI datBaseSPI : dataBaseSPIs) {
			datBaseSPI.getConnection();
		}

		//JDK SPI
		ServiceLoader<DataBaseSPI> JDKSPs = ServiceLoader.load(DataBaseSPI.class);
		for (DataBaseSPI datBaseSPI : JDKSPs) {
			datBaseSPI.getConnection();
		}


	}

}
