package com.example.demo.map;

public interface Map<K,V> {
    void add(K key,V value);
    V get(K key);
    V remove(K key);
    boolean contains(K key);
    void set(K key,V value);
    int getSize();
    boolean isEmpty();
}
