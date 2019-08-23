package com.example.demo.bst;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 计算两个数组的相同元素
 */
public class Solution2 {
    //不能重复的
    public int[] intersection(int[] nums1,int[] nums2){
        TreeSet<Integer> set=new TreeSet<>();
        for (int num1 : nums1) {
            set.add(num1);
        }
        List<Integer> list=new ArrayList<>();
        for (int num2 : nums2) {
            if(set.contains(num2)){
                list.add(num2);
                set.remove(num2);
            }
        }

        int[] res=new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i]=list.get(i);
        }
        return res;
    }
    public int[] intersect(int[] nums1,int[] nums2){
        TreeMap<Integer,Integer> map=new TreeMap<>();
        for (int num1 : nums1) {
            if(!map.containsKey(num1)){
                map.put(num1,1);
            }else
                map.put(num1,map.get(num1)+1);
        }
        ArrayList<Integer> list=new ArrayList<>();
        for (int num2 : nums2) {
            if(map.containsKey(num2)){
                list.add(num2);
                map.put(num2,map.get(num2)-1);
                if(map.get(num2)==0){
                    map.remove(num2);
                }
            }
        }
        int[] res=new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i]=list.get(i);
        }
        return res;
    }
}
