package com.company.map;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * 两个集合的交集
 */
public class Solution3 {
    public int[] interserct(int[] mums1,int[] mums2){
        TreeMap<Integer,Integer> map=new TreeMap<>();
        for (int num : mums1) {
            if(!map.containsKey(num)){
                map.put(num,1);
            }else{
                map.put(num,map.get(num)+1);
            }
        }
        ArrayList<Integer> list=new ArrayList<>();
        for (int num : mums2) {
            if(map.containsKey(num)){
                list.add(num);
                map.put(num,map.get(num)-1);
                if(map.get(num)==0)
                    map.remove(num);
            }
        }
        int[] ints=new int[list.size()];
        for (int i = 0; i < list.size(); i++){
            ints[i]=list.get(i);
        }
        return ints;
    }
}
