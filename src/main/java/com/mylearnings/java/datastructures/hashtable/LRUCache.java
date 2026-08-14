package com.mylearnings.java.datastructures.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 146 - LRU Cache
 * <p>
 * Design a data structure that follows Least Recently Used (LRU) eviction policy.
 * <p>
 * Approach: HashMap + Doubly Linked List
 * - HashMap  → O(1) key lookup (key → node)
 * - DLL      → O(1) insert/remove, tracks recency (MRU at head, LRU at tail)
 * <p>
 * Time:  O(1) for both get() and put()
 * Space: O(capacity)
 * <p>
 * Layout:
 * DummyHead ↔ [MRU] ↔ ... ↔ [LRU] ↔ DummyTail
 * ↑ move here on access   ↑ evict from here
 */
public class LRUCache {

    private final int capacity;
    private final Map<Integer, Node> map;  // key → node reference
    private final Node head, tail;          // dummy sentinels (avoid null checks)
    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(0, 0);  // MRU side sentinel
        tail = new Node(0, 0);  // LRU side sentinel
        head.next = tail;
        tail.prev = head;
    }

    // ── Test ──────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(3);

        cache.put(1, 1);         // cache: [1]
        cache.put(2, 2);         // cache: [2, 1]
        cache.put(3, 3);         // cache: [3, 2, 1]
        System.out.println(cache.get(1));  // 1  → cache: [1, 3, 2]
        cache.put(4, 4);         // evicts 2 (LRU) → cache: [4, 1, 3]
        System.out.println(cache.get(2));  // -1 (evicted)
        System.out.println(cache.get(3));  // 3  → cache: [3, 4, 1]
        System.out.println(cache.get(4));  // 4  → cache: [4, 3, 1]
    }

    // ── get: O(1) ─────────────────────────────────────────────────────────────
    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        moveToFront(node);   // mark as most recently used
        return node.val;
    }

    // ── put: O(1) ─────────────────────────────────────────────────────────────
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            // Update existing
            Node node = map.get(key);
            node.val = value;
            moveToFront(node);
        } else {
            if (map.size() == capacity) {
                // Evict LRU (node just before tail)
                Node lru = tail.prev;
                remove(lru);
                map.remove(lru.key);
            }
            Node newNode = new Node(key, value);
            insertAtFront(newNode);
            map.put(key, newNode);
        }
    }

    // ── Linked List helpers ────────────────────────────────────────────────────

    /**
     * Remove node from its current position in the list
     */
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    /**
     * Insert node right after dummy head (MRU position)
     */
    private void insertAtFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void moveToFront(Node node) {
        remove(node);
        insertAtFront(node);
    }

    // ── Doubly Linked List Node ────────────────────────────────────────────────
    private static class Node {
        int key, val;
        Node prev, next;

        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

}
