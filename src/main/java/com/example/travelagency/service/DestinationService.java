package com.example.travelagency.service;

import com.example.travelagency.entity.Destination;
import com.example.travelagency.repository.DestinationRepository;

import java.util.List;

public class DestinationService {
    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public void addDestination(Destination destination){
        destinationRepository.addDestination(destination);
    }
    public List<Destination> getAllDestinations(){return destinationRepository.getAllDestinations();}
    public Destination getDestinationByCountry(String country){return destinationRepository.getDestinationByCountry(country);}
    public void removeDestination(Destination destination){
        destinationRepository.deleteDestination(destination);}
}
