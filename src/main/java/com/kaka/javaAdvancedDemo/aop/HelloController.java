package com.kaka.javaAdvancedDemo.aop;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {


    @ResponseBody
    @GetMapping(value = "/hello")
    @LogRecord(name = "修改订单的配送地址：", el = "#operator")
    public void hello(@RequestParam("content") String content, @RequestParam("operator") String operator) throws InterruptedException {
        System.out.println(test(content, operator));
    }

    private String test(String content, String operator) throws InterruptedException {
        Thread.sleep(1000);
        String result = content + operator;
        Message message = new Message();
        message.setContent(content);
        message.setOperator(operator);
        test1(message);
        return result;
    }

    private void test1(Message message) {
        System.out.println(JSON.toJSONString(message));
        message.setOperator("test");
        System.out.println(JSON.toJSONString(message));
    }

    @Data
    public class Message {
        private String content;
        private String operator;
    }


}
