package com.example.myapplication;

public class AccountModel {
    private int id;
    private String name;
    private String email;

    private String password;

    private boolean isCurrentUser;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountModel(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isCurrentUser = false;
    }

    public AccountModel(String email, String password) {
        this.isCurrentUser = isCurrentUser;
        this.id = -2;
        this.email = email;
        this.password = password;
        this.name = "none";
        this.isCurrentUser = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isCurrentUser() {
        return isCurrentUser;
    }

    public void setCurrentUser(boolean currentUser) {
        isCurrentUser = currentUser;
    }

    @Override
    public String toString() {
        return "AccountModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isCurrentUser=" + isCurrentUser +
                '}';
    }
}
