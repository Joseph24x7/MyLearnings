package com.mylearnings.java.collections;

import lombok.Data;

class BSTMainClass {

    public static void main(String[] args) {

        BinaryTree bst = new BinaryTree();
        bst.add(5);
        bst.add(3);
        bst.add(8);
        bst.add(1);
        bst.add(4);

        System.out.print("In-order traversal: ");
        bst.printInOrder();
        System.out.println();
        System.out.print("Pre-order traversal: ");
        bst.printPreOrder();
        System.out.println();
        System.out.print("Post-order traversal: ");
        bst.printPostOrder();

    }

}

@Data
public class BinaryTree {
    private TreeNode head;

    public void add(int num) {
        if (head == null) {
            head = new TreeNode(num);
        } else {
            head.insert(num);
        }
    }

    public void printInOrder() {
        if (head != null) {
            head.inOrderTraversal();
        }
    }

    public void printPreOrder() {
        if (head != null) {
            head.preOrderTraversal();
        }
    }

    public void printPostOrder() {
        if (head != null) {
            head.postOrderTraversal();
        }
    }


}


@Data
class TreeNode {

    private TreeNode left;
    private Integer val;
    private TreeNode right;

    public TreeNode(Integer val) {
        this.val = val;
    }

    public void insert(int num) {
        if (num < val) {
            if (left == null) {
                left = new TreeNode(num);
            } else {
                left.insert(num);
            }
        } else {
            if (right == null) {
                right = new TreeNode(num);
            } else {
                right.insert(num);
            }
        }
    }

    public void inOrderTraversal() {
        if (left != null) {
            left.inOrderTraversal();
        }
        System.out.print(val + " ");
        if (right != null) {
            right.inOrderTraversal();
        }
    }

    public void preOrderTraversal() {
        System.out.print(val + " ");
        if (left != null) {
            left.preOrderTraversal();
        }
        if (right != null) {
            right.preOrderTraversal();
        }
    }

    public void postOrderTraversal() {
        if (left != null) {
            left.postOrderTraversal();
        }
        if (right != null) {
            right.postOrderTraversal();
        }
        System.out.print(val + " ");
    }
}