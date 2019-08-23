package com.company.segmentTree;

public class NumArray {
    private SegmentTree<Integer> segmentTree;
    public NumArray(int[] num){
        if(num.length>0){
            Integer[] data=new Integer[num.length];
            for (int i = 0; i < data.length; i++) {
                data[i]=num[i];
            }
            segmentTree=new SegmentTree<Integer>(data, (a, b) -> a + b);
        }
    }
    public int sumRange(int i,int j){
        if(segmentTree==null){
            throw new IllegalArgumentException("Segment Tree is null");
        }
        return segmentTree.query(i,j);
    }
}
