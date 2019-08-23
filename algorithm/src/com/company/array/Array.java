package com.company.array;


//自定义动态组类 自定义泛型
public class Array<E> {
    private E[] data;
    private int size;
    //构造函数传入 数组的容量 a
    public  Array(int a){
        data=(E[]) new Object[a];//强制类型转换
        size=0;
    }
    //构造函数传入 数组
    public Array(E[] arr){
        data=(E[])new Object [arr.length];
        for (int i = 0; i < arr.length ; i++) {
            data[i]=arr[i];
        }
        size=arr.length;
    }
    //无参构造函数 默认容量10
    public Array(){
        this(10);
    }
    //获取数组的中元素的个数
    public int getSize(){
        return size;
    }

    //获取数组的容量
    public int getA(){
        return data.length;
    }
    //判断数组是否为空
    public boolean isEmpty(){
        return size==0;
    }

    //在数组的后面添加一个元素
    public void addLast(E e){
        add(size,e);
    }
    //在数组的所有元素前面添加一个元素
    public void addFirst(E e){
        add(0,e);
    }
    //在第index个位置中添加一个元素e
    public void add(int index,E e){

        if(index < 0 || index > size)
            throw new IllegalArgumentException("传入的index是不符合规范的！");
        if(size>=data.length)
            resize(2*data.length);
        for(int i=size-1;i>=index;i--){
            data[i+1]=data[i];
        }
        data[index]=e;
        size++;
    }
    //获取指定索引的值
    public E get(int index){
        if(index<0||index>=size){
            throw new IllegalArgumentException("传入的参数不服");
        }
        return data[index];
    }
    public E getLast(){
        return get(size-1);
    }
    public E getFirst(){
        return get(0);
    }
    //设置指定位置的值
    public void setData(int index,E e){
        if(index<0||index>=size){
            throw new IllegalArgumentException("传入的参数不服");
        }
        data[index]=e;
    }
    //查询数组是否包含 e
    public boolean ccontains(E e){
        for (int i = 0; i < size; i++) {
            if(data[i].equals(e))
                return true;
        }
        return false;
    }
    //查询是否有e 元素 返回该元素的索引 没有则返回的是-1
    public int find(E e){
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e))
                return i;
        }
        return -1;
    }
    //删除指定索引的元素 返回删除的元素
    public E remove(int index){
        if(index<0||index>=size){
            throw new IllegalArgumentException("传入的参数不服");
        }
        E ret=data[index];
        for (int i = index+1; i < size; i++) {
            data[i-1]=data[i];
        }
        size--;
        if(size==data.length/4&& data.length/2!=0)
            resize(data.length/2);
        return ret;
    }
    //删除数组第一个元素
    public E removeFirst(){
        return remove(0);
    }
    //删除数组最后一个元素
    public E removeLast(){
        return remove(size-1);
    }
    //从数组中删除 指定元素
    public void removeElement(E e ){
        int index = find(e);
        if(index!=-1){
            remove(index);
        }
    }
    //交换两个元素的值
    public void swap(int i,int j){
        if(i<0||i>=size||j<0||j>=size){
            throw new IllegalArgumentException("参数错误!");
        }
        E t=data[i];
        data[i]=data[j];
        data[j]=t;
    }

    @Override
    public String toString(){
        StringBuilder res=new StringBuilder();
        res.append(String.format("Array:size=%d\n,capacity=%d\n",size,data.length));
        res.append("[");
        for(int i=0;i<size;i++){
            res.append(data[i]);
            if(i!=size-1)
                res.append(",");
        }
        res.append("]");
        return res.toString();
    }

    /**
     * 动态数组 扩容或者缩小容量
     * @param newCapacity
     */
    private void resize(int newCapacity){
        E[] newData=(E[])new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i]=data[i];
        }
        data=newData;
    }

}
