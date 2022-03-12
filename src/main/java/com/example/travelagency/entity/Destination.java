package com.example.travelagency.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "destination")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String country;

    @OneToMany(mappedBy = "destination" , orphanRemoval = true)
    private List<Package> packageList;

    public Destination(String country, List<Package> packageList) {
        this.country = country;
        this.packageList = packageList;
    }
    public Destination(){

    }

    public Destination(int id, String country, List<Package> packageList) {
        this.id = id;
        this.country = country;
        this.packageList = packageList;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Package> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<Package> packageList) {
        this.packageList = packageList;
    }

    public int getId() {
        return id;
    }
}
