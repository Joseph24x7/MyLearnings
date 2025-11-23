package com.mylearnings.java.advanced_java;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Java8Features {

    void main() {

        //FlatMap
        List<List<Integer>> nestedList = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6)
        );
        List<Integer> flatList = nestedList.stream().flatMap(Collection::stream).toList(); // Result: [1, 2, 3, 4, 5, 6]
        System.out.println(flatList);

        // toArray() Method
        List<String> wordList = Arrays.asList("Hello", "World", "Stream");
        String[] wordArray = wordList.toArray(String[]::new);
        System.out.println(Arrays.toString(wordArray));

        //peek() vs map() Method:
        List<Integer> numsList = Arrays.asList(1, 2, 3, 4);
        numsList.stream()
                .peek(n -> System.out.println("Peeked: " + n))
                .map(n -> n * 2)
                .forEach(n -> System.out.println("Mapped: " + n));

        //distinct() Method:
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 2, 3, 5);
        System.out.println(numbers.stream().distinct().toList()); // [1, 2, 3, 4, 5]

        // reduce() Method:
        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        int sum = nums.stream().reduce(0, (a, b) -> {
            System.out.println("a: " + a + ", b: " + b);
            return a + b;
        });
        System.out.println("Sum: " + sum); // Sum: 10

        //

    }
}
