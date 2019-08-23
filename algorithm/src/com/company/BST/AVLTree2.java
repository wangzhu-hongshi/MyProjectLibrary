package com.company.BST;

import com.company.map.Map;

import java.util.ArrayList;

public class AVLTree2<K extends Comparable<K>,V> implements Map<K,V> {


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

    public AVLTree2(){
        root=null;
        size=0;
    }
    //获取节点的高度
    public int getHeight(Node node){
        if(node==null)
            return 0;
        return node.height;
    }
    //获取节点的平衡因子
    public int getBalanceFactor(Node node){
        if(node==null)
            return 0;
        return getHeight(node.left)-getHeight(node.right);
    }
    //判断该二叉树是否是二分搜索树
    public boolean isBST(){
        ArrayList<K> keys=new ArrayList<>();
        inOrder(root,keys);
        for (int i = 0; i < keys.size(); i++) {
            if(keys.get(i-1).compareTo(keys.get(i))>0)
                return false;
        }
        return true;
    }
    //中序遍历将遍历的结果存到list集合中
    private void inOrder(Node node,ArrayList<K> keys){
        if (node==null)
            return;
        inOrder(node.left,keys);
        keys.add(node.key);
        inOrder(node.right,keys);
    }
    //判断该二叉树是否是平衡二叉树
    public boolean isBalanced(){
        return isBalanced(root);
    }
    //判断以Node为根的二叉树是否是一棵平衡二叉树，递归算法
    private boolean isBalanced(Node node){
        //递归到底的结束条件
        if(node==null)
            return true;
        int balanceFactor=getBalanceFactor(node);
        if (Math.abs(balanceFactor)>1)
            return false;
        return isBalanced(node.left)&&isBalanced(node.right);
    }
    //对节点y进行向右旋转操作，返回旋转后的新的根节点
    private Node rightRotate(Node y){
        Node x=y.left;
        Node T3=x.right;
        //向右旋转的过程
        x.right=y;
        y.left=T3;

        //更新height
        y.height=Math.max(getHeight(y.left),getHeight(y.right))+1;
        x.height=Math.max(getHeight(x.left),getHeight(x.right))+1;

        return x;
    }
    //左旋转  返回旋转后的根节点
    private Node leftRotate(Node y){
        Node x=y.right;
        Node T3=x.left;

        //左旋转
        x.left=y;
        y.right=T3;
        //更新height
        y.height=Math.max(getHeight(y.left),getHeight(y.right))+1;
        x.height=Math.max(getHeight(x.left),getHeight(x.right))+1;

        return x;
    }

    //向二分搜索树中添加新的元素 key，value
    //每次添加元素 都可能会使该节点的高度增加 所以要维护高度
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
        //维护添加元素后的节点高度
        node.height=Math.max(getHeight(node.left),getHeight(node.right));
        int balan=getBalanceFactor(node);
        //平衡维护
        //
        // LL当不平衡发生在 左侧的左侧 进行右旋转
        if(balan>1&& getBalanceFactor(node.left)>=0)
            //进行右旋转
            return rightRotate(node);
        //RR 当不平衡发生在 右侧的右侧 进行左旋转
        if(balan<-1&& getBalanceFactor(node.right)<=0)
            return leftRotate(node);
        //LR 当左子树 大于右子树 左子树的左子树小于右子树
        if(balan>1 && getBalanceFactor(node.left)<0){
            //先进行 左旋转
            node.left=leftRotate(node.left);
            return rightRotate(node);
        }
        //RL 当右子树大于左子树 右子树的右子树小于左子树
        if(balan<-1 && getBalanceFactor(node.right)>0){
            //先进行右旋转
            node.right=rightRotate(node.right);
            return leftRotate(node);
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
        Node retNode=null;//记录删除节点的根节点
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
        if(retNode==null){
            return null;
        }
        //更新height
        retNode.height=Math.max(getHeight(retNode.left),getHeight(retNode.right));
        //计算平衡因子
        int balan=getBalanceFactor(retNode);
        //平衡维护
        // LL当不平衡发生在 左侧的左侧 进行右旋转
        if(balan>1&& getBalanceFactor(retNode.left)>=0)
            //进行右旋转
            return rightRotate(retNode);
        //RR 当不平衡发生在 右侧的右侧 进行左旋转
        if(balan<-1&& getBalanceFactor(retNode.right)<=0)
            return leftRotate(node);
        //LR 当左子树 大于右子树 左子树的左子树小于右子树
        if(balan>1 && getBalanceFactor(retNode.left)<0){
            //先进行 左旋转
            node.left=leftRotate(retNode.left);
            return rightRotate(retNode);
        }
        //RL 当右子树大于左子树 右子树的右子树小于左子树
        if(balan<-1 && getBalanceFactor(retNode.right)>0){
            //先进行右旋转
            retNode.right=rightRotate(retNode.right);
            return leftRotate(retNode);
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
