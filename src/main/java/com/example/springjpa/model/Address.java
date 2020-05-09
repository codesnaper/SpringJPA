package com.example.springjpa.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Setter
@Getter
@ToString
public class Address {

    public Address(){}

    public Address(String line1, String line2, String city, String country, String zip) {
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.country = country;
        this.zip = zip;
    }

    private String line1;
    private String line2;
    private String city;
    private String country;
    private String zip;
}
