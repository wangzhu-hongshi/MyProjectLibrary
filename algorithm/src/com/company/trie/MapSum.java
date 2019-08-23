package com.company.trie;

import com.company.map.Map;

import java.util.TreeMap;

public class MapSum {

    private class Node{
        public int value;
        public TreeMap<Character,Node> next;

        public Node(int value){
            this.value=value;
            next=new TreeMap<>();
        }
        public Node(){
            this(0);
        }
    }
    private  Node root;
    private int size;

    public MapSum(){
        root=new Node();
        size=0;
    }

    public void insert(String word,int val){
        Node cur=root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(cur.next.get(c)==null)
                cur.next.put(c,new Node());
            cur=cur.next.get(c);
        }
        cur.value=val;
    }
    //返回以这个字符串为前缀的节点的值加和
    public int sum(String prefix) {
        Node cur=root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if(cur.next.get(c)==null){
                return 0;
            }
            cur=cur.next.get(c);
        }
        return sum(cur);
    }
    private int sum(Node node){
        int res=node.value;
        for (Character character : node.next.keySet()) {
            res+=sum(node.next.get(character));
        }
        return res;
    }

}
