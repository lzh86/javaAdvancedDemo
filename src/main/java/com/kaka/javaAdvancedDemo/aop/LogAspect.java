package com.kaka.javaAdvancedDemo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Aspect
@Component
public class LogAspect implements ApplicationContextAware {
    private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Pointcut(value = "@annotation(com.kaka.javaAdvancedDemo.aop.LogRecord)")
    public void logAspect() {

    }

    /**
     * 定义环绕拦截
     *
     * @param joinPoint 切点
     * @return
     * @throws Throwable
     */
    @Around(value = "logAspect()")
    public Object getAroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed;
        try {
            //前置处理
            saveLogAspect(joinPoint);
            // 调用业务代码
            proceed = joinPoint.proceed();
            saveLogAspect(joinPoint);
            return proceed;
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable.getMessage());
        }


    }

    private void saveLogAspect(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        try {
            // 获取自定义注解
            LogRecord logRecord = methodSignature.getMethod().getAnnotation(LogRecord.class);
            if (Objects.nonNull(logRecord)) {
                String logData = getLogData(joinPoint, methodSignature, logRecord);
                log.info("logData = {}", logData);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 获取日志信息
     *
     * @param joinPoint       连接点
     * @param methodSignature 拦截的函数签名
     * @param logRecord       注解信息
     * @return
     */
    private String getLogData(JoinPoint joinPoint, MethodSignature methodSignature, LogRecord logRecord) {

        // 创建 context 对象
        StandardEvaluationContext context = new StandardEvaluationContext();
        // 设置bean的根对象
        context.setBeanResolver(new BeanFactoryResolver(applicationContext));
        // 获取被拦截方法的参数信息
        String[] params = parameterNameDiscoverer.getParameterNames(((MethodSignature) joinPoint.getSignature()).getMethod());
        // 把参数配置到上下文中
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(params[i], args[i]);
        }
        // 创建spel的解析对象
        SpelExpressionParser parser = new SpelExpressionParser();
        // 解析el 表达式
        String strElData = parser.parseExpression(logRecord.el()).getValue(context, String.class);
        return String.format("操作 %d ,内容 %d",logRecord.name(),strElData);
    }

}
