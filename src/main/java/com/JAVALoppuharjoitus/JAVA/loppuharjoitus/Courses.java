package com.JAVALoppuharjoitus.JAVA.loppuharjoitus;

public class Courses {
    private String coursename;
    private String courseteacher;
    private String cid;

    public Courses(){}
    public Courses(String coursename, String courseteacher, String cid) {
        this.coursename = coursename;
        this.courseteacher = courseteacher;
        this.cid = cid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCourseteacher() {
        return courseteacher;
    }

    public void setCourseteacher(String courseteacher) {
        this.courseteacher = courseteacher;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

}
