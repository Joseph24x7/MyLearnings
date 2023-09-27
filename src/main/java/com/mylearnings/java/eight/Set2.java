package com.mylearnings.java.eight;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;

public class Set2 {

    public static void main(String[] args) {

        List<Integer> myList = Arrays.asList(10, 15, 8, 49, 25, 98, 32);

        // Java 8 program to find the Minimum number of a Stream
        Integer min = myList.stream().min((i1, i2) -> i1 - i2).get();
        System.out.println(min);

        // Java 8 program to find the Maximum number of a Stream
        Integer max = myList.stream().max((i1, i2) -> i1 - i2).get();
        System.out.println(max);

        //Java 8 program to find the Sum of a Stream
        Integer sum = myList.stream().mapToInt(i -> i).sum();
        OptionalDouble avg = myList.stream().mapToInt(i -> i).average();
        Long count = myList.stream().mapToInt(i -> i).count();

        System.out.println(sum);
        System.out.println(avg.getAsDouble());
        System.out.println(count);

        //Java 8 Program to Print ten random numbers using forEach?
        new Random().ints().limit(10).forEach(i -> System.out.print(i + " : "));

    }

}
