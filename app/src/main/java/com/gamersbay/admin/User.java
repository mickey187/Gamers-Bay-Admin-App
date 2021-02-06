package com.gamersbay.admin;

public class User {
    private String Full_name;
    private String ID;
    private int Balance;
    private String Phone_number;
    private String Email;

    public User() {
    }

    public User(String full_name, String ID, int balance, String phone_number, String email) {
        Full_name = full_name;
        this.ID = ID;
        Balance = balance;
        Phone_number = phone_number;
        Email = email;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFull_name() {
        return Full_name;
    }

    public void setFull_name(String full_name) {
        Full_name = full_name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
