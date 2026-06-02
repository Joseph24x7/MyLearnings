package com.mylearnings.java.datastructures.hashtable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequentElements {

    public int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> count = new HashMap<>();
        for(int num : nums) {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }

        return count.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(k)
                .mapToInt(Map.Entry::getKey)
                .toArray();

    }

    public static void main(String[] args) {
        TopKFrequentElements topKFrequentElements = new TopKFrequentElements();
        int[] nums = {1,1,1,2,2,3};
        int k = 2;
        int[] result = topKFrequentElements.topKFrequent(nums, k);
        for(int num : result) {
            System.out.print(num + " ");
        }
    }

}

class UsingPriorityQueue {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Integer> heap =
                new PriorityQueue<>((a, b) ->
                        Integer.compare(frequencyMap.get(a), frequencyMap.get(b)));
        for (Integer keys : frequencyMap.keySet()) {
            heap.offer(keys);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        int[] arr = new int[k];
        int index = 0;

        for (Integer queueResult : heap) {
            arr[index++] = queueResult;
            if (index == k) break;
        }

        return arr;
    }
}
