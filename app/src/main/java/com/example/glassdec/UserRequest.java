package com.example.glassdec;

import android.net.Uri;

public class UserRequest
{
    String name,phno,address,imgpath;
    String uLocation;

    public UserRequest()
    {

    }



    public UserRequest(String name, String phno, String address,String imgpath,String uLocation) {

        this.name = name;
        this.phno = phno;
        this.address = address;
        this.imgpath = imgpath;
        this.uLocation=uLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getuLocation() {
        return uLocation;
    }

    public void setuLocation(String uLocation) {
        this.uLocation = uLocation;
    }
}
