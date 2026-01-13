package com.mylearnings.java.java_code.core_java;

import java.util.Objects;

public class CustomSingletonClassWithDoubleCheckLocking {

    private static volatile CustomSingletonClassWithDoubleCheckLocking instance;

    private CustomSingletonClassWithDoubleCheckLocking() {
    }

    public static CustomSingletonClassWithDoubleCheckLocking getInstance() {
        if (Objects.isNull(instance)) {
            synchronized(CustomSingletonClassWithDoubleCheckLocking.class) {
                if (Objects.isNull(instance)) {
                    instance = new CustomSingletonClassWithDoubleCheckLocking();
                }
            }
        }
        return instance;
    }

}
