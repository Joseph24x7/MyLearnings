package com.mylearnings.java.datastructures.two_pointers;

public class ContainerWithMostWater {

    void main() {
        ContainerWithMostWater obj = new ContainerWithMostWater();
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(obj.maxArea(height));
    }

    public int maxArea(int[] height) {
        int start = 0, end = height.length - 1;
        int max = Integer.MIN_VALUE;
        while (start < end) {
            int min = Math.min(height[start], height[end]);
            int volume = (end - start) * min;
            if (volume > max) {
                max = volume;
            }
            if (min == height[start]) {
                start++;
            } else {
                end--;
            }
        }
        return max;
    }

}
