package com.company;

import com.company.array.Array;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Array<Integer> array=new Array<Integer>();
        for (int i = 0; i < 10; i++) {
            array.addLast (i );
        }
        array.add(1,19);
        System.out.println(array);
        array.addFirst(-1);
        System.out.println(array);
        array.remove(2);
        array.remove(4);
        System.out.println(array);
        array.removeElement(-1);
        System.out.println(array);


    }
}
