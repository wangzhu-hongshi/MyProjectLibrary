package com.company.queue;

import com.company.queue.Queue;

public class LinkedQueue<E> implements Queue<E> {




    /**
     * 链表节点
     *
     */
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
    public void LinkedQueue(){
        head=null;
        tail=null;
        size=0;
    }

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
        Node retNode=head;
        head=head.next;
        retNode.next=null;
        if(head==null)
            tail=null;
        size--;
        return retNode.e;

    }

    @Override
    public E getFront() {
        if(isEmpety()){
            throw new IllegalArgumentException("该链表为空");
        }
        return head.e;
    }

    @Override
    public String toString(){
        StringBuilder res=new StringBuilder();
        res.append("Queue: front");
        Node cur=head;
        while (cur!=null) {
            res.append(cur + "->");
            cur = cur.next;
        }
        res.append("NULL tail");
        return res.toString();
    }
    public static void main(String[] args) {
       LinkedQueue<Integer> queue=new LinkedQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
            if(i%3==2)
                queue.dequeue();
            System.out.println(queue);
        }

    }
}
