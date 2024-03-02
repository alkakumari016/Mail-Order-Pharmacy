package com.cognizant.drugs.DrugsMicroServices.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.cognizant.drugs.model.DrugDetails;

class DrugDetailsTest {
DrugDetails drugDetails = new DrugDetails();
	@Test
	void test() {
		drugDetails.setName("Rehan");
		assertEquals("Rehan", drugDetails.getName());
		//DrugDetails d2=new DrugDetails(2, "zave",new Dat, null, null, 0, null)
	}

}
