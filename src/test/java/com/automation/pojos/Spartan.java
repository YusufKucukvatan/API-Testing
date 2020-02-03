package com.automation.pojos;

import com.google.gson.annotations.SerializedName;

public class Spartan {

    @SerializedName("id")
    private int spartan_id;
    @SerializedName("name")
    private String spartan_name;
    @SerializedName("gender")
    private String spartan_gender;
    @SerializedName("phone")
    private long spartan_phone;

    public Spartan() {
    }

    public Spartan(int spartan_id, String spartan_gender, String spartan_name, long spartan_phone) {
        this.spartan_id = spartan_id;
        this.spartan_name = spartan_name;
        this.spartan_gender = spartan_gender;
        this.spartan_phone = spartan_phone;
    }

    public int getSpartan_id() {
        return spartan_id;
    }

    public void setSpartan_id(int spartan_id) {
        this.spartan_id = spartan_id;
    }

    public String getSpartan_name() {
        return spartan_name;
    }

    public void setSpartan_name(String spartan_name) {
        this.spartan_name = spartan_name;
    }

    public String getSpartan_gender() {
        return spartan_gender;
    }

    public void setSpartan_gender(String spartan_gender) {
        this.spartan_gender = spartan_gender;
    }

    public long getSpartan_phone() {
        return spartan_phone;
    }

    public void setSpartan_phone(long spartan_phone) {
        this.spartan_phone = spartan_phone;
    }

    @Override
    public String toString() {
        return "Spartan{" +
                "spartan_id=" + spartan_id +
                ", spartan_name='" + spartan_name + '\'' +
                ", spartan_gender='" + spartan_gender + '\'' +
                ", spartan_phone=" + spartan_phone +
                '}';
    }

    public Spartan withPhone(long phone) {
        this.spartan_phone = phone;
        return this;
    }

    public Spartan withName(String name) {
        this.spartan_name = name;
        return this;
    }

    public Spartan withGender(String gender) {
        this.spartan_gender = gender;
        return this;
    }

}
