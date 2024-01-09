package com.mylearnings.java.datastructures.linkedlist;

public class MergeTwoSortedLists {

    public static void main(String[] args) {

        ListNode list1 = new ListNode();
        list1.val = 1;
        list1.next = new ListNode(4);
        list1.next.next = new ListNode(5);

        ListNode list2 = new ListNode();
        list2.val = 2;
        list2.next = new ListNode(4);
        list2.next.next = new ListNode(6);

        ListNode head = new ListNode();
        ListNode tail = head;

        while (list1 != null || list2 != null) {

            if (list1 != null && list2 != null) {

                if (list1.val < list2.val) {
                    tail.next = list1;
                    tail = tail.next;
                    list1 = list1.next;
                } else if (list1.val > list2.val) {
                    tail.next = list2;
                    tail = tail.next;
                    list2 = list2.next;
                } else {
                    tail.next = list2;
                    tail = tail.next;
                    list2 = list2.next;
                    tail.next = list1;
                    tail = tail.next;
                    list1 = list1.next;
                }

            } else if (list2 != null) {
                tail.next = list2;
                tail = tail.next;
                list2 = list2.next;
            } else {
                tail.next = list1;
                tail = tail.next;
                list1 = list1.next;
            }

        }

        while (head.next != null) {
            System.out.println(head.next.val);
            head.next = head.next.next;
        }

    }

}


class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}