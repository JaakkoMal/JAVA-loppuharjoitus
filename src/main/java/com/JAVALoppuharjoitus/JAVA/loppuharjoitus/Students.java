package com.JAVALoppuharjoitus.JAVA.loppuharjoitus;

public class Students {
    private String fnames;
    private String lnames;
    private String addresss;
    private String sid;

    public Students(String fnames, String lnames, String addresss, String sid) {
        this.fnames = fnames;
        this.lnames = lnames;
        this.addresss = addresss;
        this.sid = sid;
    }

    public String getFnames() {
        return fnames;
    }

    public void setFnames(String fnames) {
        this.fnames = fnames;
    }

    public String getLnames() {
        return lnames;
    }

    public void setLnames(String lnames) {
        this.lnames = lnames;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
