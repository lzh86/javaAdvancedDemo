package com.kaka.javaAdvancedDemo.design.strgegy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Controller
public class StrgegyController {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private Map<String, ConvertService> convertServiceMap;

    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(4, 8, 5000L,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5000), new ThreadPoolExecutor.CallerRunsPolicy());
    @ResponseBody
    @GetMapping(value = "/strgegy")
    public void strgegy() {
        Map<String, ConvertService> beansOfType = applicationContext.getBeansOfType(ConvertService.class);

        for (Map.Entry<String, ConvertService> entry : beansOfType.entrySet()) {
            ConvertService convertService = entry.getValue();
            String convert = convertService.convert("123");
            System.out.println(convert);
        }

        for (Map.Entry<String, ConvertService> entry : convertServiceMap.entrySet()) {
            ConvertService convertService = entry.getValue();
            String convert = convertService.convert("321");
            System.out.println(convert);
        }

    }
}
