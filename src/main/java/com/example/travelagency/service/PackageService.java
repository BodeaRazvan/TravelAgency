package com.example.travelagency.service;


import com.example.travelagency.entity.Destination;
import com.example.travelagency.entity.Package;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.Collectors;

public class PackageService {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");
    EntityManager em = entityManagerFactory.createEntityManager();

    public void addPackage(Package pkg){
        em.getTransaction().begin();
        em.persist(pkg);
        em.getTransaction().commit();
        em.close();
    }

    public void modifyPackage(Package pkg){
        em.getTransaction().begin();
        em.merge(pkg);
        em.getTransaction().commit();
        em.close();
    }

    public void removePackage(Package pkg){
        Package newPkg = em.find(Package.class,pkg.getId());
        em.getTransaction().begin();
        em.remove(newPkg);
        em.getTransaction().commit();
        em.close();
    }

    public List<Package> getAllPackages(){
        return em.createQuery("select p from Package p", Package.class).getResultList();
    }

    public List<Package> getAllNotBookedPackages(){
        return em.createQuery("select p from Package p where p.status =:value1 or p.status =:value2 ", Package.class).
                setParameter("value1","NOT_BOOKED").setParameter("value2","IN_PROGRESS").getResultList();
    }

    public Package getPackageById(int id){
        return (Package) em.createQuery("select p from Package p where p.id =:value1").setParameter("value1",id).getSingleResult();
    }

    public List<Package> filterPackages(List<Package> packages, String destination, String name, int price, String period,String status, int noOfPeople){
        try{
            return packages.stream()
                    .filter(pkg -> destination.equals("") || pkg.getDestination().getCountry().contains(destination))
                    .filter(pkg -> name.equals("") || pkg.getName().contains(name))
                    .filter(pkg -> price==-1 || pkg.getPrice()<=price)
                    .filter(pkg -> noOfPeople==-1 || pkg.getNoOfPeople()>=noOfPeople)
                    .filter(pkg -> status.equals("") || pkg.getStatus().equals(status))
                    .filter(pkg -> period.equals("") || pkg.getPeriod().contains(period))
                    .collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
