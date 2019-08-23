package com.company.map;

import com.company.BST.BST;

import java.security.Key;
import java.util.Random;

public class BSTMap<K extends Comparable<K>,V> implements Map<K,V> {


    private class Node{
        public K key;
        public V value;
        public Node left,right;

        public Node(K key,V value){
            this.key=key;
            this.value=value;
            left=null;
            right=null;
        }
    }
    private Node root;
    private int size;

    public BSTMap(){
        root=null;
        size=0;
    }
    //向二分搜索树中添加新的元素 key，value
    @Override
    public void add(K key, V value) {
        root=add(root,key,value);
    }
    private Node add(Node node,K key,V value){
        if(node==null){
            size++;
            return new Node(key,value);
        }
        if(node.key.compareTo(key)<0)
            node.left=add(node.left,key,value);
        else if(node.key.compareTo(key)>0)
            node.right=add(node.right,key,value);
        else
            node.value=value;
        return node;
    }
    //从二分搜索树中删除键为key的节点
    @Override
    public V remove(K key) {
        Node node = getNode(root, key);
        if(node!=null){
            root=remove(root,key);
            return node.value;
        }
        return null;
    }
    private Node remove(Node node, K key){
        if(node==null)
            return null;
        if(node.key.compareTo(key)<0) {
            node.left = remove(node.left, key);
            return node;
        }
        else if(node.key.compareTo(key)>0) {
            node.right = remove(node.right, key);
            return node;
        }
        else {//node.key.compareTo(key)==0
                if(node.left==null){
                    Node rightNode=node.right;
                    node.right=null;
                    size--;
                    return rightNode;
                }
                if(node.right==null){
                    Node leftNode=node.left;
                    node.left=null;
                    size--;
                    return leftNode;
                }
                Node rightmin = minimum(node.right);
                rightmin.right=removeMin(node.right);
                rightmin.left=node.left;
                node.left=node.right=null;
                return rightmin;

        }
    }
    public K minimum(){
        if(size==0)
            throw new IllegalArgumentException("BST is empty");
        return minimum(root).key;
    }
    private Node minimum(Node node){
        //判断递归的终止条件
        if(node.left==null){
            return node;
        }
        return minimum(node.left);
    }
    public K removeMin(){
        K minimum = minimum();
        root=removeMin(root);
        return minimum;
    }
    private Node removeMin(Node node){
        if(node.left==null){
            Node rightNode=node.right;
            node.right=null;
            size--;
            return rightNode;
        }
        node.left=removeMin(node.left);
        return node;
    }
    @Override
    public boolean contains(K key) {
        return getNode(root,key)!=null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(root, key);
        return node==null?null:node.value;
    }

    @Override
    public void set(K key, V value) {
        Node node = getNode(root, key);
        if(node==null)
            throw new IllegalArgumentException("not key!");
        node.value=value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }
    //返回以node为节点的二分搜索树中，可以所在的节点
    private Node getNode(Node node,K key){
        if(node==null)
            return null;
        if(node.key.compareTo(key)<0)
            return getNode(node.left,key);
        else if(node.key.compareTo(key)>0)
            return getNode(node.right,key);
        else
            return node;
    }
}
