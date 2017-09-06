package com.example.urgenism.tenantscreening.List;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by urgenism on 7/25/17.
 */

public class UserInformation  {

    private String user_ID;
    private String name;
    private String address;


    private String sex;
    private String date_of_birth;
    private String citizenship_no;
    private String mobile_no;
    public UserInformation(){

    }

    public UserInformation(String user_ID, String name, String address, String sex,String DOB,
                           String citizenshipNo, String mobile_no) {
        this.user_ID = user_ID;
        this.name = name;
        this.address = address;
        this.mobile_no = mobile_no;
        this.sex = sex;
        this.date_of_birth = DOB;
        this.citizenship_no = citizenshipNo;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String DOB) {
        this.date_of_birth = DOB;
    }

    public String getCitizenship_no() {
        return citizenship_no;
    }

    public void setCitizenship_no(String citizenshipNo) {
        this.citizenship_no = citizenshipNo;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }




}
