package com.company.trie;

import java.util.TreeMap;

public class WordDictionary {
    private class Node{
        public boolean isWord;
        public TreeMap<Character, Node> next;
        public Node(boolean isWord){
            this.isWord=isWord;
            next=new TreeMap<>();
        }
        public Node(){
            this(false);
        }
    }
    private Node root;

    public WordDictionary(){
        root=new Node();
    }
    public void addWord(String word){
        Node cur=root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(cur.next.get(c)==null)
                cur.next.put(c,new Node());
            cur=cur.next.get(c);
        }
        cur.isWord=true;
    }

    public boolean search(String word){
//        Node cur=root;
//        for (int i = 0; i < word.length(); i++) {
//            if(cur.next.get(word.charAt(i))==null)
//                return false;
//            cur=cur.next.get(word.charAt(i));
//        }
//        return true;
        return search(root,word,0);
    }
    private boolean search(Node node,String word,int index){
        if(index==word.length()){
            return node.isWord;
        }
        char c = word.charAt(index);
        if(c!='.'){
            if(node.next.get(c)==null)
                return false;
            return search(node.next.get(c),word,index+1);
        }else {
            for (Character character : node.next.keySet()) {
                if(search(node.next.get(character),word,index+1))
                    return true;
            }
            return false;
        }
    }
}
