package com.example.glassdec;

public class Task {

    String userName;
    String driverName;
    String address;
    String phone;
    String location;

    public Task() {
    }

    public Task(String userName, String driverName, String address, String phone, String location) {
        this.userName = userName;
        this.driverName = driverName;
        this.address = address;
        this.phone = phone;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
