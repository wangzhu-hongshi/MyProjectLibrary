package com.company.segmentTree;

public class TreeMain {
    public static void main(String[] args) {
        Integer[] num={1,3,5,2,6,4,6,-1};
        SegmentTree2<Integer> tree=new SegmentTree2<Integer>(num, new Merger<Integer>() {
            @Override
            public Integer merger(Integer a, Integer b) {
                return a+b;
            }
        });
        System.out.println(tree);
        System.out.println(tree.query(0,3));

    }
}
