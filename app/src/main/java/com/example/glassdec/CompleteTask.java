package com.example.glassdec;

public class CompleteTask
{
    String dname,user,totalWeight;

    public CompleteTask()
    {
    }

    public CompleteTask(String dname, String user, String totalWeight) {
        this.dname = dname;
        this.user = user;
        this.totalWeight = totalWeight;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }
}
