package com.company.array;

import org.omg.CORBA.Object;

public class Array2 <E> {
    private E[] data;
    private int size;
    public Array2(){
        data=(E[]) new Object[10];
        size=0;
    }

    public int getSize(){
        return size;
    }
    public boolean isEmpyt(){
        return size==0;
    }
    public int lenght(){
        return data.length;
    }
    public void add(E e,int index){


        if(index<0||index>size){
            throw new IllegalArgumentException("越界");
        }
        if(size==data.length){
            resize(2*data.length);
        }

        for (int i=size-1;i>=index;i--){
            data[i+1]=data[i];
        }
        data[index]=e;
        size++;
    }

    private void resize(int newCapacity) {
        E[] newData=(E[]) new Object[newCapacity];
        for (int i = 0; i <size; i++) {
            newData[i]=data[i];
        }
        data=newData;
    }

    public void addLast(E e){add(e,size);
    }
    public void addFirst(E e){
        add(e,0);
    }

    public E get(int index){
        if(index<0||index>=size){
            throw new IllegalArgumentException("越界");
        }
        return data[index];
    }
    public void set (E e,int index){
        if(index<0||index>=size){
            throw new IllegalArgumentException("越界");
        }
        data[index]=e;
    }
    public boolean contains(E e){
        for (int i = 0; i < size; i++) {
            if (data[i]==e)
                return true;
        }
        return false;
    }
    public int find(E e){
        for (int i = 0; i < size; i++) {
            if(data[i]==e)
                return i;
        }
        return -1;
    }
    public E remove(int index){
        if(index<0||index>=size){
            throw new IllegalArgumentException("越界");
        }

        E ret=data[index];
        for (int i = index+1; i < size; i++) {
            data[i-1]=data[i];
        }
        size--;
        if(size==data.length/4&&data.length/2!=0){
            resize(data.length/2);
        }
        return ret;
    }
    public E removeLast(){
        return remove(size-1);
    }
    public E removeFirst(){
        return remove(0);
    }
    public boolean removerElement(E e){
        int index = find(e);
        if(index!=-1) {
            remove(index);
            return true;
        }
        return false;

    }

    @Override
    public String toString(){
        StringBuilder builder=new StringBuilder();
        builder.append("[");
        for (int i = 0; i < size; i++) {
            builder.append(data[i]);
            if(i==size-1){
               builder.append("]");
            }else
                builder.append(",");
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        Array2<Integer> array2=new Array2();
        int[] arr={2,4,1,5,6};
        for (int i = 0; i < arr.length; i++) {
            array2.addLast(arr[i]);
        }
        System.out.println(array2);
        array2.addFirst(10);
        System.out.println(array2);
        array2.removeLast();
        System.out.println(array2);
        array2.remove(1);
        System.out.println(array2);

    }


}
