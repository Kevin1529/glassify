package com.example.glassdec;

public class Retrive_User {
    private String chooseAcc;
    private String name;
    private String email;
    private String phno;

    public Retrive_User() {
    }

    public Retrive_User(String chooseAcc, String name, String email, String phno) {
        this.chooseAcc = chooseAcc;
        this.name = name;
        this.email = email;
        this.phno = phno;
    }

    public String getChooseAcc() {
        return chooseAcc;
    }

    public void setChooseAcc(String chooseAcc) {
        this.chooseAcc = chooseAcc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }
}
