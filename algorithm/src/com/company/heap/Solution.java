package com.company.heap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 *
 */
public class Solution {
    //设置优先级类 定义 频次越低优先级越高
    private class Freq implements Comparable<Freq>{
        public int e,freq;//e 值 freq 频次

        public Freq(int e, int freq) {
            this.e = e;
            this.freq = freq;
        }

        @Override
        public int compareTo(Freq another) {
            if(this.freq<another.freq){
                return 1;
            }else if(this.freq>another.freq)
                return -1;
            else
                return 0;
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
        PriorityQueue<Freq> pq=new PriorityQueue<>();
        for (int key:map.keySet()){
            if(pq.getSize()<k){
                pq.enqueue(new Freq(key,map.get(key)));
            }else if(map.get(key)>pq.getFront().freq){
                pq.dequeue();
                pq.enqueue(new Freq(key,map.get(key)));
            }
        }
        List<Integer> list=new LinkedList<>();
        while (!pq.isEmpety()){
            list.add(pq.dequeue().e);
        }
        return list;
    }
}
