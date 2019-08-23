package com.company.segmentTree;

/**
 * 线段树  计算的一段区间的业务要求
 * @param <E>
 */
public class SegmentTree<E> {

    private Merger<E> merger;//融合器
    private E[] tree;

    private E[] data;

    public SegmentTree(E[] arr,Merger<E> merger){
        this.merger=merger;
        data=(E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            data[i]=arr[i];
        }

        tree=(E[])new Object[4*arr.length];
        //构建线段树
        buildSegmentTree(0,0,data.length-1);
    }

    //在treeIndex的位置创建表示区间[l...r]的线段树;
    private void buildSegmentTree(int treeIndex,int l,int r) {
        //终止条件
        if(l==r){
            tree[treeIndex]=data[l];
            return;
        }
        int leftTreeIndex=lfteChild(treeIndex);
        int rightTreeIndex=rightChild(treeIndex);
        //求出中间值
        int mid=l+(r-l)/2;
        //进行递归
        buildSegmentTree(leftTreeIndex,l,mid);
        buildSegmentTree(rightTreeIndex,mid+1,r);
        //按照业务逻辑融合两个子树到父节点
        tree[treeIndex]=merger.merger(tree[leftTreeIndex],tree[rightTreeIndex]);
    }
    public int getSize(){
        return data.length;
    }

    public E get(int index){
        if(index<0||index>=data.length){
            throw new IllegalArgumentException("Index is illegal.");
        }
        return data[index];
    }
    //返回完全二叉树的数组表示中，一个索引所表示的元素的左孩子节点的索引
    public int lfteChild(int index){
        return (2*index)+1;
    }

    public int rightChild(int index){
        return (2*index)+2;
    }
    public E query(int queryL,int queryR){
        if(queryL<0||queryL>=data.length ||
                queryR<0|| queryR>=data.length){
            throw new IllegalArgumentException("Index is illegal.");
        }
        //进行递归操作
        return query(0,0,data.length,queryL,queryR);
    }
    //在以treeId为跟节点的线段树中[l...r]的范围里，搜索区间[queryL...queryR]的值
    private E query(int treeIndex,int l,int r,int queryL,int queryR){
        //终止条件
        if(l==queryL&&r==queryR){
            return tree[treeIndex];
        }

        int leftTreeIndex=lfteChild(treeIndex);
        int rightTreeIndex=rightChild(treeIndex);

        //求出中间值
        int mid=l+(r-l)/2;
        if(queryL>=mid+1)
            return query(rightTreeIndex,mid+1, r,queryL,queryR);
        else if (queryR<=mid)
            return query(leftTreeIndex,l,mid,queryL,queryR);

        E leftResult=query(leftTreeIndex,l,mid,queryL,mid);
        E rightResult=query(rightTreeIndex,mid+1,r,mid+1,queryR);
        //返回融合的E
         return  merger.merger(leftResult,rightResult);

    }
    public void set(int index,E val){
        if(index<0||index>=data.length){
            throw new IllegalArgumentException("Index is illegal");
        }
        data[index]=val;
        set(0,0,data.length-1,index,val);
    }
    //在以treeIndex为根的线段树中更新index的值为e
    private void set(int treeIndex,int l,int r,int index,E val){
        if(l==r) {
            tree[treeIndex] = val;
            return;
        }
        int mid=l+(r-l)/2;
        int leftTreeIndex=lfteChild(treeIndex);
        int rightTreeIndex=rightChild(treeIndex);
        if(index>=mid+1){
            set(rightTreeIndex,mid+1,r,index,val);
        }else if(index<=mid){
            set(leftTreeIndex,l,mid,index,val);
        }
        tree[treeIndex]=merger.merger(tree[rightTreeIndex],tree[leftTreeIndex]);
    }
    @Override
    public String toString(){
        StringBuilder res=new StringBuilder();
        res.append("[");
        for (int i = 0; i <=tree.length - 1; i++) {
            if(tree[i]!=null)
                res.append(tree[i]);
            else
                res.append("null");
            if(i!=tree.length-1)
                res.append(",");
            else
                res.append("]");
        }
        return res.toString();
    }
}
