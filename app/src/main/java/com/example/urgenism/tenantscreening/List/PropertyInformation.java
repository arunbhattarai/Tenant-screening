package com.example.urgenism.tenantscreening.List;

/**
 * Created by urgenism on 7/28/17.
 */

public class PropertyInformation {



    private String property_ID;
    private String propertyType;
    private String propertyName;
    private String propertyNumber;
    private String address;
    private String ward_no;
    private String municipality;
    private String district;
    private String zone;
    private String amount;
    private String tax;
    private String totalAmount;
    private String note;

    public PropertyInformation(){

    }

    public PropertyInformation(String propertyID, String propertyType, String propertyName, String propertyNumber,
                               String address, String ward_no, String municipality, String district,
                               String zone, String amount, String tax, String totalAmount, String note) {
        this.property_ID = propertyID;
        this.propertyType = propertyType;
        this.propertyName = propertyName;
        this.propertyNumber = propertyNumber;
        this.address = address;
        this.ward_no = ward_no;
        this.municipality = municipality;
        this.district = district;
        this.amount = amount;
        this.tax = tax;
        this.totalAmount = totalAmount;
        this.zone = zone;
        this.note = note;
    }


    public String getProperty_ID() {
        return property_ID;
    }

    public void setProperty_ID(String property_ID) {
        this.property_ID = property_ID;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyNumber() {
        return propertyNumber;
    }

    public void setPropertyNumber(String propertyNumber) {
        this.propertyNumber = propertyNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWard_no() {
        return ward_no;
    }

    public void setWard_no(String ward_no) {
        this.ward_no = ward_no;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
