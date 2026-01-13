package com.mylearnings.java.leetcode150.linkedlist;

public class AddTwoNumbers {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode headNode = new ListNode(-1);
        ListNode tailNode = headNode;
        int carry = 0;

        while (l1 != null || l2 != null || carry != 0) {

            int sum = carry;

            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            carry = sum / 10;
            tailNode.next = new ListNode(sum%10);
            tailNode = tailNode.next;
        }

        return headNode.next;

    }

    private int setTailNode(int sum, ListNode tailNode) {
        if (sum < 10) {
            tailNode.next = new ListNode(sum);
            tailNode = tailNode.next;
            return 0;
        } else {
            tailNode.next = new ListNode(sum % 10);
            tailNode = tailNode.next;
            return 1;
        }
    }

    public static void main(String[] args) {

        AddTwoNumbers adder = new AddTwoNumbers();

        // l1 = [9,9,9,9,9,9,9]
        ListNode l1 = new ListNode(9);
        l1.next = new ListNode(9);
        l1.next.next = new ListNode(9);
        l1.next.next.next = new ListNode(9);
        l1.next.next.next.next = new ListNode(9);
        l1.next.next.next.next.next = new ListNode(9);
        l1.next.next.next.next.next.next = new ListNode(9);

        // l2 = [9,9,9,9]
        ListNode l2 = new ListNode(9);
        l2.next = new ListNode(9);
        l2.next.next = new ListNode(9);
        l2.next.next.next = new ListNode(9);

        adder.addTwoNumbers(l1, l2);
    }


}
