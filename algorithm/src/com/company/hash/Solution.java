package com.company.hash;

/**
 * 给定一个字符串 ，找到他的第一个不重复的字符 并返回它的索引。如果不存在 则返回一
 */
public class Solution {

    public int firstUniqChar(String s){
        int[] arr=new int[26];

        for (int i = 0; i < s.length(); i++) {
            arr[s.charAt(i)-'a']++;
        }
        for (int i = 0; i < s.length(); i++) {
            if(arr[s.charAt(i)-'a']==1)
                return i;

        }
        return -1;
    }
}
