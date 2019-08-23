package com.company.set;

import java.util.ArrayList;
import java.util.TreeSet;

//两个数组的交集
public class Solution2 {
    public int[] intersection(int[] mums1,int[] mums2){
        TreeSet<Integer> treeSet=new TreeSet<>();
        for (int mun1 : mums1) {
            treeSet.add(mun1);
        }
        ArrayList<Integer> list=new ArrayList<>();
        for (int mum2 : mums2) {
            if(treeSet.contains(mum2)){
                list.add(mum2);
                treeSet.remove(mum2);
            }
        }
        int[] ints=new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ints[i]=list.get(i);
        }
        return ints;
    }
}
