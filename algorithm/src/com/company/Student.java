package com.company;

import com.company.array.Array;

public class Student {
    private String name;
    private Integer score;

    public Student(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }

    public static void main(String[] args) {
        Array<Student> array=new Array<>();
        array.addLast(new Student("王诸",90));
        array.addLast(new Student("丽娜",80));
        array.addLast(new Student("宏诗",98));
        System.out.println(array);
    }
}
