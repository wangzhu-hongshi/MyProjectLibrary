package com.example.demo.bst;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BST<E extends Comparable<E>> {
    private class Node{
        public E e;

        public Node lfte,right;

        public Node(E e){
            this.e=e;
            lfte=null;
            right=null;
        }

    }

    private Node root;

    private int size;

    public int getSize(){
        return size;
    }
    public  boolean isEmpyt(){
        return size==0;
    }

    public void add(E e){
        root=add(root,e);
    }
    private Node add(Node node,E e){
        if(node==null){
            size++;
            return new Node(e);
        }
        if(e.compareTo(node.e)<0){
            node.lfte=add(node.lfte,e);
        }else if (e.compareTo(node.e)>0){
            node.right=add(node.right,e);
        }
        return node;
    }

    public boolean contain(E e ){
        return contain(root,e);
    }
    private boolean contain(Node node,E e){
        if(node==null){
            return false;
        }
        if(e.compareTo(node.e)<0){
            return contain(node.lfte,e);
        }else if(e.compareTo(node.e)>0){
            return contain(node.right,e);
        }else {
            return true;
        }
    }
    //前序遍历
    public void preOrder(){
        preOrder(root);
    }
    private void preOrder(Node node){
        if(node==null)
            return;
        System.out.println(node.e);
        preOrder(node.lfte);
        preOrder(node.right);
    }
    //前序遍历的非递归方法
    public void proOrderNR(){
        Stack<Node> stack=new Stack<>();
        stack.push(root);
        if(!stack.isEmpty()){
            Node cur = stack.pop();
            System.out.println(cur.e);

            if(cur.right!=null)
                stack.push(cur.right);
            if (cur.lfte!=null)
                stack.push(cur.lfte);
        }
    }
    //层序遍历
    public void levelOrder(){
        Queue<Node> queue=new LinkedList<>();
        queue.add(root);
        if(!queue.isEmpty()){
            Node remove = queue.remove();
            System.out.println(remove.e);
            if(remove.lfte!=null)
                queue.add(remove.lfte);
            if(remove.right!=null)
                queue.add(remove.right);
        }
    }
    //找出最大元素
    public E maxmum(){
        if(size==0)
            throw new IllegalArgumentException("BST is empty");
        return maxmum(root).e;
    }
    private Node maxmum(Node node){
        if(node.right==null)
            return node;
        return maxmum(node.right);
    }
    //找出最小
    public E minmum(){
        if(size==0)
            throw new IllegalArgumentException("BST is empty");
        return minmum(root).e;
    }
    private Node minmum(Node node){
        if(node.lfte==null)
            return node;
        return minmum(node.lfte);
    }

    public E removeMin(){
        E e = minmum();
        root = removeMin(root);
        return e;
    }
    private Node removeMin(Node node){
        if(node.lfte==null){
            Node rightNode=node.right;
            node.right=null;
            size--;
            return rightNode;
        }

        node.lfte=removeMin(node.lfte);
        return node;
    }

    public void  remove(E e){

    }
    private Node remove(Node node,E e){
        if(node==null){
            return null;
        }
        if(e.compareTo(node.e)<0) {
            node.lfte = remove(node.lfte, e);
            return node;
        }
        else if(e.compareTo(node.e)>0) {
            node.right = remove(node.right, e);
            return node;
        }else {
            if(node.right==null){
                Node leftNode=node.lfte;
                node.lfte=null;
                size--;
                return leftNode;
            }else if(node.lfte==null){
                Node rightNode=node.right;
                node.right=null;
                size--;
                return rightNode;
            }else {
                Node minmum = minmum(node.right);
                minmum.lfte=node.lfte;
                minmum.right=removeMin(node.right);
                node.lfte=node.right=null;
                return minmum;
            }
        }
    }




}
