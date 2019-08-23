package com.company.AVL;

import com.company.map.Map;

import java.util.ArrayList;

/**
 * 平衡二叉树
 * @param <K>
 * @param <V>
 */
public class AVLBSTMap<K extends Comparable<K>,V> implements Map<K,V> {


    private class Node{
        public K key;
        public V value;
        public Node left,right;
        public int height;

        public Node(K key,V value){
            this.key=key;
            this.value=value;
            left=null;
            right=null;
            height=1;
        }
    }
    private Node root;
    private int size;

    public AVLBSTMap(){
        root=null;
        size=0;
    }


    //获得 节点的高度
    private int getHeight(Node node){
        if(node==null)
            return 0;
        return node.height;
    }
    //获得 该节点的 平衡因子
    private int getBalanceFactor(Node node){
        if(node==null)
            return 0;
        return getHeight(node.left)-getHeight(node.right);
    }
    //判断该二叉树 是否是二分搜索树
    private boolean isBST(Node node){
        ArrayList<K> list=new ArrayList<>();
        isOrder(node,list);
        for (int i = 1; i < list.size(); i++) {
            if(list.get(i-1).compareTo(list.get(i))>0)
                return false;
        }
        return true;
    }
    //中序遍历 将结果存到集合里
    private void isOrder(Node node, ArrayList<K> list) {
        if(node==null)
            return;
        isOrder(node.left,list);
        list.add(node.key);
        isOrder(node.right,list);
    }
    //判断该二叉树是否是 平衡二叉树
    private boolean isBalanced(){
        return isBalanced(root);
    }
    private boolean isBalanced(Node node){
        if(node==null)
            return true;
        int balan = getBalanceFactor(node);
        if(Math.abs(balan)>1){
            return false;
        }
        return isBalanced(node.left)&&isBalanced(node.right);
    }
    //对节点 y 进行 右旋转的操作 返回旋转后的操作
    private Node rightRotate(Node y){
        Node x =y.left;
        Node T3=x.right;

        x.right=y;
        y.left=T3;

        y.height=Math.max(getHeight(y.left),getHeight(y.right))+1;
        x.height=Math.max(getHeight(x.left),getHeight(x.right))+1;
        return x;
    }
    //左旋转
    private Node lfteRotate(Node y){
       Node x=y.right;
       Node T3=x.left;

       x.left=y;
       y.right=T3;

        y.height=Math.max(getHeight(y.left),getHeight(y.right))+1;
        x.height=Math.max(getHeight(x.left),getHeight(x.right))+1;
        return x;
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
        //更新 高度
        node.height=1+Math.max(getHeight(node.left),getHeight(node.right));
        //计算平衡因子
        int balan = getBalanceFactor(node);
        //平衡维护
        if(balan>1){
            if(getBalanceFactor(node.left)>=0){
                return rightRotate(node);
            }else {
                node.left=lfteRotate(node.left);
                return rightRotate(node);
            }

        }
        if(balan<-1){
            if( getBalanceFactor(node.right)<=0){
                return lfteRotate(node);
            }else {
                node.right=rightRotate(node.right);
                return lfteRotate(node);
            }

        }

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

        Node retNode=null;
        if(node.key.compareTo(key)<0) {
            node.left = remove(node.left, key);
            retNode= node;
        }
        else if(node.key.compareTo(key)>0) {
            node.right = remove(node.right, key);
            retNode= node;
        }
        else {//node.key.compareTo(key)==0
                if(node.left==null){
                    Node rightNode=node.right;
                    node.right=null;
                    size--;
                    retNode= rightNode;
                }
                else if(node.right==null){
                    Node leftNode=node.left;
                    node.left=null;
                    size--;
                    retNode= leftNode;
                }else {
                    Node rightmin = minimum(node.right);
                    rightmin.right = remove(node.right,rightmin.key);
                    rightmin.left = node.left;
                    node.left = node.right = null;
                    retNode = rightmin;
                }
        }
        if(retNode==null)
            return null;
        retNode.height=1+Math.max(getHeight(retNode.left),getHeight(retNode.right));
        //计算平衡因子
        int balan = getBalanceFactor(retNode);
        //平衡维护
        if(balan>1){
            if(getBalanceFactor(retNode.left)>=0){
                return rightRotate(retNode);
            }else {
                retNode.left=lfteRotate(retNode.left);
                return rightRotate(retNode);
            }

        }
        if(balan<-1){
            if( getBalanceFactor(retNode.right)<=0){
                return lfteRotate(retNode);
            }else {
                retNode.right=rightRotate(retNode.right);
                return lfteRotate(retNode);
            }

        }

        return retNode;

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
