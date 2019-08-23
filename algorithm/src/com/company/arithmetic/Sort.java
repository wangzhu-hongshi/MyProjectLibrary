package com.company.arithmetic;

public class Sort {
     /**
     * 选择排序
     * @param arr
     * @return
     */
    public static<E extends Comparable> E [] selection(E[] arr){
        int n=arr.length;
        for (int i = 0; i < n; i++) {
            int minIndex=i;
            for (int j = i+1; j < n; j++) {
                if(arr[j].compareTo(arr[minIndex])<0){
                    minIndex=j;
                }
            }
            E ert;
            ert=arr[i];
            arr[i]=arr[minIndex];
            arr[minIndex]=ert;
        }
        return arr;
    }

    public static void main(String[] args) {
        Integer [] arr={9,8,7,5,2,3,1,4,6};
        Integer[] ints = selection(arr);
        for (int i : ints) {
            System.out.print(i+" ");
        }
        System.out.println(" ");
        Character[] c={'a','k','b','c'};
        Character[] selection = selection(c);
        for (Character character : selection) {
            System.out.print(character+" ");
        }
    }
}
