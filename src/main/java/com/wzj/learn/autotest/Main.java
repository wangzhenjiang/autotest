package com.wzj.learn.autotest;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml"});
    }
}
