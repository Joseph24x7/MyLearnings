package com.mylearnings.java.basicJava;

import lombok.Getter;


//@Value also does the below
@Getter
public final class ImmutablePerson {
    private final String name;
    private final int age;

    public ImmutablePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

