package com.example.foodzy;
public class userData {
    private String fname;
    private String lname;
    private long phno;
    private String mail;
    private String pass;
    private String dob;
    private String gender;

    public userData(String fname, String lname, long phno, String mail, String pass, String dob, String gender) {
        this.fname = fname;
        this.lname = lname;
        this.phno = phno;
        this.mail = mail;
        this.pass = pass;
        this.dob = dob;
        this.gender = gender;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public long getPhno() {
        return phno;
    }

    public void setPhno(long phno) {
        this.phno = phno;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "userData{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", phno=" + phno +
                ", mail='" + mail + '\'' +
                ", pass='" + pass + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
