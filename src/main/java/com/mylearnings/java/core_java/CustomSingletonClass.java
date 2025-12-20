package com.mylearnings.java.core_java;

import java.util.Objects;

public class CustomSingletonClass {

    private static volatile CustomSingletonClass instance;

    private CustomSingletonClass() {
    }

    public  static CustomSingletonClass getInstance() {
        if (Objects.isNull(instance)) {
            synchronized(CustomSingletonClass.class) {
                if (Objects.isNull(instance)) {
                    instance = new CustomSingletonClass();
                }
            }
        }
        return instance;
    }

}
