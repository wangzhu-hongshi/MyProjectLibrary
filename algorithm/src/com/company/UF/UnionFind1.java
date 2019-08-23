package com.company.UF;

/**
 * 并查集
 *第一版
 */
public class UnionFind1 implements UF {
    //使用数组来存储集合的编号
    private int[] id;
    //给并查集 指定 集合的个数
    public UnionFind1(int size){
        id=new int[size];
        for (int i = 0; i < id.length; i++) {
            //初始化集合编号 使他们都不相等
            id[i]=i;
        }
    }

    public int getSize(){
        return id.length;
    }

    /**
     * 查找元素p的所对应的集合编号
     * @param p
     * @return
     */
    private int find(int p){
        if(p<0||p>=this.getSize())
            throw new IllegalArgumentException("p is out of bound.");
        return id[p];
    }

    /**
     * 查询是否连接
     * @param p
     * @param q
     * @return
     */
    @Override
    public boolean isConnected(int p, int q) {
        return find(p)==find(q);
    }

    /**
     * 合并两个元素
     * @param p
     * @param q
     */
    @Override
    public void unionElements(int p, int q) {
        int pID=find(p);
        int qID=find(q);

        if(pID==qID)
            return;

        //遍历所有元素 如果属于p的集合 全部改写成 q 的集合 实现合并
        for (int i = 0; i < id.length; i++) {
            if(id[i]==pID)
                id[i]=qID;
        }
    }

}
