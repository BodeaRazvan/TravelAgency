package com.example.travelagency.repository;

import com.example.travelagency.entity.Destination;
import com.example.travelagency.service.DestinationService;

import java.util.List;

public class DestinationRepository {
    private final DestinationService destinationService;

    public DestinationRepository(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    public void addDestination(Destination destination){
        destinationService.addDestination(destination);
    }
    public List<Destination> getAllDestinations(){return destinationService.getAllDestinations();}
    public Destination getDestinationByCountry(String country){return destinationService.getDestinationByCountry(country);}
    public void removeDestination(Destination destination){destinationService.deleteDestination(destination);}
}
