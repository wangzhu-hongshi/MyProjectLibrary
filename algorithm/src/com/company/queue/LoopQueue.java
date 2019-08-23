package com.company.queue;

/**
 * 循环队列
 */
public class LoopQueue<E> implements Queue<E>{
    private E[] data;
    private int front,tail;
    private int size;

    /**
     * 初始数组容量
     * @param capacity
     */
    public LoopQueue(int capacity){
        //循环队列 有意识的浪费一个空间
        data =(E[]) new java.lang.Object[capacity+1];
        front=0;
        tail=0;
        size=0;
    }
    public LoopQueue(){
        this(10);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpety() {
        return front==tail;
    }

    public int getCapacity(){
        return data.length-1;
    }

    @Override
    public void enqueue(E e) {
        //判断 队列空间是否已满
        if((tail+1)%data.length==front){
            resize(getCapacity()*2);
        }
        data[tail] = e;
        tail=(tail+1)%data.length;
        size++;
    }

    /**
     * 扩容操作
     * @param newCapacity
     */
    public void resize(int newCapacity){
        E[] newData=(E[]) new Object[newCapacity+1];
        for (int i = 0; i < size; i++) {
            newData[i]=data[(i+front)%data.length];
        }
        data=newData;
        front=0;
        tail=size;
    }
    @Override
    public E dequeue() {
        if(isEmpety())
            throw new IllegalArgumentException("该队列里没有数据！");
        E ert=data[front];
        data[front]=null;
        front=(front+1)%data.length;
        size--;
        if(size<=data.length/4&&data.length/2!=0)
            resize(getCapacity()/2);
        return ert;
    }

    @Override
    public E getFront() {
        if(isEmpety())
            throw new IllegalArgumentException("队列是空的");
        return data[front];
    }
    @Override
    public String toString(){
        StringBuilder res=new StringBuilder();
        res.append(String.format("Queue:size=%d,capacity=%d\n",size,data.length));
        res.append("front:[");
        for(int i=front;i!=tail;i=(i+1)%data.length){
            res.append(data[i]);
            if((i+1)%data.length!=tail)
                res.append(",");
        }
        res.append("] tail");
        return res.toString();
    }
    public static void main(String[] args) {
        LoopQueue<Integer> queue = new LoopQueue<>(5);
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
//            if (i % 3 == 2)
//                queue.dequeue();
            System.out.println(queue);
        }
        for (int i = 0; i < 8; i++) {
            queue.dequeue();
            System.out.println(queue);
        }


    }
}
