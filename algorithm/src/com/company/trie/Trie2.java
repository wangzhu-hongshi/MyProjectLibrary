package com.company.trie;

import java.util.TreeMap;

public class Trie2 {
    private class Node{
        public boolean isWord;
        public TreeMap<Character,Node> next;

        public Node(boolean isWord){
            this.isWord=isWord;
            next=new TreeMap<>();
        }
        public Node(){
            this(false);
        }
    }
    private Node root;
    private int size;
    public Trie2(){
        root=new Node();
        size=0;
    }
    public int getSize(){
        return size;
    }
    public boolean isEmpty(){
        return size==0;
    }

    public void add(String word){
        Node cur=root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(cur.next.get(c)==null){
                cur.next.put(c,new Node());
            }
            cur=cur.next.get(c);
        }
        if(!cur.isWord){
            cur.isWord=true;
            size++;
        }
    }
    private void add(String word,int index,Node node){
        if(index==word.length()){
            if(!node.isWord){
                node.isWord=true;
                size++;
            }
            return;
        }
        char c = word.charAt(index);
        if(node.next.get(c)==null){
            node.next.put(c,new Node());
        }
        add(word,index+1,node.next.get(c));
    }
    public boolean contains(String word){
        Node cur=root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(cur.next.get(c)==null){
                return false;
            }
            cur=cur.next.get(c);
        }
        if(cur.isWord){
            return true;
        }
        return false;
    }
    public boolean search(String word){
        return search(word,0,root);
    }
    private boolean search(String word,int index, Node node){
        if(index==word.length()){
            return isEmpty();
        }
        char c = word.charAt(index);
        if(c!='.'){
            if(node.next.get(c)==null)
                return false;
            return search(word,index+1,node.next.get(c));
        }else {
            for (Character character : node.next.keySet()) {
                if(search(word,index+1,node.next.get(character))){
                    return true;
                }
            }
            return false;
        }
    }
}
