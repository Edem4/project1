package ru.alishev.springcourse.models;

import javax.validation.constraints.*;

public class Person {
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2,max = 30, message = "Name should be between 2 and 30 characters")
    private String name;
    @Min(value = 0, message = "Age should be greater than 0")
    private int age;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Адресс не в том формате!")
    private String address;


    public Person() {
    }

    public String getAddress() {
        return address;
    }

    public Person(int id, String name, int age, String email, String address) {
       this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

}