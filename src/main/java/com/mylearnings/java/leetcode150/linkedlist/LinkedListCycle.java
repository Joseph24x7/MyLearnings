package com.mylearnings.java.leetcode150.linkedlist;

public class LinkedListCycle {

    public static void main(String[] args) {
        LinkedListCycle detector = new LinkedListCycle();

        ListNode head = new ListNode(3);
        head.next = new ListNode(2);
        head.next.next = new ListNode(0);
        head.next.next.next = new ListNode(-4);
        head.next.next.next.next = head.next; // Creates a cycle

        boolean hasCycle = detector.hasCycle(head);
        System.out.println("Linked List has cycle: " + hasCycle);
    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;

        ListNode fast = head;
        ListNode slow = head;

        while(fast!=null && fast.next!=null) {

            fast = fast.next.next;
            slow = slow.next;

            if(fast == slow) {
                return true;
            }

        }
        return false;
    }

}
