package com.cognizant.drugs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.drugs.feign.AuthorizationClient;
import com.cognizant.drugs.model.DrugDetails;
import com.cognizant.drugs.model.DrugLocationDetails;
import com.cognizant.drugs.model.FormInputGetByLocation;
import com.cognizant.drugs.service.DrugDetailsService;


@RestController
public class DrugController {
	@Autowired
	private DrugDetailsService drugDetailsService;

	@Autowired
	private AuthorizationClient client;

	@GetMapping(value = "/searchDrugsByID/{drugId}")
	public DrugDetails getDrugDetailsById(@PathVariable(name = "drugId") Integer drugId,
			@RequestHeader(value = "Authorization", required = false) String requestTokenHeader) throws Exception {
		/*
		 * if (!client.authorizeTheRequest(requestTokenHeader)) {
		 * System.out.println("From AUthorized Request"); throw new
		 * Exception("Not allowed"); }
		 */
		DrugDetails drugDetails = drugDetailsService.getDrugDetailsById(drugId);
		return drugDetails;
	}

	@GetMapping(value = "/getAllDrugs")
	public List<DrugDetails> getAllDrugs() {
		return drugDetailsService.getDrugDetails();
	}

	@GetMapping(value = "/searchDrugsByName/{drugName}")
	public DrugDetails getDrugDetailsByName(@RequestHeader(value = "Authorization", required=false) String requestTokenHeader,@PathVariable(name = "drugName") String drugName) throws Exception {
		/*
		 * if (!client.authorizeTheRequest(requestTokenHeader)) {
		 * System.out.println("From AUthorized Request"); throw new
		 * Exception("Not allowed"); }
		 */
		System.out.println("inside drug service");
		System.out.println(drugDetailsService.getDrugDetailsByName(drugName));
		DrugDetails drugDetails = drugDetailsService.getDrugDetailsByName(drugName);
		return drugDetails;
	}

	@PostMapping(value = "/getDispatchableDrugStock")
	public DrugLocationDetails retriveStockBasedOnLocation(@RequestBody FormInputGetByLocation formInputGetByLocation,
			@RequestHeader(value = "Authorization", required = false) String requestTokenHeader) throws Exception {
		/*
		 * if (!client.authorizeTheRequest(requestTokenHeader)) {
		 * System.out.println("From AUthorized Request"); throw new
		 * Exception("Not allowed"); }
		 */
		return drugDetailsService.searchByLocation(formInputGetByLocation.getDrugId(),
				formInputGetByLocation.getLocation());
	}

}
