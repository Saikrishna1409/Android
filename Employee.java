package com.example.employee_crud;

public class Employee {
    private int id;
    private String name;
    private String dob;
    private String department;
    private String phone;
    private String address;

    public Employee(int id, String name, String dob, String department, String phone, String address) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.department = department;
        this.phone = phone;
        this.address = address;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDob() { return dob; }
    public String getDepartment() { return department; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
}

