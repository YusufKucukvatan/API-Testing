package com.automation.pojos;

import com.google.gson.annotations.SerializedName;

public class Contact {
    private int contactId;
    private String emailAddress;
    private String phone;
    @SerializedName("premanentAddress")
    private String permanentAddress;

    public Contact(){

    }

    public Contact(String emailAddress, String phone, String permanentAddress) {
        this.emailAddress = emailAddress;
        this.phone = phone;
        this.permanentAddress = permanentAddress;
    }

    public int getContactId() {
        return contactId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", emailAddress='" + emailAddress + '\'' +
                ", phone='" + phone + '\'' +
                ", permanentAddress='" + permanentAddress + '\'' +
                '}';
    }

}