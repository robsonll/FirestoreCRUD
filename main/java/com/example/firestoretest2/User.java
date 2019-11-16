package com.example.firestoretest2;

public class User {

    private String userId;
    private String userName;
    private String userEmail;
    private boolean isAdmin;

    public User() {}

    public User(String userName, String userEmail, boolean isAdmin) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.isAdmin = isAdmin;
    }

    public User(String userId, String userName, String userEmail, boolean isAdmin) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.isAdmin = isAdmin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

}
