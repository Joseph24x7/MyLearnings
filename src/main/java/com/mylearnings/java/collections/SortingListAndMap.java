package com.mylearnings.java.collections;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SortingListAndMap {

    public static void main(String[] args) {

        List<Students> students = List.of(new Students(1, 100), new Students(2, 80),
                new Students(3, 85), new Students(4, 92), new Students(5, 50));

        List<Students> sortedStudentsFirstThree = students.stream().sorted((s1, s2) -> s2.getMarks() - s1.getMarks())
                .limit(3).skip(0)
                .toList();
        System.out.println(sortedStudentsFirstThree);
        System.out.println("----------");

        Map<Students, Integer> employeeMap = Map.of(
                new Students(4, 100), 20,
                new Students(3, 80), 25,
                new Students(5, 70), 30,
                new Students(2, 90), 15,
                new Students(6, 85), 10);

        Map<Students, Integer> sortedMapUsingMarks = employeeMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(Students::getMarks)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        Map<Students, Integer> descSortedMapUsingValue = employeeMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((v1, v2) -> v2 - v1))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        System.out.println(sortedMapUsingMarks);
        System.out.println("----------");
        System.out.println(descSortedMapUsingValue);
        System.out.println("----------");

        List<Students> students2 = List.of(new Students(1, 75, "gabc"), new Students(2, 80, "bcd"),
                new Students(3, 85, "fcd"), new Students(4, 92, "fcd3"), new Students(5, 50, "awwa"));

        List<Students> sortedStudentsWithMoreMarks = students2.stream().sorted(Comparator.comparing(Students::getName)).filter(e -> e.getMarks() > 70).toList();
        System.out.println(sortedStudentsWithMoreMarks);

    }


}

@Data
@AllArgsConstructor
class Students {

    private int id;
    private int marks;
    private String name;

    public Students(int id, int marks) {
        this.id = id;
        this.marks = marks;
    }

}