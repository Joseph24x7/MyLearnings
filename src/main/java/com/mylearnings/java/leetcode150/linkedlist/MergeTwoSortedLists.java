package com.mylearnings.java.leetcode150.linkedlist;

public class MergeTwoSortedLists {

    public static void main(String[] args) {
        MergeTwoSortedLists merger = new MergeTwoSortedLists();

        ListNode list1 = new ListNode(1);
        list1.next = new ListNode(2);
        list1.next.next = new ListNode(4);

        ListNode list2 = new ListNode(1);
        list2.next = new ListNode(3);
        list2.next.next = new ListNode(4);

        ListNode mergedList = merger.mergeTwoLists(list1, list2);

        // Print merged list
        ListNode current = mergedList;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        ListNode headNode = new ListNode(-1);
        ListNode tailNode = headNode;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tailNode.next = list1;
                list1 = list1.next;
            } else {
                tailNode.next = list2;
                list2 = list2.next;
            }
            tailNode = tailNode.next;
        }

        if (list1 != null) {
            tailNode.next = list1;
        } else {
            tailNode.next = list2;
        }

        return headNode.next;

    }
}

