package com.mailorderpharma.subscription.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.OneToMany;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**Model class for the business details*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrugDetails {

	@Id
	private String drugId;
	/**
	 * Name of the drug
	 */
	private String drugName;
	/**
	 * name of the manufacturer
	 */
	private String manufacturer;
	/**
	 * Manufacture date
	 */
	private Date manufactureDate;
	/**
	 * Expiry date
	 */
	private Date expiryDate;

	/**
	 * List of drug locations and quantities in location
	 */
	@OneToMany(mappedBy="drugDetails")
	private List<DrugLocationDetails> druglocationQuantities = new ArrayList<>();

}

