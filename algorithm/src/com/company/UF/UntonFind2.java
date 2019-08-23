package com.company.UF;

/**
 * 并查集 孩子指向父亲
 */
public class UntonFind2 implements UF{
    //依然使用数组来存储每一个节点
    private int[] parent;
    public UntonFind2(int size){//传来数组大小
        parent=new int[size];
        //初始化时 让每个节点指向自己
        for (int i = 0; i < parent.length; i++) {
            parent[i]=i;
        }
    }
    public int getSize(){
        return parent.length;
    }
    /**
     * 找根节点  根节点的节点指向自己
     * 元素所在的节点向上寻找 根节点
     * O(h)复杂度， h为树的高度
     * @param p
     * @return
     */
    private int find(int p){
        if(p<0||p>=parent.length)
            throw new IllegalArgumentException("p is out of bound.");
        while (p!=parent[p])//判断是父节点是不是自己
            p=parent[p];//每次循环都找到自己的父节点 让然后指向它 直到根节点指向自己
        return p;//返回根节点
    }

    /**
     * 查看两个元素是否属于同一个集合
     * o(h)的复杂度  h为树的高度
     * @param p
     * @param q
     * @return
     */
    @Override
    public boolean isConnected(int p, int q) {

        return find(p)==find(q);
    }

    /**
     * 合并 两个元素所属的集合
     * o(h)的复杂度  h为树的高度
     * @param p
     * @param q
     */
    @Override
    public void unionElements(int p, int q) {
        int pRoot=find(p);
        int qRoot=find(q);

        if(pRoot==qRoot)
            return;
        //把p所在的根节点指向 q的根节点
        parent[pRoot]=qRoot;
    }


}
