package com.cognizant.drugs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.drugs.model.DrugDetails;

@Repository
public interface DrugDetailsRepository extends JpaRepository<DrugDetails, Integer> {
	public DrugDetails findByName(String name);

	// public DrugDetails findByDrugIdAndDrugLocationDetails(Integer drugId, String
	// Location);
	// public DrugDetails findByDrugAndLocationDetails(Integer drugId, String
	// Location);
}
