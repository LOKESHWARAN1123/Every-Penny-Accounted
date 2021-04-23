package com.example.puthagum;

public class Employee {
    private String PhoneNumber;
    private String Name;

    public Employee() {
    }

    public Employee(String phoneNumber, String name) {
        PhoneNumber = phoneNumber;
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
