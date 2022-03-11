package com.example.travelagency.repository;

import com.example.travelagency.entity.Destination;
import com.example.travelagency.service.DestinationService;

public class DestinationRepository {
    private final DestinationService destinationService;

    public DestinationRepository(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    public void addDestination(Destination destination){
        destinationService.addDestination(destination);
    }

}
