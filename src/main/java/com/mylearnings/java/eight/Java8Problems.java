package com.mylearnings.java.eight;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

public class Java8Problems {

    public static void main(String[] args) {

        // Q1 convert all the String to upper case and join them
        List<String> str = List.of("Stri1", "Str22ng2", "Stri33wg3");
        System.out.println(str.stream().map(st -> st.toUpperCase()).collect(Collectors.joining(",")));

        // Q2 Find the length of each string
        Map<String, Integer> lengthOfString = str.stream().distinct().collect(Collectors.toMap(t -> t, s -> s.length()));
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

        // Q6 Given a list of integers, find the maximum and minimum value element present in it using Stream functions?
        System.out.println(myList2.stream().max((i, j) -> i - j).get());
        System.out.println(myList2.stream().min((i, j) -> i - j).get());

        // Q7 Given a list of integers, sort all the values present in it in descending order using Stream functions?
        System.out.println(myList2.stream().sorted((i1, i2) -> i2 - i1).toList());

        // Q8 - sum - average and count
        System.out.println(myList.stream().mapToInt(i -> i).sum());
        System.out.println(myList.stream().mapToInt(i -> i).average().orElse(0d));
        System.out.println(myList.stream().mapToInt(i -> i).count());

        // Q9 - students with distinction
        List<Student> students = Arrays.asList(new Student("john", 85.0f), new Student("Ajay", 70.0f), new Student("Mani", 85.0f), new Student("Vikram", 60.0f));
        System.out.println(students.stream().filter(s -> s.getMarks() >= 80.0f).peek(s -> s.setDistinction(true)).toList());
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
