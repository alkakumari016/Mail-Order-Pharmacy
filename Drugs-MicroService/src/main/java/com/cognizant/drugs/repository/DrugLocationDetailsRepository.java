package com.cognizant.drugs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.drugs.model.DrugLocationDetails;

@Repository
public interface DrugLocationDetailsRepository extends JpaRepository<DrugLocationDetails, Integer> {
	public DrugLocationDetails findByDrugDetailsDrugIdAndLocation(Integer drugId, String location);
}
