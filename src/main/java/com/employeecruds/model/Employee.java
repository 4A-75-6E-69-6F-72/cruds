package com.employeecruds.model;

// Model employee Employee for storing information about the employee
public class Employee {

    // Attributes of the employee
    private int id;
    private String name;
    private int age;
    private int phone;
    
    // No arguement contructor
    public Employee(){}

    // Constructor with all attribute
    public Employee(String name, int age, int phone){
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getPhone() {
        return phone;
    }
    public void setPhone(int phone) {
        this.phone = phone;
    }

}
