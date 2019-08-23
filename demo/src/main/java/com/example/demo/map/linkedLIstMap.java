package com.example.demo.map;



public class linkedLIstMap<K,V> implements Map<K,V> {
    private class Node{
        public K key;
        public V value;
        public Node next;
        public Node(K key,V value,Node next){
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
    public linkedLIstMap(){
        dummyHead=new Node();
        size=0;
    }
    //辅助函数 找到key所在的节点
    private Node getNode(K key){
        Node cur=dummyHead.next;
        while (cur!=null){
            if(cur.key.equals(key))
                return cur;
            cur=cur.next;
        }
        return null;
    }
    @Override
    public void add(K key, V value) {
        Node node = getNode(key);
        if(node==null){
            dummyHead.next = new Node(key,value,dummyHead.next);
            size++;
        }else
            node.value=value;

    }

    @Override
    public V get(K key) {
        Node cur=getNode(key);
        return cur==null?null:cur.value;
    }

    @Override
    public V remove(K key) {
        Node node = getNode(key);
        Node head=dummyHead;
        while (head.next!=null){
            if(head.next.key.equals(key)){
                head.next=head.next.next;
                size--;
            }
            head=head.next;
        }
        return node!=null?node.value:null;
    }

    @Override
    public boolean contains(K key) {
        return getNode(key)==null?false:true;
    }

    @Override
    public void set(K key, V value) {
        Node node = getNode(key);
        if(node!=null){
            node.value=value;
        }

    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }
}
