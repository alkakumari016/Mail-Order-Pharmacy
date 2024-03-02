package com.mailorderpharma.subscription.restclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mailorderpharma.subscription.entity.DrugDetails;

/**Interface to connect with authentication service*/
@FeignClient(url="http://localhost:8081/drugdetailapi",name="drugdetailapp")
public interface DrugDetailClient {

	@GetMapping("/searchDrugsByName/{name}")
	public DrugDetails getDrugByName(@RequestHeader("Authorization") final String token, @PathVariable("name") String name);
}
