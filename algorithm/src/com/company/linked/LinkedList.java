package com.company.linked;



/**
 * 链表
 * @param <E>
 */
public class LinkedList<E> {
    /**
     * 链表节点
     *
     */
    private class Node{
        public E e;

        public Node next;

        /**
         *
         * @param e 存储数据
         * @param next 存储下个一个节点
         */
        public Node(E e,Node next){
            this.e=e;
            this.next=next;
        }

        public Node(E e){
            this(e,null);
        }

        public Node(){
            this(null,null);
        }
        @Override
        public String toString(){
            return e.toString();
        }
    }
    //虚拟头节点
    private Node dummyHead;
    private int size;

    public LinkedList(){
        dummyHead=new Node (null,null);//有意识的浪费一个节点  让他成为头节点
        size=0;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    //在链表的index位置添加新的元素
    public void add(int index,E e){
        if(index< 0||index>size) {
            throw new IllegalArgumentException("传入参数异常");
        }
            Node prev=dummyHead;
            for (int i = 0; i < index ; i++) {
                prev=prev.next;
            }
            prev.next=new Node(e,prev.next);
            size++;

    }
    //使用递归方法 为链表添加元素
    public Node add2(Node pre, int cruindex, int index, E e){
        if(cruindex==index){
            pre.next=new Node(e,pre.next);
            size++;
            return pre;
        }
        pre.next=add2(pre.next,cruindex+1,index,e);
        return  pre;
    }
    //在链表最后添加一个元素
    public void addLast(E e){
        add2(dummyHead, 0, size-1 , e);
    }
    //在链表头部添加新的元素e
    public void addFirst(E e){
        add2(dummyHead, 0, 0, e);
        //方式一
//        Node node=new Node(e);
//        node.next=head;
//        head=node;
        //简洁方法
//        add(0,e);
    }
    //获取链表中的第index个元素
    public E get(int index){
        if(index<0||index>size){
            throw new IllegalArgumentException("传入参数异常");
        }
        Node cur=dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur=cur.next;
        }
        return cur.e;
    }
    public E getFirst(){
        return get(0);
    }
    public E getLast(){
        return get(size-1);
    }
    //修改链表中的第index个元素
    public void set(int index,E e){
        if(index<0||index>=size){
            throw new IllegalArgumentException("传入参数异常");
        }
        Node cur=dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur=cur.next;
        }
        cur.e=e;
    }
    //查找链表中是否有元素e
    public boolean  contains(E e){
        Node cur=dummyHead.next;
        while(cur!=null){
            if (cur.e.equals(e))
                return true;
            cur=cur.next;
        }
        return false;
    }
    //删除指定索引的元素
    public E remove(int index){
        if(index<0||index>size){
            throw new IllegalArgumentException("传入参数异常");
        }
        Node prev=dummyHead;
        for (int i = 0; i < index; i++) {
            prev=prev.next;
        }
        Node retNode=prev.next;
        prev.next=retNode.next;
        retNode.next=null;
        size--;
        return retNode.e;
    }
    //递归
    public Node remove1(Node prev,int cruindex,int index){
      if(cruindex==index){
          prev.next=prev.next.next;
          size--;
          return prev;
      }
      prev.next=remove1(prev.next,cruindex+1,index);
      return prev;
    }

    public E removeFirst(){
        return remove1(dummyHead,0,0).e;
    }
    public  E removeLast(){
        return remove1(dummyHead,0,size-1).e;
    }
    @Override
    public String toString(){
        StringBuilder linked=new StringBuilder();
        Node cur=dummyHead.next;
        while(cur!=null){
            linked.append(cur+"-->");
            cur=cur.next;
        }
        linked.append("null");
        return linked.toString();
    }



}
