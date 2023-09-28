package com.mylearnings.java.eight;

import java.util.Arrays;
import java.util.List;

public class Set2 {

    public static void main(String[] args) {

        List<Integer> myList = Arrays.asList(10, 15, 8, 49, 25, 98, 32);

        Integer sum = myList.stream().mapToInt(i -> i).sum();
        Double avg = myList.stream().mapToInt(i -> i).average().orElse(0d);
        Long count = myList.stream().mapToInt(i -> i).count();

        System.out.println(sum);
        System.out.println(avg);
        System.out.println(count);

    }

}
