package com.mylearnings.java.custom_collections;

import lombok.Data;

class BSTMainClass {

    public static void main(String[] args) {

        BinaryTree bst = new BinaryTree();
        bst.add(5);
        bst.add(3);
        bst.add(8);
        bst.add(1);
        bst.add(2);

        BinaryTree bst2 = new BinaryTree();
        bst2.add(5);
        bst2.add(3);
        bst2.add(8);
        bst2.add(1);
        bst2.add(2);

        boolean areBinaryTreeIdentical = areBinaryTreeIdentical(bst.getHead(), bst2.getHead());
        System.out.println("areBinaryTreeIdentical: " + areBinaryTreeIdentical);

        System.out.print("In-order traversal: ");
        inOrderTraversal(bst.getHead());
        System.out.println();
        System.out.print("Pre-order traversal: ");
        preOrderTraversal(bst.getHead());
        System.out.println();
        System.out.print("Post-order traversal: ");
        postOrderTraversal(bst.getHead());
    }

    private static boolean areBinaryTreeIdentical(TreeNode bst1, TreeNode bst2) {
        if (bst1 == null && bst2 == null) {
            return true;
        } else if (bst1 == null || bst2 == null) {
            return false;
        } else if (!bst1.getVal().equals(bst2.getVal())) {
            return false;
        }
        boolean leftIdentical = areBinaryTreeIdentical(bst1.getLeft(), bst2.getLeft());
        boolean rightIdentical = areBinaryTreeIdentical(bst1.getRight(), bst2.getRight());
        return leftIdentical && rightIdentical;
    }

    private static void inOrderTraversal(TreeNode node) {
        if (node != null) {
            inOrderTraversal(node.getLeft());
            System.out.print(node.getVal() + " ");
            inOrderTraversal(node.getRight());
        }
    }

    private static void preOrderTraversal(TreeNode node) {
        if (node != null) {
            System.out.print(node.getVal() + " ");
            preOrderTraversal(node.getLeft());
            preOrderTraversal(node.getRight());
        }
    }

    private static void postOrderTraversal(TreeNode node) {
        if (node != null) {
            postOrderTraversal(node.getLeft());
            postOrderTraversal(node.getRight());
            System.out.print(node.getVal() + " ");
        }
    }

}

@Data
public class BinaryTree {
    private TreeNode head;

    public void add(Integer num) {
        if (head == null) {
            head = new TreeNode(num);
        } else {
            insert(head, num);
        }
    }

    private void insert(TreeNode node, Integer num) {
        if (num < node.getVal()) {
            if (node.getLeft() == null) {
                node.setLeft(new TreeNode(num));
            } else {
                insert(node.getLeft(), num);
            }
        } else {
            if (node.getRight() == null) {
                node.setRight(new TreeNode(num));
            } else {
                insert(node.getRight(), num);
            }
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
}
