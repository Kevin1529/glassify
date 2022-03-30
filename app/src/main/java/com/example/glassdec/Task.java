package com.example.glassdec;

public class Task {

    String userName;
    String driverName;
    String address;
    String phone;

    public Task(String userName, String driverName, String address, String phone) {
        this.userName = userName;
        this.driverName = driverName;
        this.address = address;
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
