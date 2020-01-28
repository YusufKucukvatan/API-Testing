package com.automation.pojos;

import com.google.gson.annotations.SerializedName;

public class Location {

    private int location_id;
    private String street_address;
    private String postal_code;
    private String city;
    private String state_province;
    private String country_id;

    public Location(){

    }

    public Location(int location_id, String street_address, String postal_code, String city, String state_province, String country_id) {
        this.location_id = location_id;
        this.street_address = street_address;
        this.postal_code = postal_code;
        this.city = city;
        this.state_province = state_province;
        this.country_id = country_id;
    }

    public int getLocationId() {
        return location_id;
    }

    public void setLocationId(int location_id) {
        this.location_id = location_id;
    }

    public String getStreetAddress() {
        return street_address;
    }

    public void setStreetAddress(String street_address) {
        this.street_address = street_address;
    }
    public String getPostalCode() {
        return postal_code;
    }

    public void setPostalCode(String postal_code) {
        this.postal_code = postal_code;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getStateProvince() {
        return state_province;
    }

    public void setStateProvince(String state_province) {
        this.state_province = state_province;
    }
    public String getCountryId() {
        return country_id;
    }

    public void setCountryId(String country_id) {
        this.country_id = country_id;
    }

    @Override
    public String toString() {
        return "Location{" +
                "location_id=" + location_id +
                ", street_address='" + street_address + '\'' +
                ", postal_code='" + postal_code + '\'' +
                ", city='" + city + '\'' +
                ", state_province='" + state_province + '\'' +
                ", country_id='" + country_id + '\'' +
                '}';
    }
}
