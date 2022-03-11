package com.example.travelagency.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "destination")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String country;

    @OneToMany(mappedBy = "destination")
    private List<Package> packageList;

    public Destination(String name, String country, List<Package> packageList) {
        this.name = name;
        this.country = country;
        this.packageList = packageList;
    }
    public Destination(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
