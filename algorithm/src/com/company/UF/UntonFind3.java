package com.company.UF;

/**
 * 并查集 孩子指向父亲
 * 并查集 优化版  基于size的优化
 * 当执行 连接两个节点时  把元素个数小的节点指向元素个数大的节点
 */
public class UntonFind3 implements UF{
    private int[] parent;
    private int[] sz; //sz[i] 表示以i为根的集合中元素的个数

    public UntonFind3(int size){
        parent=new int[size];

        sz=new int[size];
        //初始化时 让每个节点指向自己
        for (int i = 0; i < parent.length; i++) {
            parent[i]=i;
            sz[i]=1;//每个集合的元素个数只有1
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
        int pRoot = find(p);
        int qRoot = find(q);

        if(pRoot==qRoot)
            return;
        //将元素少的集合合并到元素多的集合里
        if(sz[pRoot]<sz[qRoot]){
            parent[pRoot]=qRoot;
            sz[qRoot]+=sz[pRoot];
        }else {
            parent[qRoot]=pRoot;
            sz[pRoot]+=sz[qRoot];
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
        while (p!=parent[p])
            p=parent[p];
        return p;
    }
}
