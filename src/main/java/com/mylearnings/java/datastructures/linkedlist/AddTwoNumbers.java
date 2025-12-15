package com.mylearnings.java.datastructures.linkedlist;

import java.math.BigInteger;

public class AddTwoNumbers {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        BigInteger s = new BigInteger("0");
        BigInteger t = new BigInteger("0");
        while (l1 != null) {
            s = s.multiply(BigInteger.valueOf(10)).add(BigInteger.valueOf(l1.val));
            l1 = l1.next;
        }
        while (l2 != null) {
            t = t.multiply(BigInteger.valueOf(10)).add(BigInteger.valueOf(l2.val));
            l2 = l2.next;
        }
        BigInteger sum = s.add(t);
        String sumStr = sum.toString();
        int index = sumStr.length() - 1;
        ListNode dummyHead = new ListNode(Character.getNumericValue(sumStr.charAt(index)));
        ListNode newNode = dummyHead;
        index--;
        while(index >= 0) {
            newNode.next = new ListNode(Character.getNumericValue(sumStr.charAt(index)));
            newNode = newNode.next;
            index--;
        }
        return dummyHead;

    }

    public static void main(String[] args) {
        AddTwoNumbers obj = new AddTwoNumbers();
        ListNode l1 = new ListNode(9);
        l1.next = new ListNode(9);
        l1.next.next = new ListNode(9);
        l1.next.next.next = new ListNode(9);
        l1.next.next.next.next = new ListNode(9);
        l1.next.next.next.next.next = new ListNode(9);
        l1.next.next.next.next.next.next = new ListNode(9);


        ListNode l2 = new ListNode(9);
        l2.next = new ListNode(9);
        l2.next.next = new ListNode(9);
        l2.next.next.next = new ListNode(9);
        ListNode result = obj.addTwoNumbers(l1, l2);
        while (result != null) {
            System.out.println(result.val);
            result = result.next;
        }
    }

}
