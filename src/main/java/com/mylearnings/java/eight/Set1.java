package com.mylearnings.java.eight;

import java.util.*;
import java.util.stream.Collectors;

public class Set1 {

    public static void main(String[] args) {

        List<Integer> myList = Arrays.asList(10, 15, 8, 49, 25, 98, 32);

        //Q1 Given a list of integers, find out all the even numbers exist in the list using Stream functions?
        System.out.println(myList.stream().filter(t -> t % 2 == 0).toList());

        //Q2 Given a list of integers, find out all the numbers starting with 1 using Stream functions
        System.out.println(myList.stream().map(s -> s + "").filter(s -> s.startsWith("1")).toList());

        List<Integer> myList2 = Arrays.asList(10, 15, 8, 49, 25, 98, 98, 32, 15);
        //Q3 How to find duplicate elements in a given integers list in java using Stream functions?
        Set<Integer> set = new HashSet<>();
        System.out.println(myList2.stream().filter(i -> !set.add(i)).toList());

        //Q4 Given the list of integers, find the first element of the list using Stream functions?
        System.out.println(myList2.stream().findFirst().get());

        //Q5 Given a list of integers, find the maximum value element present in it using Stream functions?
        //System.out.println(myList2.stream().max((i, j) -> i - j).get());
        System.out.println(myList2.stream().max(Comparator.comparingInt(i -> i)).get());

        //Q6 Given a list of integers, find the minimum value element present in it using Stream functions?
        //System.out.println(myList2.stream().min((i, j) -> i - j).get());
        System.out.println(myList2.stream().min(Comparator.comparingInt(i -> i)).get());

        String input = "Java Hungry Blog Alive is Awesome";
        //Q7 Given a String, find the first repeated character in it using Stream functions?
        Set<Character> set2 = new HashSet<>();
        System.out.println(input.chars().mapToObj(e -> (char) e).filter(i -> !set2.add(i)).findFirst());

        //Q8 Given a list of integers, sort all the values present in it using Stream functions?
        System.out.println(myList2.stream().sorted(Integer::compare).collect(Collectors.toList()));

        //Q9 Given a list of integers, sort all the values present in it in descending order using Stream functions?
        System.out.println(myList2.stream().sorted((i1, i2) -> i2 - i1).collect(Collectors.toList()));

        //Q10 Java 8 Program to Print ten random numbers using forEach?
        new Random().ints().limit(10).forEach(System.out::print);

    }

}
