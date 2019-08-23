package com.company.heap;

import java.util.*;
import java.util.PriorityQueue;

public class Solution33 {
    public List<Integer> topkFrequent(int[] nums, int k){

        TreeMap<Integer,Integer> map=new TreeMap();
        for (int num : nums) {
            if(!map.containsKey(num)){
                map.put(num,1);
            }else {
                map.put(num,map.get(num)+1);
            }
        }

        //创建一个优先队列 传入一个比较器 计算优先级
        PriorityQueue<Integer> queue=new PriorityQueue<>(new Comparator<Integer>(){

            @Override
            public int compare(Integer o1, Integer o2) {
                return map.get(01)-map.get(02);
            }
        });

        for (Integer key : map.keySet()) {
            if(queue.size()<k){
                queue.add(key);
            }else if(map.get(key)>map.get(queue.peek())) {
                queue.remove();
                queue.add(key);
            }
        }

        List<Integer> list=new ArrayList<>();

        while (!queue.isEmpty()){
            list.add(queue.remove());
        }
        return list;
    }
}
