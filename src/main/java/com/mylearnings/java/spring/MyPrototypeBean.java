package com.mylearnings.java.spring;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Component
@Scope("prototype")
public class MyPrototypeBean {
    private static int instanceCounter = 0;
    private final int instanceNumber;

    public MyPrototypeBean() {
        instanceCounter++;
        instanceNumber = instanceCounter;
        System.out.println("Created instance #" + instanceNumber);
    }

}