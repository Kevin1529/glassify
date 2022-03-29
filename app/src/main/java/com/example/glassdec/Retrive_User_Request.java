package com.example.glassdec;

public class Retrive_User_Request {
    private String address;
    private String name;
    private String imgpath;
    private String phno;
    private String uLocation;

    public Retrive_User_Request() {
    }

    public Retrive_User_Request(String address, String name, String imgpath, String phno, String uLocation) {
        this.address = address;
        this.name = name;
        this.imgpath = imgpath;
        this.phno = phno;
        this.uLocation = uLocation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getuLocation() {
        return uLocation;
    }

    public void setuLocation(String uLocation) {
        this.uLocation = uLocation;
    }
}
