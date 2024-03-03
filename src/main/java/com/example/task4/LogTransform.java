package com.example.task4;

import com.opencsv.CSVWriter;
import lombok.Getter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class LogTransform implements BeanPostProcessor {


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof Checker) {
            Object proxy = this.getTargetObject(bean);
            LogTransformation annotation = AnnotationUtils.getAnnotation(proxy.getClass(), LogTransformation.class);

            if (annotation != null) {
                return proxiedBean((Checker) bean, annotation.value(), proxy.getClass().getName());
            }
        }
        return bean;
    }

    private Object getTargetObject(Object proxy) throws BeansException {
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            try {
                return ((Advised) proxy).getTargetSource().getTarget();
            } catch (Exception e) {
                throw new FatalBeanException("Error getting target of JDK proxy", e);
            }
        }
        return proxy;
    }

    private Object proxiedBean(Checker bean, String logName, String className) {
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvice(new LoggingInterceptor(logName, className));
        return proxyFactory.getProxy();
    }

    @Getter
    private static class LoggingInterceptor implements MethodInterceptor {

        String className;
        String logName;

        public LoggingInterceptor(String logName, String className) {
            this.className = className;
            this.logName = logName;
        }

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            System.out.println("Before greeting");
            HashMap<String, String> param = (HashMap<String, String>) methodInvocation.getArguments()[0];
            String[] logRecord = new String[12];
            logRecord[0] = (LocalDateTime.now().toString());
            logRecord[1] = (getClassName());
            logRecord[2] = ("Values before:");
            logRecord[3] = (param.get("fileName"));
            logRecord[4] = (param.get("fio"));
            logRecord[5] = (param.get("username"));
            logRecord[6] = (param.get("application"));
            logRecord[7] = ("Values after:");
            Object returnValue = methodInvocation.proceed();
            if(returnValue != null) {
                var retParam = (HashMap<String, String>) returnValue;
                logRecord[8] = (retParam.get("fileName"));
                logRecord[9] = (retParam.get("fio"));
                logRecord[10] = (retParam.get("username"));
                logRecord[11] = retParam.get("application");
            }
            try (CSVWriter writer = new CSVWriter(new FileWriter("./" + getLogName(), true))) {
                {
                    writer.writeNext(logRecord);
                }
            }
            System.out.println("After greeting");
            return returnValue;
        }
    }
}