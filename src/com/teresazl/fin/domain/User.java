package com.teresazl.fin.domain;

import java.util.Date;

/**
 * @author Teresa
 */
public class User {
    private long id;
    private String name;
    private Date birthday;
    private long age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return id + "\t" + name + "\t" + birthday + "\t" + age;
    }
}
