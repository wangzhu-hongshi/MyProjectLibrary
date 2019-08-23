package com.company.linked;

import com.company.stack.Stack;
//链表实现的栈
public class StackLinked<E> implements Stack<E>{
    private LinkedList<E> linkedList;

    public StackLinked(){
        linkedList=new LinkedList<>();
    }


    @Override
    public int getSize() {
        return linkedList.getSize();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    @Override
    public void push(E e) {
        linkedList.addFirst(e);
    }

    @Override
    public E pop() {
//        return null;
       return linkedList.removeFirst();
    }

    @Override
    public E peek() {
        return linkedList.getFirst();
    }
    //使用递归方法 为链表添加元素

    @Override
    public String toString(){
        StringBuilder builder=new StringBuilder();
        builder.append("Stack: top");
        builder.append(linkedList);
        return builder.toString();
    }

    public static void main(String[] args) {
        StackLinked<Integer> stack=new StackLinked<>();
        for (int i = 0; i < 5; i++) {
            stack.push(i);
            System.out.println(stack);
        }
        stack.pop();
        System.out.println(stack);
    }
}
