package com.example.glassdec;

public class User
{
    public String hpname,phno,email,chooseAcc;
    public User()
    {

    }

    public User(String hpname, String phno, String email,String chooseAcc)
    {
        this.hpname = hpname;
        this.phno = phno;
        this.email = email;
        this.chooseAcc = chooseAcc;
    }

    public String getHpname() {
        return hpname;
    }

    public void setHpname(String hpname) {
        this.hpname = hpname;
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
