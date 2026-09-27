package com.mylearnings.java;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUCache {

    private final int maxSize;
    private final LinkedList<Node> linkedList = new LinkedList<>();
    private final Map<Integer, Node> hashMap = new HashMap<>();

    public LRUCache(int maxSize) {
        this.maxSize = maxSize;
        Node head = new Node(-1, -1);
        Node tail = new Node(-2, -2);
        head.next = tail;
        linkedList.addFirst(head);
        linkedList.addLast(tail);
    }

    public void insertData(Integer key, Integer value) {

        Node head = linkedList.getFirst();
        Node tail = linkedList.getLast();

        if (hashMap.size() >= maxSize) {

            Node last = tail.prev;

            hashMap.remove(last.getKey());

            last.prev.next = tail;
            tail.prev = last.prev;
        }

        if (head.next == tail) {

            Node node = new Node(key, value);

            head.next = node;
            node.prev = head;

            node.next = tail;
            tail.prev = node;

            hashMap.put(key, node);

        } else if (hashMap.containsKey(key)) {

            Node existing = hashMap.get(key);

            existing.value = value;

            Node prev = existing.prev;
            Node next = existing.next;

            prev.next = next;
            next.prev = prev;

            Node first = head.next;

            first.prev = existing;
            head.next = existing;

            existing.prev = head;
            existing.next = first;

            hashMap.put(key, existing);

        } else {

            Node node = new Node(key, value);

            node.next = head.next;
            node.prev = head;

            head.next.prev = node;
            head.next = node;

            hashMap.put(key, node);
        }
    }

    public Integer get(Integer key) {

        Node head = linkedList.getFirst();

        if (hashMap.containsKey(key)) {

            Node existing = hashMap.get(key);

            Node existingPrev = existing.prev;
            Node existingNext = existing.next;

            existingPrev.next = existingNext;
            existingNext.prev = existingPrev;

            existing.next = head.next;
            existing.prev = head;

            head.next.prev = existing;
            head.next = existing;

        }

        return hashMap.get(key).value;
    }

    public boolean remove(Integer key) {

        Node existing = hashMap.get(key);

        Node existingPrev = existing.prev;
        Node existingNext = existing.next;

        existingPrev.next = existingNext;
        existingNext.prev = existingPrev;

        hashMap.remove(key);

        return true;
    }

    public void print() {

        Node head = linkedList.getFirst();

        while (head != null) {

            System.out.println(head.key + ":" + head.value);

            head = head.next;

        }

    }

}

@Getter
@Setter
class Node {

    int key;
    int value;

    Node prev;
    Node next;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }

}

class MainClass {

    static void main() {

        LRUCache lruCache = new LRUCache(10);
        lruCache.insertData(1, 10);
        lruCache.insertData(2, 101);
        lruCache.insertData(4, 10);
        lruCache.insertData(2, 10);

        lruCache.get(1);

        lruCache.remove(2);

        lruCache.print();

    }

}