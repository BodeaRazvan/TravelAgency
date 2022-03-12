package com.example.travelagency.repository;

import com.example.travelagency.entity.Package;
import com.example.travelagency.service.PackageService;

import java.util.List;

public class PackageRepository {
    private final PackageService packageService;

    public PackageRepository(PackageService packageService) {
        this.packageService = packageService;
    }

    public void addPackage(Package pkg){
        packageService.addPackage(pkg);
    }
    public Package getPackageById(int id){return packageService.getPackageById(id);}
    public List<Package> getAllPackages(){return packageService.getAllPackages();}
    public void modifyPackage(Package pkg){packageService.modifyPackage(pkg);}
    public void removePackage(Package pkg){packageService.removePackage(pkg);}
}
