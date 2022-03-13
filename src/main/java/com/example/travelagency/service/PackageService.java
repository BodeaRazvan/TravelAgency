package com.example.travelagency.service;

import com.example.travelagency.entity.Package;
import com.example.travelagency.repository.PackageRepository;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PackageService {
    private final PackageRepository packageRepository;

    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public void addPackage(Package pkg){
        packageRepository.addPackage(pkg);
    }
    public Package getPackageById(int id){return packageRepository.getPackageById(id);}
    public List<Package> getAllPackages(){return packageRepository.getAllPackages();}
    public void modifyPackage(Package pkg){
        packageRepository.modifyPackage(pkg);}
    public void removePackage(Package pkg){
        packageRepository.removePackage(pkg);}
    public List<Package> getAllNotBookedPackages(){return packageRepository.getAllNotBookedPackages();}

    public List<Package> filterPackages(List<Package> packages, String destination, String name, int price, Date period, String status,
                                        int noOfPeople){
        try{
            return packages.stream()
                    .filter(pkg -> destination.equals("") || pkg.getDestination().getCountry().contains(destination))
                    .filter(pkg -> name.equals("") || pkg.getName().contains(name))
                    .filter(pkg -> price==-1 || pkg.getPrice()<=price)
                    .filter(pkg -> noOfPeople==-1 || pkg.getNoOfPeople()>=noOfPeople)
                    .filter(pkg -> status.equals("") || pkg.getStatus().equals(status))
                    .filter(pkg -> period == null || pkg.getPeriod().toLocalDate().isAfter(period.toLocalDate()))
                    .collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
