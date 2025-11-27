package com.mylearnings.java.advanced_java;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

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

        // Chain of Predicates:
        List<String> strings = Arrays.asList("apple", "banana", "avocado", "blueberry", "apricot");
        Predicate<String> startsWithA = s -> s.startsWith("a");
        Predicate<String> lengthGreaterThan5 = s -> s.length() > 5;
        Predicate<String> combinedPredicateUsingAnd = startsWithA.and(lengthGreaterThan5);
        Predicate<String> combinedPredicateUsingOr = startsWithA.or(lengthGreaterThan5);
        Predicate<String> negatedPredicate = combinedPredicateUsingAnd.negate();
        System.out.println(strings.stream().filter(negatedPredicate).filter(combinedPredicateUsingOr).toList()); // [apple, banana, blueberry]

        // Difference between andThen and compose methods in Function interface:
        List<Integer> values = Arrays.asList(1, 2, 3);
        Function<Integer, Integer> add2 = x -> x + 2;
        Function<Integer, Integer> multiplyBy3 = x -> x * 3;

        // independent usage
        System.out.println(add2.andThen(multiplyBy3).apply(5)); // Result: 21
        System.out.println(add2.compose(multiplyBy3).apply(5)); // Result: 17

        List<Integer> resultAndThen = values.stream()
                .map(add2.andThen(multiplyBy3))
                .toList(); // ((x + 2) * 3)
        List<Integer> resultCompose = values.stream()
                .map(add2.compose(multiplyBy3))
                .toList(); // ((x * 3) + 2)
        System.out.println("Result using andThen: " + resultAndThen); // [9, 12, 15]
        System.out.println("Result using compose: " + resultCompose); // [5, 8, 11]

        // Using Consumer's default method 'andThen':
        Consumer<Integer> printConsumer = x -> System.out.println("Value: " + x);
        Consumer<Integer> squareConsumer = x -> System.out.println("Square: " + (x * x));
        Consumer<Integer> combinedConsumer = printConsumer.andThen(squareConsumer);
        values.forEach(combinedConsumer); // Prints value and its square for each element

    }
}
