package com.example.glassdec;

public class User
{
    public String name,phno,email,chooseAcc;
    public User()
    {

    }

    public User(String name, String phno, String email,String chooseAcc)
    {
        this.name = name;
        this.phno = phno;
        this.email = email;
        this.chooseAcc = chooseAcc;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChooseAcc() {
        return chooseAcc;
    }

    public void setChooseAcc(String chooseAcc) {
        this.chooseAcc = chooseAcc;
    }
}
