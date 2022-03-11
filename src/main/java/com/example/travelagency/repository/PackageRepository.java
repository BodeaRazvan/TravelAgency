package com.example.travelagency.repository;

import com.example.travelagency.service.PackageService;

public class PackageRepository {
    private final PackageService packageService;

    public PackageRepository(PackageService packageService) {
        this.packageService = packageService;
    }

    public void addPackage(Package pkg){
        packageService.addPackage(pkg);
    }
}
