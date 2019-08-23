package com.company.heap;

import java.util.*;
import java.util.PriorityQueue;

/**
 *
 */
public class Solution2 {
    //设置优先级类 定义 频次越低优先级越高
    private class Freq{
        public int e,freq;//e 值 freq 频次

        public Freq(int e, int freq) {
            this.e = e;
            this.freq = freq;
        }
    }
    public List<Integer> topkFrequent(int[] nums,int k){
        TreeMap<Integer,Integer> map=new TreeMap<>();
        for (int num : nums) {
            if(map.containsKey(num)){
                map.put(num,map.get(num)+1);
            }else{
                map.put(num,1);
            }
        }
        PriorityQueue<Freq> pq=new PriorityQueue<>(new Comparator<Freq>() {
            @Override
            public int compare(Freq a, Freq b) {
                return a.freq-b.freq;
            }
        });
        for (int key:map.keySet()){
            if(pq.size()<k){
                pq.add(new Freq(key,map.get(key)));
            }else if(map.get(key)>pq.peek().freq){
                pq.remove();
                pq.add(new Freq(key,map.get(key)));
            }
        }
        List<Integer> list=new LinkedList<>();
        while (!pq.isEmpty()){
            list.add(pq.remove().e);
        }
        return list;
    }
}
