package com.example.travelagency.service;

import com.example.travelagency.entity.Destination;
import com.example.travelagency.entity.Package;
import com.example.travelagency.entity.User;
import sun.security.krb5.internal.crypto.Des;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class DestinationService {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");
    EntityManager em = entityManagerFactory.createEntityManager();

    public void addDestination(Destination destination){
        em.getTransaction().begin();
        em.persist(destination);
        em.getTransaction().commit();
        em.close();
    }
    public void deleteDestination(Destination destination){
        Destination neDest = em.find(Destination.class,destination.getId());
        em.getTransaction().begin();
        em.remove(neDest);
        em.getTransaction().commit();
        em.close();
    }

    public List<Destination> getAllDestinations(){
        return em.createQuery("select d from Destination d",Destination.class).getResultList();
    }

    public Destination getDestinationByCountry(String country){
        return (Destination) em.createQuery("select d from Destination d where d.country =:value1").setParameter("value1",country).getSingleResult();
    }

}
