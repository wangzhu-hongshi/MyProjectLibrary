package com.company.arithmetic;

public class Insertion {
    /**
     * 插入排序
     * @param arr
     * @param <E>
     */
    public static<E extends Comparable> void insertion1(E[] arr){
        for (int i = 1; i < arr.length; i++) {
            //寻找合适的位置arr[i]合适的插入
            for (int j = i; j >0&&arr[j].compareTo(arr[j-1])<0; j--) {
                E ert;
                ert=arr[j];
                arr[j]=arr[j-1];
                arr[j-1]=ert;
            }
        }
    }

    /**
     * 优化后的插入排序
     * @param arr
     * @param <E>
     */
    public static<E extends Comparable> void insertion2(E[] arr){
        for (int i = 1; i < arr.length; i++) {
            E e=arr[i];
            int j;
            for ( j = i; j >0&&arr[j-1].compareTo(e)>0; j--) {
               arr[j]=arr[j-1];
            }
            arr[j]=e;
        }
    }

    public static void main(String[] args) {
        Integer[] a={3,45,62,62,65,67,5,22,6};
        insertion1(a);
        for (Integer integer : a) {
            System.out.print(integer+" ");
        }
        System.out.println(" ");
        insertion2(a);
        for (Integer integer : a) {
            System.out.print(integer+" ");
        }

    }
}
