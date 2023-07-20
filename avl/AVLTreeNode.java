package gad.avl;

import java.util.List;
import java.util.Stack;

public class AVLTreeNode {
    private int key;
    private int balance = 0;
    private AVLTreeNode left = null;
    private AVLTreeNode right = null;

    public AVLTreeNode(int key) {
        this.key = key;
    }

    public AVLTreeNode getLeft() {
        return left;
    }

    public AVLTreeNode getRight() {
        return right;
    }

    public int getBalance() {
        return balance;
    }

    public int getKey() {
        return key;
    }

    public void setLeft(AVLTreeNode left) {
        this.left = left;
    }

    public void setRight(AVLTreeNode right) {
        this.right = right;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int height() {
        if(left == null && right == null){
            return 1;
        }else if(left == null || right == null){
            return 2;
        }else {
            return 1+Math.max(left.height(), right.height());
        }
    }
  public boolean validity(AVLTreeNode node, List<AVLTreeNode> value, Stack<AVLTreeNode> treeNodes){
      if (value.contains(node) || treeNodes.contains(node)) {
          value.add(null);
          return false;
      }
      treeNodes.push(node);
             if(node.left != null) {
                  validity(node.left, value,treeNodes);
             }
             node = treeNodes.pop();
            value.add(node);
            if(node.right != null) {
                validity(node.getRight(),value,treeNodes);
            }

        return value.contains(null);
  }
  public void insert(AVLTree tree,AVLTreeNode initial,AVLTreeNode node,List<Boolean> path, List<AVLTreeNode> i){
        AVLTreeNode current = initial;

        while(current != null){
            i.add(0,current);
            if(current.getKey()<node.getKey()){
                path.add(0,true);
                if(current.right == null ){
                    current.right = node;
                    break;
                }else {
                current = current.right;}
            }else{
                path.add(0,false);
                if(current.left == null){
                    current.left = node;
                    break;
                }else
                current = current.left;
            }
        }

            i.forEach(n -> n.updateBlance());
        i.add(0,node);

         for(int j = 0;j<i.size();j++){
             if(Math.abs(i.get(j).getBalance())>1){
                if(j == i.size()-1){
                     rotate(tree,null,i.subList(j-2,j+1), path.subList(path.size() - 2, path.size() ),true);}
                else{
                    rotate(tree,i.get(j+1),i.subList(j-2,j+1),path.subList(j - 2, j+1 ),false);}
                i.forEach(n->n.updateBlance());
                 break;
             }
         }

  }
    private void rotate(AVLTree tree,AVLTreeNode parent,List<AVLTreeNode> nodes,List<Boolean> path,Boolean isRoot){
        AVLTreeNode node = nodes.get(2);
        AVLTreeNode first = nodes.get(1);
        AVLTreeNode second = nodes.get(0);
        boolean change = false;
     if(path.get(0) != path.get(1) ){
         if(path.get(0)){
             node.left = second;
             first.right=second.left;
             second.left = first;
         }else{
             node.right = second;
             first.left = second.right;
             second.right = first;
         }
         change = true;
     }
     if(change){
         AVLTreeNode temp = second;
         first = second;
         second = temp;
     }
     if(!isRoot) {
         if (path.get(2)) {
             parent.right = first;
         } else {
             parent.left = first;
         }
     }else {
         tree.setRoot(first);
     }
     if(path.get(1)){
         node.right= first.left;
         first.left = node;
     }else{
         node.left = first.right;
         first.right = node;
     }

    }
    private void updateBlance(){
        int rHeight = right == null ? 0 : right.height();
        int lHeight = left == null ? 0 : left.height();
        balance = rHeight- lHeight;
    }
    /**
     * Diese Methode wandelt den Baum in das Graphviz-Format um.
     *
     * @param sb der StringBuilder für die Ausgabe
     */

    public void dot(StringBuilder sb) {
        dotNode(sb, 0);
    }

    private int dotNode(StringBuilder sb, int idx) {
        sb.append(String.format("\t%d [label=\"%d, b=%d\"];%n", idx, key, balance));
        int next = idx + 1;
        if (left != null) {
            next = left.dotLink(sb, idx, next, "l");
        }
        if (right != null) {
            next = right.dotLink(sb, idx, next, "r");
        }
        return next;
    }

    private int dotLink(StringBuilder sb, int idx, int next, String label) {
        sb.append(String.format("\t%d -> %d [label=\"%s\"];%n", idx, next, label));
        return dotNode(sb, next);
    }
}