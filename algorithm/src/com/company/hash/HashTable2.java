package com.company.hash;

import java.util.TreeMap;

public class HashTable2<K,V> {

    private static final int upperTol=10;// 平均哈希冲突> upperTol进行扩容
    private static final int lowerTol=2;
    private static final int initCapacity=7;//数组大小
    private TreeMap<K,V>[] hashTable;
    private int M;
    private int size;
    public HashTable2(int M){
        this.M=M;
        size=0;
        hashTable=new TreeMap[M];
        for (int i = 0; i < M; i++) {
            hashTable[i]=new TreeMap<>();
        }
    }
    public HashTable2(){
        this(97);
    }

    /**
     * 计算哈希值
     * @param key
     * @return
     */
    private int hash(K key){
        return (key.hashCode()& 0X7fffffff)%M;
    }
    public int getSize(){
        return size;
    }
    public void add(K key,V value){
        TreeMap<K, V> map = hashTable[hash(key)];
        if(map.containsKey(key)){
            map.put(key,value);
        }else {
            map.put(key,value);
            size++;
        }
        if(size>=upperTol*M){
            resize(2*M);
        }
    }
    public V remove(K key){
        TreeMap<K, V> map = hashTable[hash(key)];
        V ret=null;
        if(map.containsKey(key)){
            ret=map.remove(key);
        }
        if(size<=lowerTol*M&& M/2>=initCapacity){
            resize(M/2);
        }
        return ret;
    }
    public void set(K key,V value){
        TreeMap<K, V> map = hashTable[hash(key)];
        if(!map.containsKey(key)){
            throw new IllegalArgumentException("key 不存在");
        }
        map.put(key,value);
    }
    public boolean contains(K key){
        return hashTable[hash(key)].containsKey(key);
    }

    public V get(K key){
        TreeMap<K, V> map = hashTable[hash(key)];
        return map.get(key);
    }
    private void resize(int newM){
        TreeMap<K,V>[] newHashTable=new TreeMap[newM];
        for (int i = 0; i < newM; i++) {
            newHashTable[i]=new TreeMap<>();
        }
        //注意  当前的M 是在hash方法里有用的
        int lodM=M;
        this.M=newM;
        for (int i = 0; i < lodM; i++) {
            //把当前的哈希表中的元素全部遍历出来 放到新的哈希表里
            TreeMap<K, V> kvTreeMap = hashTable[i];
            for (K key : kvTreeMap.keySet()) {
                newHashTable[hash(key)].put(key,kvTreeMap.get(key));
            }
        }
        this.hashTable=newHashTable;
    }
}
