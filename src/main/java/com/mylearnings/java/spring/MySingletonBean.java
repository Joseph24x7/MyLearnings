package com.mylearnings.java.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MySingletonBean {
    private final MyPrototypeBean myPrototypeBean;

    public void doSomething() {
        // You can use the prototype bean here
        int instanceNumber = myPrototypeBean.getInstanceNumber();
        System.out.println("Singleton bean is using prototype bean with instance number: " + instanceNumber);
    }
}
