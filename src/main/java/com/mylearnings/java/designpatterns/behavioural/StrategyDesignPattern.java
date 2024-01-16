package com.mylearnings.java.designpatterns.behavioural;

public class StrategyDesignPattern {

    public static void main(String[] args) {
        int[] data = {5, 2, 9, 1, 5, 6};

        SortingContext context = new SortingContext(new BubbleSort());
        context.performSort(data);

        // Change strategy at runtime
        context.setSortingStrategy(new QuickSort());
        context.performSort(data);
    }

}

class SortingContext {
    private SortingStrategy sortingStrategy;

    public SortingContext(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    public void setSortingStrategy(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    public void performSort(int[] data) {
        sortingStrategy.sort(data);
    }
}


interface SortingStrategy {
    void sort(int[] data);
}


class BubbleSort implements SortingStrategy {
    @Override
    public void sort(int[] data) {
        // Implement Bubble Sort algorithm
    }
}

class QuickSort implements SortingStrategy {
    @Override
    public void sort(int[] data) {
        // Implement Quick Sort algorithm
    }
}

