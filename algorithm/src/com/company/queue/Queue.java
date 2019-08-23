package com.company.queue;

public interface Queue<E>{
    int getSize();
    boolean isEmpety();
    void enqueue(E e);
    E dequeue();
    E getFront();
}
