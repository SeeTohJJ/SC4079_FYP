package com.SeeTohJJ.Backend.user.model;

import com.SeeTohJJ.Backend.auth.model.User;

public class UserProfile {

    private int userId;
    private User user;
    private String username;
    private String gender;
    private int age;
    private String employmentStatus;
    private int income;
    private String country;

    public UserProfile() {}

    public UserProfile(User user, String username, String gender, int age, String employmentStatus, int income, String country) {
        this.user = user;
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.employmentStatus = employmentStatus;
        this.income = income;
        this.country = country;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}