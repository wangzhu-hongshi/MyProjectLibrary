package com.company.segmentTree;

public class SegmentTree2<E> {

    private Merger<E> merger;

    private E[] tree;
    private E[] data;
   public SegmentTree2(E[] arr,Merger<E> merger){
       this.merger=merger;
       data=(E[]) new Object[arr.length];
       for (int i = 0; i < arr.length; i++) {
           data[i]=arr[i];
       }
       tree=(E[]) new Object[arr.length*4];
       buildSegmentTree(0,0,arr.length-1);
   }

    private void buildSegmentTree(int treeIndex, int l, int r) {
        if(l==r){
            tree[treeIndex]=data[l];
            return;
        }
        int lfte = lfteChild(treeIndex);
        int right = rightChild(treeIndex);
        int mid=l+(r-l)/2;

        buildSegmentTree(lfte,l,mid);
        buildSegmentTree(right,mid+1,r);
        tree[treeIndex]=merger.merger(tree[lfte],tree[right]);
    }

    public E query(int queryL,int queryR){
        if(queryL<0||queryL>=data.length ||
                queryR<0|| queryR>=data.length){
            throw new IllegalArgumentException("Index is illegal.");
        }
        //进行递归操作
        return query(0,0,data.length,queryL,queryR);
    }
    private E query(int treeIndex,int l,int r,int queryL,int queryR){
       if(l==queryL&&r==queryR){
           return tree[treeIndex];
       }
        int lfte = lfteChild(treeIndex);
        int right = rightChild(treeIndex);
        int mid=l+(r-l)/2;
        if(queryL>=mid+1){
            return query(right,mid+1,r,queryL,queryR);
        }else if(queryR<=mid){
            return query(lfte,l,mid,queryL,queryR);
        }
        E lftequery = query(lfte, l, mid, queryL, mid);
        E rightquery = query(right, mid + 1, r, mid + 1, queryR);
        return merger.merger(lftequery,rightquery);
    }

    public void set(int index,E val){
        if(index<0||index>=data.length){
            throw new IllegalArgumentException("Index is illegal");
        }
        data[index]=val;
        set(0,0,data.length-1,index,val);
    }
    private void set(int treeIndex,int l,int r,int index,E val){
       if(l==r){
           tree[treeIndex]=val;
           return;
       }
        int lfte = lfteChild(treeIndex);
        int right = rightChild(treeIndex);
        int mid=l+(r-l)/2;

        if(index>=mid+1){
            set(right,mid+1,r,index,val);
        }else if(index<=mid){
            set(lfte,l,mid,index,val);
        }
        tree[treeIndex]=merger.merger(tree[lfte],tree[right]);
    }

    public int getSize(){
       return data.length;
   }
   public E getIndex(int index){
       if(index<0||index>=data.length){
           throw new IllegalArgumentException("越界");
       }
       return data[index];
   }
    public int lfteChild(int index){
        return (2*index)+1;
    }

    public int rightChild(int index){
        return (2*index)+2;
    }
    @Override
    public String toString(){
        StringBuilder res=new StringBuilder();
        res.append("[");
        for (int i = 0; i <=tree.length - 1; i++) {
            if(tree[i]!=null)
                res.append(tree[i]);
            else
                res.append("null");
            if(i!=tree.length-1)
                res.append(",");
            else
                res.append("]");
        }
        return res.toString();
    }
}
