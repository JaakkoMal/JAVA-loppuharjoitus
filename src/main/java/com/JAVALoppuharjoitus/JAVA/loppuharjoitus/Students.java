package com.JAVALoppuharjoitus.JAVA.loppuharjoitus;

public class Students {
    private String fname;
    private String lname;
    private String address;
    private String sid;

    public Students(String fname, String lname, String address, String sid) {
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.sid = sid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLnames() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
