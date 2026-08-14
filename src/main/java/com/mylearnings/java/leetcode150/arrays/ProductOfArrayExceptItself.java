package com.mylearnings.java.leetcode150.arrays;

public class ProductOfArrayExceptItself {

    static void main() {

        ProductOfArrayExceptItself obj = new ProductOfArrayExceptItself();
        System.out.println(java.util.Arrays.toString(obj.productExceptSelf(new int[]{1, 2, 3, 4})));

    }

    public int[] productExceptSelf(int[] nums) {

        // Left product
        // index 0 → 1 = 1
        // index 1 → 1 * 1 = 1
        // index 2 → 2 * 1 = 2
        // index 3 → 3 * 2 = 6

        // Right product
        // index 3 → 1      = 1 * leftProductIndex
        // index 2 → 4 * 1  = 4
        // index 1 → 3 * 4  = 12 *
        // index 0 → 2 * 12 = 24 * leftProductIndex(0)

        int n = nums.length;
        int[] answer = new int[n];

        // First pass: product of elements to the left
        int leftProduct = 1;

        for (int i = 0; i < n; i++) {
            answer[i] = leftProduct;
            leftProduct *= nums[i];
        }

        System.out.println(java.util.Arrays.toString(answer));

        // Second pass: product of elements to the right
        int rightProduct = 1;

        for (int i = n - 1; i >= 0; i--) {
            answer[i] *= rightProduct;
            rightProduct *= nums[i];
        }

        System.out.println(java.util.Arrays.toString(answer));

        return answer;

    }

}
