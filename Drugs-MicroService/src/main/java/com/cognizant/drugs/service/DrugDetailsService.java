package com.cognizant.drugs.service;

import java.util.List;
import java.util.Optional;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 

import com.cognizant.drugs.model.DrugDetails;
import com.cognizant.drugs.model.DrugLocationDetails;
import com.cognizant.drugs.repository.DrugDetailsRepository;
import com.cognizant.drugs.repository.DrugLocationDetailsRepository;

 

@Service
public class DrugDetailsService {
    @Autowired
    private DrugDetailsRepository drugDetailsRepository;

 

    @Autowired
    private DrugLocationDetailsRepository drugLocationDetailsRepository;

 

    public DrugDetails getDrugDetailsById(int drugId) {
        Optional<DrugDetails> findById = drugDetailsRepository.findById(drugId);
        return findById.get();
    }
    
    public List<DrugDetails> getDrugDetails() {
        return drugDetailsRepository.findAll(); 
        
    }

 

    public DrugDetails getDrugDetailsByName(String drugName) {
        return drugDetailsRepository.findByName(drugName);
    }

 

    public DrugLocationDetails searchByLocation(Integer drugId, String Location) {
        return drugLocationDetailsRepository.findByDrugDetailsDrugIdAndLocation(drugId, Location);
    }
}