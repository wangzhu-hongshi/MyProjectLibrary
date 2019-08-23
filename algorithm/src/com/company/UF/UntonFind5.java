package com.company.UF;

/**
 * 并查集 孩子指向父亲
 * 并查集 优化版  基于rank的优化
 * 当执行 连接两个节点时  深度小的节点去指向深度大的节点
 *
 * 第五版 路径优化  find查询时要进行 路径压缩
 */
public class UntonFind5 implements UF{
    private int[] parent;
    private int[] rank; //记录树的深度大小

    public UntonFind5(int size){
        parent=new int[size];

        rank=new int[size];
        //初始化时 让每个节点指向自己
        for (int i = 0; i < parent.length; i++) {
            parent[i]=i;
            rank[i]=1;
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
        if(rank[pRoot]<rank[qRoot]){
            parent[pRoot]=qRoot;
        }else if(rank[qRoot]<rank[pRoot]){
            parent[qRoot]=pRoot;
        }else {//深度相等 才会进行维护
            parent[qRoot]=pRoot;
            rank[pRoot]+=1;
        }
    }

    /**
     * 找根节点  根节点的节点指向自己
     * O(h)复杂度， h为树的高度
     * @param p
     * @return
     */
    private int find(int p){
        if(p<0||p>=parent.length)
            throw new IllegalArgumentException("p is out of bound.");
        while (p!=parent[p]){
            //路径压缩 把p的父节点指向p的父节点的父节点
            parent[p]=parent[parent[p]];
            p=parent[p];
        }

        return p;
    }
}
