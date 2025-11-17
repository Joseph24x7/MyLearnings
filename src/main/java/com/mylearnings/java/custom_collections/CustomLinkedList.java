package com.mylearnings.java.custom_collections;

import lombok.Data;

class MainClass {
    void main() {
        CustomLinkedList linkedList = new CustomLinkedList();
        linkedList.add("element");
        linkedList.add("element2");
        linkedList.print();
    }
}

@Data
public class CustomLinkedList {

    private Node head;
    private Node tail;

    public void add(String element) {

        if (this.getTail() == null) {
            Node node = new Node(element);
            this.setHead(node);
            this.setTail(node);
        } else {
            Node temp = this.getTail();
            this.getTail().setNext(new Node(element));
            this.setTail(this.getTail().getNext());
            this.getTail().setPrev(temp);
        }
    }

    public void print() {
        Node print = head;
        while (print != null) {
            System.out.println(print.getVal());
            print = print.getNext();
        }

        print = tail;
        while (print != null) {
            System.out.println(print.getVal());
            print = print.getPrev();
        }

    }
}

@Data
class Node {

    private Node prev;
    private String val;
    private Node next;

    public Node(String val) {
        this.val = val;
    }
}
