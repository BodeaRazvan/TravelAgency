package com.example.travelagency.entity;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String userName;
    @Column
    private String password;
    @Column
    private String address;
    @Column
    private String email;
    @Column
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Package> packages;

    public User(String userName, String password, String address, String email, String role, List<Package> packages) {
        this.userName = userName;
        this.password = password;
        this.address = address;
        this.email = email;
        this.role = role;
        this.packages = packages;
    }

    public User() {

    }

    public User(int id, String userName, String password, String address, String email, String role, List<Package> packages) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.address = address;
        this.email = email;
        this.role = role;
        this.packages = packages;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public int getId() {
        return id;
    }
}
