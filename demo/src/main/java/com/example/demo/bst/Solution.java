package com.example.demo.bst;

import java.util.TreeSet;

public class Solution {
    public int uniqueMorseRepresentations(String[] words){
        String[] codes={".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
        TreeSet<String> treeSet=new TreeSet();
        for (String word : words) {
            StringBuilder builder=new StringBuilder();
            for (int i = 0; i < word.length(); i++) {
                String code = codes[word.charAt(i) - 'a'];
                builder.append(code);
            }
            treeSet.add(builder.toString());
        }
        return treeSet.size();
    }
}
