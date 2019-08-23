package com.company.arithmetic;

import java.util.Arrays;
import java.util.Random;

/**
 * 快速排序
 */
public class Qksort {
    public void pkSort(int[] arr){
        quickSort(arr,0,arr.length-1);
    }
    //对arr[l...r] 部分进行快速排序
    private void quickSort(int[] arr,int l,int r){
        if(l>=r){
            return;
        }
        //快速排序的优化 当我们的数组长的小于16 采用插入排序算法
//        if(r-l<=15){
//            insertion2(arr,l,r);
//        }
        //返回的是索引
        int p=partiton2(arr,l,r);
        quickSort(arr,l,p-1);
        quickSort(arr,p+1,r);
    }
    //对arr[l..r]部分进行partition 操作
    //返回p,使得arr[l..p-1]< arr[p]; arr[p+1...r]>arr[p]
    private int partiton(int[] arr,int l,int r){
        //排序前，先让基准值和随机的一个数进行交换。这样，基准值就有随机性。
        //就不至于在数组相对有序时，导致左右两边的递归规模不一致，产生最坏时间复杂度
        swap(arr,l,(int)(Math.random()*(r - l + 1)+l));
        int v =arr[l];
        int j=l;
        //arr[l+1..j] < v; arr[j+1...i] > v
        for (int i = l+1; i <=arr.length-1 ; i++) {
            if(arr[i]< v){
               swap(arr,i,j+1);
                j++;
            }
        }
        swap(arr,l,j);
        return j;
    }
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

    /**
     * 对于一个很多重复范围的数组进行排序  性能比之前的更加好
     * @param arr
     * @param l
     * @param r
     * @return
     */
    private int partiton2(int[] arr,int l,int r){
        //处理 数组近乎有序的 排序性能的提升
        //采用 一个在l 到 r 的范围里的随机元素 作为 标定元素
        swap(arr,l,(int)(Math.random()*(r - l + 1)+l));
        int v =arr[l];
        int i=l+1,j=r;
        while (true){
            while (i<=r&&arr[i]<v) i++;
            while (j>=l+1&& arr[j]>v) j--;
            if(i>j) break;
            int eru;
            //交换
          swap(arr,i,j);
            i++;
            j--;
        }
        swap(arr,l,j);
        return j;
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
        new Qksort().pkSort(integer);
        System.out.println(Arrays.toString(integer));
    }
}
