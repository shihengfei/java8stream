package com.camelot.share.basic;

/**
 * <p>
 * Description:[]
 * </p>
 *
 * @author shf
 * @version 1.0
 * @date Created on  2020/6/9 13:38
 */
public class Student {

    private Long id;

    private String name;

    private Integer age;

    private  Double money;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", money=" + money +
                '}';
    }

    public Student(Long id, String name, Integer age, Double money) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.money = money;
    }

    public Long getId() {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
