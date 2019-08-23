package com.company.BST;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二分搜索树（二叉树）
 * 不包含重复元素
 * @param <E>
 *     AVL树   记录度度 还有平衡因子
 *      该节点的高度=左子树和右子树高度进行比较 在最大的高度数值上加一
 */
public class AVLTree<E extends Comparable<E>> {
    //节点内部类
    private class Node{
        public E e;
        public Node left,right;
        public int height;//高度

        public Node(E e){
            this.e=e;
            left=null;
            right=null;
            height=1;//初始时是1
        }


    }
    private Node root;//根节点

    private int size;
    public AVLTree(){
        root=null;
        size=0;
    }
    public int getSize(){
        return size;
    }
    public boolean isEmpty(){
        return size==0;
    }
    //查询传入的节点的高度  辅助方法
    public int getheight(Node node){
        if(node==null)
            return 0;
        return node.height;
    }
    //计算平衡因子  左子树的高度减去右子树的高度
    public int getBalanceFactor(Node node){
        if(node==null)
            return 0;
        return getheight(node.left)-getheight(node.right);
    }

//    //起始方法
//    public void add(E e){
//        if(root==null) {
//            root = new Node(e);
//            size++;
//        }else
//            add(root,e);
//            //启动递归现象添加功能
//    }
//    //向以node为根的二分搜索树插入元素e，递归算法
//    private void add(Node node,E e){
//        //递归终止
//        //判断元素是否存在
//        if(e.equals(node.e)){
//            return;
//            //判断元素和根节点中的元素大小 因泛型是不确定的 所有使用Comparable接口的实现类中方法compareTo()
//            //<0 表示传入的元素小于 根节点中的元素 反之 大于根节点中的元素
//        }else if(e.compareTo(node.e)<0&&node.left==null){
//            node.left=new Node(e);
//            return;
//        }else if(e.compareTo(node.e)>0&&node.right==null){
//            node.right=new Node(e);
//            return;
//        }
//        //
//        if(e.compareTo(node.e)<0){
//            add(node.left,e);
//        }else //e.compareTo(node.e)>0
//            add(node.right,e);
//    }
    //添加元素
    public void add(E e){

         root = add(root, e);
        //启动递归现象添加功能
}
    //向以node为根的二分搜索树插入元素e，递归算法
    //添加一个元素时 整体的高度有可能会加一
    private Node add(Node node,E e){
        if(node==null){
            size++;
            return new Node(e);
        }
        if(e.compareTo(node.e)<0){
            node.left=add(node.left,e);
        }else if(e.compareTo(node.e)>0){
            node.right=add(node.right,e);
        }
        //维护 高度
        node.height=1+Math.max(getheight(node.left),getheight(node.right));

        //计算平衡因子
        int balanceFactor=getBalanceFactor(node);
        if(Math.abs(balanceFactor)>1)
            System.out.println("unbalanced:"+balanceFactor);
        return node;
    }
    //看二分搜索树是否包含元素e
    public boolean contains(E e ){
        return contains(root,e);
    }
    private boolean contains(Node node,E e){
        if(node==null){
            return false;
        }
        if(e.compareTo(node.e)==0){
            return true;
        }
        else if(e.compareTo(node.e)<0){
            return contains(node.left,e);
        }else
            return contains(node.right,e);
    }
    //前序遍历一个二分搜索树
    public void preOrder(){
        preOrder(root);
    }
    private void preOrder(Node node){
        if(node==null)
            return;
        //先访问 根节点在访问 根节点的左节点 然后访问有节点
        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }
    //使用非递归的方法进行前序遍历二分搜索树
    public void preOrderNR(){
        Stack<Node> stack=new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            Node cur = stack.pop();
            System.out.println(cur.e);
            //前序遍历 先把右子树压入栈中 因为栈是后入先出
            if(cur.right!=null)
                stack.push(cur.right);
            if(cur.left!=null)
                stack.push(cur.left);

        }
    }
    //中序遍历以node为节点的二分搜索树，递归算法
    public void inOrder(){
        inOrder(root);
    }
    private void inOrder(Node node){
        if(node==null)
            return;
        //先访问左节点 ，根节点，又节点
        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);

    }
    //二分搜索树的层序遍历
    public void levelOrder(){
        Queue<Node> queue=new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            Node node = queue.remove();
            System.out.println(node.e);
            if(node.left!=null)
                queue.add(node.left);
            if(node.right!=null)
                queue.add(node.right);
        }
    }
    //寻找二分搜索树中的最小元素
    public E minimum(){
        if(size==0)
            throw new IllegalArgumentException("BST is empty");
        return minimum(root).e;
    }
    private Node minimum(Node node){
        //判断递归的终止条件
        if(node.left==null){
            return node;
        }
        return minimum(node.left);
    }
    //查找最大元素
    public E maximum(){
        if(size==0)
            throw new IllegalArgumentException("BST is empty");
        return maximum(root).e;
    }
    private Node maximum(Node node){
        if(node.right==null)
            return node;
        return maximum(node.right);
    }
    //删除二分搜索树中的最小元素
    public E removeMin(){
        E minimum = minimum();
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
    //删除二分搜索树中的最大元素
    public E removeMax(){
        E maximum = maximum();
        root=removeMax(root);
        return maximum;
    }
    private Node removeMax(Node node){
        if(node.right==null){
            Node leftNode=node.left;
            node.left=null;
            size--;
            return leftNode;
        }
        node.right = removeMax(node.right);
        return node;
    }
    //删除二分搜索树中的指定元素 e
    public void remove(E e){
        remove(root,e);
    }


    private Node remove(Node node,E e){
        if(node==null)
            return null;
        if(e.compareTo(node.e)<0){
            node.left=remove(node.left,e);
            return node;
        }else if(e.compareTo(node.e)>0) {
            node.right = remove(node.right, e);
            return node;
        }
        else {//e==node.e
            //待删除节点左子树为空的情况
            if(node.left==null){
                Node rightNode=node.right;
                node.right=null;
                size--;
                return rightNode;
            }
            //待删除节点右子树为空的情况
            if(node.right==null){
                Node leftNode=node.left;
                node.left=null;
                size--;
                return leftNode;
            }
            //
            //待删除节点左右都不为空的情况
            //找到比待删除节点大的最小节点，就是删除节点右子树的最小节点 找后继 当然也可以找前驱
            //用这个节点顶替待删除节点
            Node successor=minimum(node.right);
            successor.right=removeMin(node.right);
            successor.left=node.left;

            node.left=node.right=null;
            return successor;

        }
    }
    @Override
    public String toString(){
        StringBuilder res=new StringBuilder();
        generateBSBATString(root,0,res);
        return res.toString();
    }

    private void generateBSBATString(Node node, int depth, StringBuilder res) {
        if(node==null){
            res.append(generateDepthString(depth)+"null\n");
            return;
        }
        //前序遍历
        res.append(generateDepthString(depth)+node.e+"\n");
        generateBSBATString(node.left,depth+1,res);

        generateBSBATString(node.right,depth+1,res);
    }

    private String generateDepthString(int depth) {
        StringBuilder res=new StringBuilder();
        for (int i = 0; i < depth; i++) {
            res.append("--");
        }
        return res.toString();
    }

    public static void main(String[] args) {
        AVLTree<Integer> bst=new AVLTree<>();
        int[] nums={6,4,3,7,8,2};
        for (int num : nums) {
            bst.add(num);
        }
//        System.out.println(bst);
//        bst.preOrder();
//        bst.preOrderNR();
        bst.levelOrder();
    }
}
