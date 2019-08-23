package com.company.queue;

import com.company.array.Array;
import com.company.stack.ArrayStack;

/**
 * 队列 先进先出
 * @param <E>
 */
public class ArrayQueue<E> implements Queue<E>{
    private Array<E> array;

    public ArrayQueue(int capacity){
        array=new Array<>(capacity);
    }
    public ArrayQueue(){
        array=new Array<>();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpety() {
        return array.isEmpty();
    }
    //添加数据
    @Override
    public void enqueue(E e) {
        array.addLast(e);
    }
    //取出数据
    @Override
    public E dequeue() {
        return array.removeFirst();
    }
    //查看队首
    @Override
    public E getFront() {
        return array.getFirst();
    }
    @Override
    public String toString(){
        StringBuilder builder=new StringBuilder();
        builder.append("Queue:");
        builder.append("front [");
        for (int i = 0; i < array.getSize(); i++) {
            builder.append(array.get(i));
            if(i!=array.getSize()-1){
                builder.append(",");
            }
        }
        builder.append("] tail");
        return builder.toString();
    }

    public static void main(String[] args) {
        ArrayQueue<Integer> queue=new ArrayQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
            if(i%3==2)
                queue.dequeue();
                 System.out.println(queue);
        }

    }

}
