package com.swh.ykm;

public class Dol{
    int price;
    String name;
    static{
        System.out.println("생성자보다 먼저 호출되요");
    }
    Dol(int price){
        this.price = price;
    }
    Dol(int price, String name){
        this(price);
        this.name = name;
    }
    Dol getToy(){ 
        return this;
    }
    void play(){
        System.out.println(this.name+"으로 놉니다.");
    }
}