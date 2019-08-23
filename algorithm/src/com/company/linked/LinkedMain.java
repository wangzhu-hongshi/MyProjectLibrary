package com.company.linked;

public class LinkedMain {
    public static void main(String[] args) {
        LinkedList<Integer> linkedList=new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            linkedList.addFirst(i);
            System.out.println(linkedList);
        }
//        linkedList.addLast(4);
//        System.out.println(linkedList);
        linkedList.removeFirst();
        System.out.println(linkedList);
        linkedList.removeLast();
        System.out.println(linkedList);
//        linkedList.add(2,87);
//        System.out.println(linkedList);
//        linkedList.remove(2);
//        System.out.println(linkedList);
//        linkedList.removeFirst();
//        System.out.println(linkedList);
//        boolean contains = linkedList.contains(4);
//        System.out.println(contains);
    }
}
