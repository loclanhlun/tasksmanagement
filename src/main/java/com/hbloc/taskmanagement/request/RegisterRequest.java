package com.hbloc.taskmanagement.request;

import java.util.List;

public class RegisterRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private Integer role;
    private List<Integer> permissionsId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public List<Integer> getPermissionsId() {
        return permissionsId;
    }

    public void setPermissionsId(List<Integer> permissionsId) {
        this.permissionsId = permissionsId;
    }
}
