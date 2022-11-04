package com.QrAttendence.student;

public class StudentModel {
    public String str_name, str_class, str_roll_no, str_phone_nom, str_enroll_no, str_pass;

    public String getStr_name() {
        return str_name;
    }

    public String getStr_class() {
        return str_class;
    }

    public String getStr_roll_no() {
        return str_roll_no;
    }

    public String getStr_phone_nom() {
        return str_phone_nom;
    }

    public String getStr_enroll_no() {
        return str_enroll_no;
    }

    public String getStr_pass() {
        return str_pass;
    }

    //str_roll_no is email

    public StudentModel(String str_name, String str_class, String str_roll_no, String str_phone_nom, String str_enroll_no, String str_pass) {
        this.str_name = str_name;
        this.str_class = str_class;
        this.str_roll_no = str_roll_no;
        this.str_phone_nom = str_phone_nom;
        this.str_enroll_no = str_enroll_no;
        this.str_pass = str_pass;
    }

}
