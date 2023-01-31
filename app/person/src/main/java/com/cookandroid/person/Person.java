package com.cookandroid.person;

import java.util.List;

public class Person {
    private Long id;
   private String name;
   private String phone;

    public Person() {

    }

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public Person(Long id, String name, String phone){
        this.id = id;
        this.name=name;
        this.phone=phone;
    }
}
