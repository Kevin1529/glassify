package com.example.glassdec;

public class DriverDetails
{
    String dName,dCarName,dVechNo;
    public DriverDetails()
    {

    }

    public DriverDetails(String dName, String dCarName, String dVechNo)
    {
        this.dName = dName;
        this.dCarName = dCarName;
        this.dVechNo = dVechNo;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdCarName() {
        return dCarName;
    }

    public void setdCarName(String dCarName) {
        this.dCarName = dCarName;
    }

    public String getdVechNo() {
        return dVechNo;
    }

    public void setdVechNo(String dVechNo) {
        this.dVechNo = dVechNo;
    }
}
