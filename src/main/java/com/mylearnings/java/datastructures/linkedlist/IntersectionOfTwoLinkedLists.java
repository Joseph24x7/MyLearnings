/*
package com.mylearnings.java.datastructures.linkedlist;

import com.mylearnings.java.leetcode150.linkedlist.ListNode;

public class IntersectionOfTwoLinkedLists {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        int size = 0;
        ListNode tempA = headA;
        ListNode tempB = headB;
        while (tempA != null) {
            size++;
            tempA = tempA.next;
        }
        while (tempB != null) {
            size--;
            tempB = tempB.next;
        }
        if (size > 0) {
            headA = advanceListByN(headA, size);
        } else {
            headB = advanceListByN(headB, -size);
        }

        while(headA != null && headB != null) {
            if(headA == headB) {
                return headA;
            }
            headA = headA.next;
            headB = headB.next;
        }

        return null;

    }

    private ListNode advanceListByN(ListNode headA, int size) {
        while (size > 0) {
            headA = headA.next;
            size--;
        }
        return headA;
    }

    public static void main(String[] args) {

        IntersectionOfTwoLinkedLists obj = new IntersectionOfTwoLinkedLists();
        ListNode headA = new ListNode(4);
        headA.next = new ListNode(1);
        headA.next.next = new ListNode(8);
        headA.next.next.next = new ListNode(4);
        headA.next.next.next.next = new ListNode(5);
        ListNode headB = new ListNode(5);
        headB.next = new ListNode(0);
        headB.next.next = new ListNode(1);
        headB.next.next.next = headA.next.next;
        System.out.println(obj.getIntersectionNode(headA, headB).val);
    }

}
*/
