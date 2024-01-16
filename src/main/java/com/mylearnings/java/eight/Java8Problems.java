package com.mylearnings.java.eight;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8Problems {

    public static void main(String[] args) {

        // Q1 convert all the String to upper case and join them
        List<String> str = List.of("Stri1", "Str22ng2", "Stri33wg3");
        System.out.println(str.stream().map(String::toUpperCase).collect(Collectors.joining(",")));

        // Q2 Find the length of each string
        Map<String, Integer> lengthOfString = str.stream().distinct().collect(Collectors.toMap(t -> t, String::length));
        System.out.println(lengthOfString);

        // Q3 Find the occurrences of each string
        Map<String, Long> noOfOccurrences = str.stream().collect(Collectors.groupingBy(t -> t, Collectors.counting()));
        System.out.println(noOfOccurrences);

        // Q4 Given a list of integers, find out all the numbers starting with 1 using Stream functions
        List<Integer> myList = Arrays.asList(10, 15, 8, 49, 25, 98, 32);
        System.out.println(myList.stream().map(s -> s + "").filter(s -> s.startsWith("1")).toList());

        // Q5 How to find duplicate elements in a given integers list in java using Stream functions?
        List<Integer> myList2 = Arrays.asList(10, 15, 8, 49, 25, 98, 98, 32, 15);
        Set<Integer> set = new HashSet<>();
        System.out.println(myList2.stream().filter(i -> !set.add(i)).toList());

        // Q6 How to find the first duplicate elements in a given integers list in java using Stream functions
        System.out.println(myList2.stream().filter(i -> !set.add(i)).findFirst().get());

        // Q7 Given a list of integers, find the maximum and minimum value element present in it using Stream functions?
        System.out.println(myList2.stream().max(Comparator.comparingInt(i -> i)).get());
        System.out.println(myList2.stream().min(Comparator.comparingInt(i -> i)).get());

        // Q8 Given a list of integers, sort all the values present in it in descending order using Stream functions?
        System.out.println(myList2.stream().sorted((i1, i2) -> i2 - i1).toList());

        // Q9 - sum - average and count
        System.out.println(myList.stream().mapToInt(i -> i).sum());
        System.out.println(myList.stream().mapToInt(i -> i).average().orElse(0d));
        System.out.println(myList.stream().mapToInt(i -> i).count());

        // Q10 - students with distinction
        List<Student> students = Arrays.asList(new Student("john", 85.0f), new Student("Ajay", 70.0f), new Student("Mani", 85.0f), new Student("Vikram", 60.0f));
        System.out.println(students.stream().filter(s -> s.getMarks() >= 80.0f).peek(s -> s.setDistinction(true)).toList());

        // Q11 - Merge two sorted array list
        List<Integer> list1 = Arrays.asList(10, 15, 8, 49, 25, 98, 32);
        List<Integer> list2 = Arrays.asList(11, 9, 8, 19, 4, 968, 90);
        System.out.println(Stream.of(list1, list2).flatMap(Collection::stream).sorted().toList());

        // Q12 - Program to reverse each word in a given string
        String string = "My name is Joseph";
        System.out.println(Arrays.stream(string.split(" ")).map(s -> new StringBuilder(s).reverse()).toList());

        // Q13 - find non-repetitive words
        String s = "red green white red blue white black ";
        String[] arr = s.split(" ");
        List<String> lists = Arrays.asList(arr);
        Map<String, Long> counts = lists.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(counts.entrySet().stream().filter(stringLongEntry -> stringLongEntry.getValue() == 1).map(Map.Entry::getKey).toList());
        System.out.println(lists.stream().filter(i -> Collections.frequency(lists, i) == 1).toList());

    }

}

@Data
@AllArgsConstructor
class Student {

    private String name;
    private Float marks;
    private Boolean distinction;

    public Student(String name, Float marks) {
        super();
        this.name = name;
        this.marks = marks;
    }

}
