package com.company.UF;

public class UnionFind22 implements UF{
    private int[] parent;

    public UnionFind22(int size){
        parent=new int[size];
        for (int i = 0; i < parent.length; i++) {
            parent[i]=i;
        }
    }

    public int getSize(){
        return parent.length;
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p)==find(q);
    }

    @Override
    public void unionElements(int p, int q) {
        int pRoot=find(p);
        int qRoot=find(q);
        if(pRoot==qRoot)
            return;
        parent[pRoot]=qRoot;
    }

    /**
     * 查询根节点
     * @param p
     * @return
     */
    private int find(int p){
        if(p<0||p>=parent.length){
            throw new IllegalArgumentException("越界");
        }
        while (p!=parent[p]){
            p=parent[p];
        }
        return p;
    }


}
