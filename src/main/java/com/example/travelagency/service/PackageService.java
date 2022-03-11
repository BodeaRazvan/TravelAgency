package com.example.travelagency.service;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PackageService {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");
    EntityManager em = entityManagerFactory.createEntityManager();

    public void addPackage(Package pkg){
        em.getTransaction().begin();
        em.persist(pkg);
        em.getTransaction().commit();
        em.close();
    }
}
