package com.company;

import com.company.map.Map;

/**
 *
 * 红黑树和2-3树是等价性的  2-3树 是绝对平衡树 它可以有2节点一个元素的节点，也可以有3节点两个元素
 * 2-3树 添加元素是：保持二分搜索树的性质下  在进行对非叶子节点的添加 先融合 在拆分
 * 红黑树 的五大定义
 * 1.红黑树的每个节点是红色 或者是黑色
 * 2.红黑树的根节点都是黑色
 * 3.红黑树的每个叶子节点（节点为null）都是黑色的
 * 4.如果一个节点它是红色的话，它的孩子永远都是黑色的
 * 5.无论从哪个节点开始出发到叶子节点 他们的黑色的节点个数永远都是一样的
 * @param <K>
 * @param <V>
 */
public class RBTree<K extends Comparable<K>,V> implements Map<K,V> {
    private static final boolean RED=true;
    private static final boolean BLACK=false;

    private class Node{
        public K key;
        public V value;
        public Node left,right;
        private boolean colour;

        public Node(K key,V value){
            this.key=key;
            this.value=value;
            left=null;
            right=null;
            colour=RED;//每个节点的初始值都是红色的
        }
    }
    private Node root;
    private int size;

    public RBTree(){
        root=null;
        size=0;
    }
    //向红黑树中添加新的元素 key，value
    @Override
    public void add(K key, V value) {
        root=add(root,key,value);
        //红黑树的根节点永远都是黑色的
        root.colour=BLACK;
    }
    //红黑树的 左旋转
    //使之保持红黑树的性质
    private Node leftRotate(Node node){
        Node x=node.right;

        node.right=x.left;
        x.left=node;

        x.colour=node.colour;
        node.colour=RED;
        return x;

    }
    private Node add(Node node, K key, V value){
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
    private Node getNode(Node node, K key){
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