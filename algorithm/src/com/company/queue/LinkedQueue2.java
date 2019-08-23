package com.company.queue;

public class LinkedQueue2<E> implements Queue<E>{


    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpety() {
        return size==0;
    }

    @Override
    public void enqueue(E e) {
        if(tail==null){
            tail=new Node(e);
            head=tail;
        }else {
            tail.next=new Node(e);
            tail=tail.next;
        }
        size++;
    }

    @Override
    public E dequeue() {
        if(isEmpety()){
            throw new IllegalArgumentException("链表是空的！");
        }
        Node ret=head;
        head=head.next;
        ret.next=null;
        if(head==null){
            tail=null;
        }
        size--;
        return ret.e;
    }

    @Override
    public E getFront() {
        if(isEmpety()){
            throw new IllegalArgumentException("链表是空的！");
        }
        return head.e;
    }

    private class Node {
        public E e;

        public Node next;

        /**
         * @param e    存储数据
         * @param next 存储下个一个节点
         */
        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this(e, null);
        }

        public Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }
    private Node head,tail;
    private int size;
    public LinkedQueue2(){
        head=null;
        tail=null;
        size=0;
    }

}
