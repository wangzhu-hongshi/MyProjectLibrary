package com.company.map;



/**
 * 基于链表的map集合
 * @param <K>
 * @param <V>
 */
public class LinkedListMap<K,V> implements Map<K,V> {


    private class Node{
        public K key;
        public V value;

        public  Node next;


        public Node(K key,V value, Node next){
           this.key=key;
           this.value=value;
           this.next=next;
        }

        public Node(K key){
            this(key,null,null);
        }

        public Node(){
            this(null,null,null);
        }
        @Override
        public String toString(){
            return key.toString()+":"+value.toString();
        }
    }
    private Node dummyHead;
    private int size;

    public  LinkedListMap(){
        dummyHead=new Node();
        size=0;
    }
    @Override
    public void add(K key, V value) {
        Node node = getNode(key);
        if(node==null){
            //判断是否已经存在 key、
            dummyHead.next=new Node (key,value,dummyHead.next);
            size++;
        }else
            node.value=value;
    }

    @Override
    public V remove(K key) {
        Node prev=dummyHead;
        while (prev.next!=null){
            if(prev.next.key.equals(key))
                break;
            prev=prev.next;
        }
        if(prev.next!=null){
            Node delNode=prev.next;
            prev.next=delNode.next;
            delNode.next=null;
            size--;
            return delNode.value;
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        return getNode(key)!=null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node==null?null:node.value;
    }

    @Override
    public void set(K key, V value) {
        Node node = getNode(key);
        if(node==null)
            throw new IllegalArgumentException(key+"没有这个key");
        node.value=value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    /**
     * 辅助方法
     * @param key
     * @return 返回key所在的节点
     */
    public Node getNode(K key){
        Node cur=dummyHead.next;
        while (cur!=null){
            if(key.equals(cur.key))
                return cur;
            cur=cur.next;
        }
        return null;
    }
}
