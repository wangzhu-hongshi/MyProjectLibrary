package com.company.heap;

import com.company.queue.Queue;

/**
 * 基于最大堆的优先队列
 * @param <E>
 */
public class PriorityQueue<E extends Comparable<E>> implements Queue<E> {
    /**
     * 最大堆的变量
     */
    private MaxHeap<E> maxHeap;

    public PriorityQueue(){
        maxHeap=new MaxHeap<>();
    }

    @Override
    public int getSize() {
        return maxHeap.getSize();
    }

    @Override
    public boolean isEmpety() {
        return maxHeap.isEmpty();
    }

    @Override
    public void enqueue(E o) {
        maxHeap.add(o);
    }

    @Override
    public E dequeue() {
        return maxHeap.extractMax();
    }

    @Override
    public E getFront() {
        return maxHeap.findMax();
    }
}
