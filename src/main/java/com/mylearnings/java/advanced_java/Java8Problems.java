package com.mylearnings.java.advanced_java;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Java8Problems {

    void main() {

        // Q1 convert all the String to upper case and join them
        List<String> strings = List.of("apple", "banana", "cherry", "date", "apple", "banana");
        System.out.println("------Q1------");
        System.out.println(strings.parallelStream().map(String::toUpperCase).collect(Collectors.joining(",")));
        System.out.println("--------------");

        // Q2 Find the occurrences of each string
        System.out.println("------Q2------");
        System.out.println(strings.parallelStream().collect(Collectors.groupingBy(s -> s, Collectors.counting())));
        System.out.println("--------------");

        // Q3 Given a list of integers, find the maximum and minimum value element present in it using Stream functions?
        System.out.println("------Q3------");
        List<Integer> integers = List.of(5, 3, 8, 1, 2, 7, 4, 6);

        System.out.println("Option 1:");
        System.out.println(integers.stream().min(Comparator.naturalOrder()).get());
        System.out.println(integers.stream().max(Integer::compareTo).get());

        System.out.println("Option 2:");
        List<Integer> sorted = integers.stream().sorted().toList();
        System.out.println(sorted.getFirst());
        System.out.println(sorted.getLast());

        System.out.println("--------------");

        // Q4 Given a list of integers, sort all the values present in it in descending order using Stream functions?
        System.out.println("------Q4------");
        System.out.println("Option 1:");
        System.out.println(integers.stream().sorted((i1, i2) -> i2 - i1).toList());
        System.out.println("Option 2:");
        System.out.println(integers.stream().sorted(Comparator.reverseOrder()).toList());
        System.out.println("--------------");

        // Q5 - sum - average and count
        System.out.println("------Q5------");
        System.out.println("Sum: " + integers.stream().mapToInt(Integer::intValue).sum());
        System.out.println("Average: " + integers.stream().mapToInt(Integer::intValue).average().orElse(0));
        System.out.println("--------------");

        // Q6 - Merge two sorted array list
        System.out.println("------Q6------");
        List<List<Integer>> listOfLists = List.of(List.of(1, 3, 5, 7, 9), List.of(2, 4, 6, 8, 10));
        System.out.println(listOfLists.stream().flatMap(Collection::stream).sorted().toList());
        System.out.println("--------------");

        // Q7 - Program to reverse each word in a given string
        System.out.println("------Q7------");
        String sentence = "Hello World from Java Streams";
        String reversedWords = Arrays.stream(sentence.split(" ")).map(word -> new StringBuilder(word).reverse().toString())
                .collect(Collectors.joining(" "));
        System.out.println(reversedWords);
        System.out.println("--------------");

        // Q10 - students with distinction
        // Q13 - find non-repetitive words
        // Q14 - top 3 students by marks
        // Q15 - sort map by key and by value
        // Q16 - Group students by marks

    }

}