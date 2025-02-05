package hr.java.project.entiteti;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String surname;
    private String id;
    private String role;
    public String password;

    public User(String id, String name, String surname, String role, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Information about user:\nID: " + id +
                "\nPassword: " + password +
                "\nName: " + name +
                "\nSurname: " + surname +
                "\nRole: " + role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
