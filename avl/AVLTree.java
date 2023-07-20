package gad.avl;

import java.util.*;

public class AVLTree {
    private AVLTreeNode root = null;


    public AVLTree() {
    }

    public AVLTreeNode getRoot() {
        return root;
    }

    public void setRoot(AVLTreeNode root) {
        this.root = root;
    }

    public int height() {
        if (root == null) {
            return 0;
        }
        return root.height();
    }

    public boolean validAVL() {
        if (root == null) {
            return false;
        }
        List<AVLTreeNode> temp = new ArrayList<>();
        if (root.validity(root, temp,new Stack<>())) {
            return false;
        } else if (!temp.stream().sorted(Comparator.comparing(AVLTreeNode::getKey)).toList().equals(temp)) {
            return false;
        } else {
            for (int i = 0; i < temp.size(); i++) {
                int rHeight = 0;
                int lHeight = 0;
                if (temp.get(i).getRight() != null) {
                    rHeight = temp.get(i).getRight().height();
                }
                if (temp.get(i).getLeft() != null) {
                    lHeight = temp.get(i).getLeft().height();
                }

                if (Math.abs(temp.get(i).getBalance()) > 1 || rHeight - lHeight != temp.get(i).getBalance()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void insert(int key) {

        AVLTreeNode node = new AVLTreeNode(key);
        if(root==null){
            setRoot(node);
            return;
        }
            root.insert(this,root, node, new ArrayList<>(),new ArrayList<>());



    }

    public boolean find(int key) {
        AVLTreeNode current = root;
        while (current != null) {
            if (current.getKey() == key) {
                return true;
            } else if (key > current.getKey()) {
                current = current.getRight();
            } else {
                current = current.getLeft();
            }
        }
        return false;
    }

    /**
     * Diese Methode wandelt den Baum in das Graphviz-Format um.
     *
     * @return der Baum im Graphiz-Format
     */
    private String dot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {" + System.lineSeparator());
        if (root != null) {
            root.dot(sb);
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String toString() {
        return dot();
    }

    public static void main(String[] args) {
      AVLTree tree = new AVLTree();
      AVLTreeNode root = new AVLTreeNode(5);
      AVLTreeNode left = new AVLTreeNode(5);
      AVLTreeNode right = new AVLTreeNode(5);
      tree.setRoot(root);
      root.setLeft(left);
      root.setRight(right);
      right.setLeft(left);
      System.out.println(tree.validAVL());
    }
}