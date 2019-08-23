package com.company.BST;

import java.util.ArrayList;
import java.util.Random;

public class BSTMain {
    public static void main(String[] args) {
        BST<Integer> bst=new BST<>();

        Random random=new Random();
        int n=1000;
        for (int i = 0; i < n; i++) {
            bst.add(random.nextInt(10000));
        }
        ArrayList<Integer> list=new ArrayList<>();
        while (!bst.isEmpty()){
            list.add(bst.removeMax());
        }
        System.out.println(list);

        for (int i = 1; i < list.size(); i++) {
            if(list.get(i-1)<list.get(i)){
                throw new IllegalArgumentException("Error");
            }
//
        }
        System.out.println("成功");
    }
}
