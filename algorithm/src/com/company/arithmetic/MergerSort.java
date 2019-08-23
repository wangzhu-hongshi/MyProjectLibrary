package com.company.arithmetic;

import com.company.array.Array;

import java.util.Arrays;

/**
 * 归并排序 使用的是递归的方式
 */
public class MergerSort {
    public void mergerSort(int[] arr){
        mergerSort(arr,0,arr.length-1);
    }
    //自顶向下的归并排序
    private void mergerSort(int[] arr,int l,int r){
        if(l==r){
            return;
        }
        //先把数组分半
        int mid=l+(r-l)/2;
        mergerSort(arr,l,mid);
        mergerSort(arr,mid+1,r);
        if(arr[mid]>arr[mid+1]) {
            //在进行归并排序
            merger(arr, l, mid, r);
        }

    }
    private void merger(int[] arr,int l,int mid,int r){
        //首先复制一份arr一样的数组
       int[] tmp=new int[arr.length];
       int r1=mid+1;
       int tIndex=l;
       int cIndex=l;

       //逐个归并
        while (l<=mid&& r1<=r){
            if(arr[l]<=arr[r1])
                tmp[tIndex++]=arr[l++];
            else
                tmp[tIndex++]=arr[r1++];
        }
        // 将左边剩余的归并
        while (l<=mid){
            tmp[tIndex++]=arr[l++];
        }
        // 将右边剩余的归并
        while (r1<=r){
            tmp[tIndex++]=arr[r1++];
        }
        //从临时数组拷贝到原数组
        while (cIndex<=r){
            arr[cIndex]=tmp[cIndex];
            cIndex++;
        }

    }
    //自底向上 的归并排序
    public void mergeSortBu(int[] arr){
        for (int sz = 1; sz <=arr.length; sz+=sz) {
            for (int i = 0; i +sz<arr.length ; i+=sz+sz) {
                merger(arr,i,i+sz-1,Math.min(i+sz+sz-1,arr.length-1));
            }
        }
    }

    public static void main(String[] args) {
        int[] integer={2,4,1,6,5,3,7,9,8};
        //new MergerSort().mergerSort(integer);
        new MergerSort().mergeSortBu(integer);
        System.out.println(Arrays.toString(integer));
    }
}
