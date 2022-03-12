package com.example.travelagency.service;

import com.example.travelagency.entity.Package;
import com.example.travelagency.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserService {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");
    EntityManager em = entityManagerFactory.createEntityManager();

    public void addUser(User user){
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public void modifyUser(User user){
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        em.close();
    }


    public User findUser(String username){
        return (User) em.createQuery("select u from User u where u.userName =:value1").setParameter("value1",username).getSingleResult();
    }

}
