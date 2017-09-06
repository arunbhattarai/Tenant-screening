package com.example.urgenism.tenantscreening.List;

/**
 * Created by urgenism on 7/28/17.
 */

public class TenantInformation {


    private String tenantID;
    private String tenantName;
    private String email;
    private String mobileNo;
    private String sex;
    private String DOB;
    private String citizenshipNo;
    private String zone;
    private String district;
    private String municipality;
    private String wardNo;
    private String fatherName;
    private String maritalStatus;
    private String moveInDate;
    private String rentAmount;
    private String note;

    public TenantInformation(){

    }


    public TenantInformation(String tenantID,String tenantName, String email, String mobileNo,
                             String sex, String DOB, String citizenshipNo, String zone,
                             String district, String municipality, String wardNo,
                             String fatherName, String maritalStatus, String moveInDate,
                              String rentAmount, String note) {
        this.tenantID = tenantID;
        this.tenantName = tenantName;
        this.email = email;
        this.mobileNo = mobileNo;
        this.sex = sex;
        this.DOB = DOB;
        this.citizenshipNo = citizenshipNo;
        this.zone = zone;
        this.district = district;
        this.municipality = municipality;
        this.wardNo = wardNo;
        this.fatherName = fatherName;
        this.maritalStatus = maritalStatus;
        this.moveInDate = moveInDate;
        this.rentAmount = rentAmount;
        this.note = note;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCitizenshipNo() {
        return citizenshipNo;
    }

    public void setCitizenshipNo(String citizenshipNo) {
        this.citizenshipNo = citizenshipNo;
    }

    public String getMoveInDate() {
        return moveInDate;
    }

    public void setMoveInDate(String moveInDate) {
        this.moveInDate = moveInDate;
    }

    public String getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(String rentAmount) {
        this.rentAmount = rentAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String wardNo) {
        this.wardNo = wardNo;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }



}
