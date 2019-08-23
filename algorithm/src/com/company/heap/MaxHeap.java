package com.company.heap;

import com.company.array.Array;

/**
 * 基于动态数组
 * 最大堆
 * @param <E>
 */
public class MaxHeap<E extends Comparable<E>> {
    private Array<E> data;

    public MaxHeap(int capacity){
        data=new Array<>(capacity);
    }
    public MaxHeap(){
        data=new Array<>();
    }
    //Heapify 把一个新数组变成堆形式
    public MaxHeap(E[] arr){
        data=new Array<>(arr);

        for (int i =parent(data.getSize()); i >=0; i--) {
            siftDown(i);
        }
    }
    public int getSize(){
        return data.getSize();
    }
    public boolean isEmpty(){
        return data.isEmpty();
    }
    //返回完全二叉树的数组表示中，一个索引所表示的元素的父亲节点的索引

    private int parent(int index){
        if(index==0){
            throw new IllegalArgumentException("该节点是最大的节点！");
        }
        return (index-1)/2;
    }
    //返回该节点的左孩子节点
    private int leftChild(int index){
        return index*2+1;
    }
    //返回右孩子的节点
    private int rightChild(int index){
        return index*2+2;
    }

    /**
     * 堆添加数据
     * 先从数组的末尾添加数据
     * 然后根据添加进来的元素的值的大小 进行相应的上浮
     * @param e
     */
    public void add(E e ){
        data.addLast(e);
        siftUp(data.getSize()-1);
    }

    /**
     * 上浮的过程
     * @param k
     */
    private void siftUp(int k){
        while (k > 0 && data.get(parent(k)).compareTo(data.get(k))<0){
            data.swap(k,parent(k));
            k=parent(k);
        }
    }
    //看堆中最大的元素
    public E findMax(){
        if(data.getSize()==0){
            throw new IllegalArgumentException("堆中是空的");
        }
        return data.get(0);
    }
    //取出堆中最大的元素
    public E extractMax(){
        E ert=findMax();
        //拿最后面的元素和最大的元素进行位置交换
        data.swap(0,data.getSize()-1);
        //删除后面的元素
        data.removeLast();
        //下沉数据
        siftDown(0);
        return ert;
    }
    //下沉数据
    private void siftDown(int k){
        //判断 k是否有左孩子
        while (leftChild(k)<data.getSize()){
            int j=leftChild(k);
            //判断k是否有右孩子 并且 比较左孩子和右孩子大小
            if(rightChild(k)<data.getSize() && data.get(j+1).compareTo(data.get(j))>0){
                j=rightChild(k);
                //此时data[j]是 左孩子和右孩子当中的最大值
            }
            //判断 k下的值大于 最大孩子 则跳出循环
            if(data.get(k).compareTo(data.get(j))>=0){
                break;
            }
            //小于 则 换位
            data.swap(k,j);
            k=j;
        }
    }
    //取出中最大的元素，并且替换成元素e  Replace
    public E replace(E e){
        E max = findMax();
        data.setData(0,e);
        siftDown(0);
        return max;
    }
}
