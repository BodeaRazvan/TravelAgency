package com.example.travelagency.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "package")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private int price;
    @Column
    private String period;
    @Column
    private String extraDetails;
    @Column
    private int noOfPeople;
    @Column
    private String status;

    @ManyToMany(mappedBy = "packages")
    private List<User> users;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;

    public Package(String name, int price, String period, String extraDetails, int noOfPeople, String status, List<User> users, Destination destination) {
        this.name = name;
        this.price = price;
        this.period = period;
        this.extraDetails = extraDetails;
        this.noOfPeople = noOfPeople;
        this.status = status;
        this.users = users;
        this.destination = destination;
    }
    public Package(String name, int price, String period, String extraDetails, int noOfPeople, String status, Destination destination) {
        this.name = name;
        this.price = price;
        this.period = period;
        this.extraDetails = extraDetails;
        this.noOfPeople = noOfPeople;
        this.status = status;
        this.destination = destination;
    }
    public Package(String name, int price, String period, String extraDetails, int noOfPeople, Destination destination) {
        this.name = name;
        this.price = price;
        this.period = period;
        this.extraDetails = extraDetails;
        this.noOfPeople = noOfPeople;
        this.status = "NOT_BOOKED";
        this.destination = destination;
    }
    public Package(){

    }

    public Package(int id, String name, int price, String period, String extraDetails, int noOfPeople, String status, List<User> users, Destination destination) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.period = period;
        this.extraDetails = extraDetails;
        this.noOfPeople = noOfPeople;
        this.status = status;
        this.users = users;
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getExtraDetails() {
        return extraDetails;
    }

    public void setExtraDetails(String extraDetails) {
        this.extraDetails = extraDetails;
    }

    public int getNoOfPeople() {
        return noOfPeople;
    }

    public void setNoOfPeople(int noOfPeople) {
        this.noOfPeople = noOfPeople;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getUser() {
        return users;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
