package com.company.arithmetic;

import java.util.Arrays;
import java.util.Random;

public class QuickSort3Ways {
    public void pkSort3Ways(int[] arr){
        pkSort3Ways(arr,0,arr.length-1);
    }

    /**
     * 三路快速排序处理 arr[l...r]
     * 将arr[l....r] 分成 <v; ==v; >v 三个部分
     * 之后递归对 < v; >v 两个部分继续进行三路快速排序
     * @param arr
     * @param l
     * @param r
     */
    private void pkSort3Ways(int[] arr,int l,int r){
        if(l>=r){
            return;
        }
        //快速排序的优化 当我们的数组长的小于16 采用插入排序算法
//        if(r-l<=15){
//            insertion2(arr,l,r);
//        }
        //排序前，先让基准值和随机的一个数进行交换。这样，基准值就有随机性。
        //就不至于在数组相对有序时，导致左右两边的递归规模不一致，产生最坏时间复杂度
        swap(arr,l,(int)(Math.random()*(r - l + 1)+l));
        int v =arr[l];
        int lt =l;//arr[l+1..lt] <v
        int gt=r+1;//arr[gt..r] >v
        int i=l+1;//arr[lt+1...i] ==v
        while (i<gt){
            if(arr[i]<v){
                swap(arr,i,lt+1);
                lt++;
                i++;
            }else if(arr[i]>v){
                swap(arr,i,gt-1);
                gt--;
            }else {//arr[i] == v
                 i++;
            }
        }
        swap(arr,l,lt);
        pkSort3Ways(arr,l,lt-1);
        pkSort3Ways(arr,gt,r);
    }
    public static void swap(int[] arr, int i, int j) {
        if (i != j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
    public static void main(String[] args) {
        int[] integer={2,4,1,6,5,3,7,9,8};
        //new MergerSort().mergerSort(integer);
        new QuickSort3Ways().pkSort3Ways(integer);
        System.out.println(Arrays.toString(integer));
    }
}
