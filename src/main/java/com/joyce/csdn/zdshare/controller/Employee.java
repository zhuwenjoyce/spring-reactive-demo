package com.joyce.csdn.zdshare.controller;

/**
 * @author: Joyce Zhu
 * @date: 2020/4/16
 */
public class Employee {
    String name;
    Integer id;

    public Employee(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
