package com.company.heap;

import java.util.Random;

public class HeapMain {

    public static double stestHeap(Integer[] testData, boolean isHeapify){
        long startTime = System.nanoTime();
        MaxHeap2<Integer> maxHeap2;
        if(isHeapify){
             maxHeap2=new MaxHeap2(testData);
        }else {
             maxHeap2=new MaxHeap2();
            for (Integer testDatum : testData) {
                maxHeap2.add(testDatum);
            }
        }
        int[] arr=new int[testData.length];
        for (int i = 0; i < testData.length; i++) {
            arr[i]=maxHeap2.extractMax();

        }
        for (int i = 1; i < testData.length; i++) {
            if(arr[i-1]<arr[i]){
                throw new IllegalArgumentException("Error");
            }
        }
        System.out.println("Test MaxHeap Completed");
        long endTime = System.nanoTime();

        return (endTime-startTime)/1000000000.0;
    }
    public static void main(String[] args) {
        int n=100000;
//        MaxHeap2<Integer> heap=new MaxHeap2<>();
//        Random random=new Random();
//        for (int i = 0; i <n ; i++) {
//            heap.add(random.nextInt(20));
//
//        }
//        int[] arr=new int[n];
//        for (int i = 0; i < n; i++) {
//            arr[i]=heap.extractMax();
//            System.out.println(arr[i]);
//        }
//        for (int i = 1; i < n; i++) {
//            if(arr[i-1]<arr[i]){
//                throw new IllegalArgumentException("Error");
//            }
//        }
//        System.out.println("Test MaxHeap Completed");\
        Integer[] testData=new Integer[n];
        Random random=new Random();
        for (int i = 0; i < n; i++) {
            testData[i]=random.nextInt(Integer.MAX_VALUE);
        }

        double v = stestHeap(testData, false);
        System.out.println(v);
        System.out.println("-----------");
        double v1 = stestHeap(testData, true);
        System.out.println(v1);
    }
}
