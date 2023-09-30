package com.mylearnings.java.eight;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Java8Features {

    public static void main(String[] args) {

        //Optional
        // If you pass a null value to Optional.of, it will throw a NullPointerException.
        Optional<String> optionalValue = Optional.of("Hello");
        // creates Optional instance if value is non-null, or an empty Optional if the value is null.
        Optional<String> optionalValueOrEmpty = Optional.ofNullable(null);

        //Examples of some predefined functional interfaces
        //Represents an operation that takes an input argument of type T and returns no result.
        Consumer<String> printUpperCase = str -> System.out.println(str.toUpperCase());

        //It does not take any input but produces a result of type T.
        Supplier<Integer> getRandomNumber = () -> new Random().nextInt();

        //Represents a function that takes an input of type T and produces a result of type R.
        Function<String, Integer> strLength = str -> str.length();

        //Represents a predicate (boolean-valued function) that takes an input of type T.
        Predicate<Integer> isEven = num -> num % 2 == 0;

        //Map vs
        List<String> words = Arrays.asList("hello", "world");
        List<Integer> wordLengths = words.stream().map(word -> word.length()).toList(); // Result: [5, 5]

        //FlatMap
        List<List<Integer>> nestedList = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6)
        );
        List<Integer> flatList = nestedList.stream().flatMap(list -> list.stream()).toList(); // Result: [1, 2, 3, 4, 5, 6]
        List<Integer> flatList2 = nestedList.stream().flatMap(list -> list.stream().filter(i -> i > 2).toList().stream()).toList(); // Result: [3, 4, 5, 6]

    }
}
