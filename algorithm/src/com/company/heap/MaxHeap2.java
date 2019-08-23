package com.company.heap;

import com.company.array.Array;

/**
 * 基于数组的堆 索引从零开始
 * @param <E>
 */
public class MaxHeap2<E extends Comparable<E>>{
    private Array<E> data;

    public MaxHeap2(int capacity){
        data=new Array<>(capacity);
    }
    public MaxHeap2(){
        data=new Array<>();
    }
    public MaxHeap2(E[] arr){
        data=new Array<>(arr);
        for (int i = parent(data.getSize()-1); i >=0 ; i--) {
            siftDown(i);
        }
    }
    public int getSize(){
        return data.getSize();
    }
    public boolean isEmpty(){
        return data.isEmpty();
    }
    public int parent(int index){
        if(index==0){
            throw new IllegalArgumentException("index-0 doesn't have parent");
        }
        return (index-1)/2;
    }
    public int lfteChild(int index){
        return index*2+1;
    }
    public int righthild(int index){
        return index*2+2;
    }
    public void add(E e){
        data.addLast(e);
        siftUp(data.getSize()-1);
    }
    //上浮
    private void siftUp(int k){
         while (k>0&& data.get(parent(k)).compareTo(data.get(k))<0){
             data.swap(k,parent(k));
             k=parent(k);
         }
    }
    public E findMax(){
        if(data.getSize()==0)
            throw new IllegalArgumentException("index-0 doesn't have parent");
        return data.get(0);
    }

    public E extractMax(){
        E ret=findMax();
        data.swap(0,data.getSize()-1);
        data.removeLast();
        siftDown(0);
        return ret;
    }
    private void siftDown(int k){
        while (lfteChild(k)<data.getSize()){
            int j=lfteChild(k);
            if(j+1<data.getSize()&&data.get(j+1).compareTo(data.get(j))>0){
                j++;
            }
            if(data.get(k).compareTo(data.get(j))>=0){
                break;
            }
            data.swap(k,j);
            k=j;
        }
    }
    //取出堆中最大的元素 并且替换成元素e
    public E erplace(E e){
        E max = findMax();
        data.setData(0,e);
        siftDown(0);
        return max;

    }

}
