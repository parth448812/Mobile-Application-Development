//Assignment 03
//Assignment1
//Parth Patel and Janvi Nandwani

package com.example.assignment3;

import java.io.Serializable;

public class Response implements Serializable {
    String name, email, role, income, education, maritial_status, living_status;

    public Response(String name, String email, String role, String income, String education, String maritial_status, String living_status) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.income = income;
        this.education = education;
        this.maritial_status = maritial_status;
        this.living_status = living_status;

    }


    public Response() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {

        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIncome() {

        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }
    public String getEducation() {

        return education;
    }
    public String getMarital() {

        return maritial_status;
    }
    public String getLiving() {

        return living_status;
    }
    public void setEducation(String education) {
        this.education = education;
    }
    public String getMartial_status() {

        return maritial_status;
    }
    public void setMaritial_status(String martial_status) {
        this.maritial_status = martial_status;
    }
    public String getLiving_status() {

        return living_status;
    }
    public void setLiving_status(String living_status) {
        this.living_status = living_status;
    }

}

