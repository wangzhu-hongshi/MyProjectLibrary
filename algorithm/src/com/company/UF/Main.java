package com.company.UF;

import java.util.Random;

public class Main {

    public static double tsetUF(UF uf,int m){
        int size = uf.getSize();
        Random random=new Random();
        long l = System.nanoTime();

        for (int i = 0; i < m; i++) {
            uf.isConnected(random.nextInt(size),random.nextInt(size));
        }

        for (int i = 0; i < m; i++) {
            uf.unionElements(random.nextInt(size),random.nextInt(size));
        }
        long l1 = System.nanoTime();

        return (l1-l)/1000000000.0;
    }
    public static void main(String[] args) {
        int size=1000000 ;
        int m=100000;
//
//        UnionFind1 unionFind1=new UnionFind1(size);
//        System.out.println("u1用了"+tsetUF(unionFind1,m)+"秒");
//
//        UnionFind22 unionFind22=new UnionFind22(size);
//        System.out.println("u2用了"+tsetUF(unionFind22,m)+"秒");

       UntonFind3 untonFind3=new UntonFind3(size);
        System.out.println("u3用了"+tsetUF(untonFind3,m)+"秒");

        UntonFind4 untonFind4=new UntonFind4(size);
        System.out.println("u4用了"+tsetUF(untonFind4,m)+"秒");


    }
}
