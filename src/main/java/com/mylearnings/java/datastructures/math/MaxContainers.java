package com.mylearnings.java.datastructures.math;

public class MaxContainers {

    void main() {
        MaxContainers obj = new MaxContainers();
        System.out.println(obj.maxContainers(2, 3, 15));
    }

    public int maxContainers(int n, int w, int maxWeight) {
        return Math.min(maxWeight / w, n * n);
    }
}
