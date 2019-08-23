package com.company.trie;

import com.company.map.Map;
import sun.reflect.generics.tree.Tree;

import java.util.TreeMap;

/**
 * 前缀树
 */
public class Trie {
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
    public Trie(){
        root=new Node();
        size=0;
    }
    public int getSize(){
        return size;
    }

    /**
     * 向trie中添加一个新的字符串
     * 非递归
     * @param word
     */
    public void insert(String word){
//        Node cur=root;
//        for (int i = 0; i < word.length(); i++) {
//            char c = word.charAt(i);
//            if(cur.next.get(c)==null){
//                cur.next.put(c,new Node());
//            }
//            cur=cur.next.get(c);
//        }
//        if(!cur.isWord){
//            cur.isWord=true;
//            size++;
//        }
        insert(word,0,root);
    }

    /**
     * 添加方法的递归实现
     * @param word
     * @param curIndex
     * @param node
     */
    private void insert(String word,int curIndex,Node node){
        if(curIndex==word.length()){
            if(!node.isWord){
                node.isWord=true;
                size++;
            }
            return;
        }
        if(node.next.get(word.charAt(curIndex))==null){
            node.next.put(word.charAt(curIndex),new Node());
        }
        insert(word,curIndex+1,node.next.get(word.charAt(curIndex)));
    }

    /**
     * 查询 前缀树中是否包含 word字符串
     * @param word
     * @return
     */
    public boolean search(String word){
//        Node cur=root;
//        for (int i = 0; i < word.length(); i++) {
//            char c = word.charAt(i);
//            if(cur.next.get(c)==null){
//                return false;
//            }
//            cur=cur.next.get(c);
//        }
//        return cur.isWord;

        return search(word,0,root);
    }

    /**
     * 查询的递归实现
     * @param word
     * @param curIndex
     * @param node
     * @return
     */
    private boolean search(String word,int curIndex,Node node){
        if(curIndex==word.length()){
            return node.isWord;
        }
        if(node.next.get(word.charAt(curIndex))==null)
            return false;
        return search(word,curIndex+1,node.next.get(word.charAt(curIndex)));
    }
    //查询是否在Trie中有单词是以prefix为前缀的
    public boolean  startsWith(String prefix){
//        Node cur=root;
//        for (int i = 0; i < prefix.length(); i++) {
//            char c = prefix.charAt(i);
//            if(cur.next.get(c)==null)
//                return false;
//            cur=cur.next.get(c);
//        }
//        return true;
        return startsWith(prefix,0,root);
    }

    /**
     * 查询是否在Trie中有单词是以prefix为前缀的递归实现
     * @param prefix
     * @param curIndex
     * @param node
     * @return
     */
    private boolean startsWith(String prefix,int curIndex,Node node){

        if(curIndex==prefix.length()){
            return true;
        }
        if(node.next.get(prefix.charAt(curIndex))==null)
            return false;

        return startsWith(prefix,curIndex+1,node.next.get(prefix.charAt(curIndex)));
    }

    public static void main(String[] args) {
        Trie trie=new Trie();
        trie.insert("qwe");
        boolean qwer = trie.search("qw");
        System.out.println(qwer);
    }

}
