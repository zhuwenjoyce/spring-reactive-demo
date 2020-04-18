package com.joyce.baeldung.spring_webflux;

/**
 * @author: Joyce Zhu
 * @date: 2020/4/16
 */
public class Employee {
    String name;
    Integer id;

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
