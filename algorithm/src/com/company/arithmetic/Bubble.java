package com.company.arithmetic;

/**
 * 冒泡排序
 *
 */
public class Bubble {

    public static<E extends Comparable> void bubble(E[] arr){
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length-1-i; j++) {
                if(arr[j].compareTo(arr[j+1])>0){
                    E e;
                    e=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=e;
                }
            }
        }
    }

    public static void main(String[] args) {
        Integer[] a={3,45,62,62,65,67,5,22,6};
        bubble(a);
        for (Integer integer : a) {
            System.out.print(integer+" ");
        }
    }
}
