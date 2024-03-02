package com.mailorderpharma.subscription.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mailorderpharma.subscription.Repository.PrescriptionRepository;
import com.mailorderpharma.subscription.Repository.SubscriptionRepository;
import com.mailorderpharma.subscription.entity.DrugDetails;
import com.mailorderpharma.subscription.entity.DrugLocationDetails;
import com.mailorderpharma.subscription.entity.PrescriptionDetails;
import com.mailorderpharma.subscription.entity.SubscriptionDetails;
import com.mailorderpharma.subscription.entity.TokenValid;
import com.mailorderpharma.subscription.exceptions.InvalidTokenException;
import com.mailorderpharma.subscription.exceptions.SubscriptionListEmptyException;
import com.mailorderpharma.subscription.restclients.AuthFeign;
import com.mailorderpharma.subscription.restclients.DrugDetailClient;
import com.mailorderpharma.subscription.restclients.RefillClient;
import com.mailorderpharma.subscription.services.SubscriptionServiceImplementation;

@SpringBootTest(classes = SubscriptionServiceImplementaionTest.class)
 class SubscriptionServiceImplementaionTest {

	@InjectMocks
	SubscriptionServiceImplementation subscriptionServiceImplementation;

	@Mock
	private DrugDetailClient drugDetailClient;
	@Mock
	private RefillClient refillClient;
	@Mock
	private AuthFeign authFeign;
	@Mock
	PrescriptionRepository prescriptionRepo;
	@Mock
	SubscriptionRepository subscriptionRepo;

	@Test
	 void subscribe() {
		LocalDate date = LocalDate.now();
		PrescriptionDetails prescriptionDetails = new PrescriptionDetails((long) 45, "member", "salem", "member", "member",
				date, "member", "member", 45, 45, "member");
		SubscriptionDetails subscriptionDetails = new SubscriptionDetails(null,(long)45,"member",45,45,"member",date,"salem","active");
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		List<DrugLocationDetails> list = new ArrayList<DrugLocationDetails>();
		//DrugLocationDetails drugLocationDetails = new DrugLocationDetails("45","salem",45,null);

		DrugLocationDetails drugLocationDetails = new DrugLocationDetails("D1","Chennai",30, null);

		list.add(drugLocationDetails);
		//DrugDetails drugDetails = new DrugDetails("drug","drug","drug",new Date(),new Date(),list);

		DrugDetails drugDetails = new DrugDetails("D1", "Drug1", "manu1", new Date(), new Date(), list);

		when(drugDetailClient.getDrugByName("token","Drug1")).thenReturn(drugDetails);
		when(prescriptionRepo.save(any(PrescriptionDetails.class))).thenReturn(prescriptionDetails);
		when(subscriptionRepo.save(any(SubscriptionDetails.class)))
				.thenReturn(subscriptionDetails);

		assertEquals(new ResponseEntity<>("You have succesfully subscribed to " + prescriptionDetails.getDrugName(),
				HttpStatus.OK), subscriptionServiceImplementation.subscribe(prescriptionDetails, "token"));

	}
 
	@Test
	 void subscribefalse() {
		LocalDate date = LocalDate.now();

		PrescriptionDetails prescriptionDetails = new PrescriptionDetails((long) 45, "member", "salem", "45", "45",
				date, "member", "member", 45, 45, "member");

		TokenValid tokenValid = new TokenValid("uid", "uname", false);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);	
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class,
				() -> subscriptionServiceImplementation.subscribe(prescriptionDetails, "token"));
	}

	@Test
	 void unsubscribe() {
		LocalDate date = LocalDate.now();
		PrescriptionDetails prescriptionDetails = new PrescriptionDetails((long) 45, "member", "salem", "45", "45",
				date, "member", "member", 45, 45, "member");
		SubscriptionDetails subscriptionDetails = new SubscriptionDetails((long) 45, 45, 45, "member", date, "salem",
				"member", "member");
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);	
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillClient.isPendingPaymentDues("token", (long) 45)).thenReturn(true);
		subscriptionServiceImplementation.unsubscribe("asd", (long) 45, "token");

		boolean actual = refillClient.isPendingPaymentDues("token", (long) 45);

		assertEquals(true, actual);

	}

	@Test
	 void unsubscribefalse() {
		LocalDate date = LocalDate.now();
		PrescriptionDetails prescriptionDetails = new PrescriptionDetails((long) 45, "member", "salem", "45", "45",
				date, "member", "member", 45, 45, "member");
		SubscriptionDetails subscriptionDetails = new SubscriptionDetails((long) 45, 45, 45, "member", date, "salem",
				"member", "member");
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);	
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillClient.isPendingPaymentDues("token", (long) 45)).thenReturn(false);
		subscriptionServiceImplementation.unsubscribe("asd", (long) 45, "token");

		boolean actual = refillClient.isPendingPaymentDues("token", (long) 45);

		assertEquals(false, actual);

	}

	@Test
	 void getStatusInvalidToken() {
		TokenValid tokenValid = new TokenValid("uid", "uname", false);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);	
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class,
				() -> subscriptionServiceImplementation.unsubscribe("asd", (long) 45, "token"));
	}

	@Test
	 void getAllSubscriptionsTest() {
		LocalDate date = LocalDate.now();
		List<SubscriptionDetails> list = new ArrayList<SubscriptionDetails>();
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);	
		when(authFeign.getValidity("token")).thenReturn(response);
		when(subscriptionRepo.findByMemberId("mem")).thenReturn(list);
		assertThrows(SubscriptionListEmptyException.class,
				() -> subscriptionServiceImplementation.getAllSubscriptions("mem", "token"));

	}

	@Test
	 void getAllSubscriptionsTestsucess() {
		LocalDate date = LocalDate.now();
		SubscriptionDetails subscriptionDetails = new SubscriptionDetails((long) 45, 45, 45, "member", date, "salem",
				"member", "member");

		List<SubscriptionDetails> list = new ArrayList<SubscriptionDetails>();
		list.add(subscriptionDetails);
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);		when(authFeign.getValidity("token")).thenReturn(response);
		when(subscriptionRepo.findByMemberId("mem")).thenReturn(list);
		subscriptionServiceImplementation.getAllSubscriptions("mem", "token");

		List<SubscriptionDetails> actual = subscriptionRepo.findByMemberId("mem");

		assertEquals(list, actual);

	}

	@Test
	 void getAllSubscriptionsTestFalse() {
		TokenValid tokenValid = new TokenValid("uid", "uname", false);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);	
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class,
				() -> subscriptionServiceImplementation.getAllSubscriptions("asd", "token"));
	}

	@Test
	 void getDrugNameBySubscriptionIdTest() {
		LocalDate date = LocalDate.now();
		PrescriptionDetails prescriptionDetails = new PrescriptionDetails((long) 45, "member", "salem", "45", "45",
				date, "member", "member", 45, 45, "member");
		SubscriptionDetails subscriptionDetails = new SubscriptionDetails((long) 45, 45, 45, "member", date, "salem",
				"member", "member");
		List<SubscriptionDetails> list = new ArrayList<SubscriptionDetails>();
		list.add(subscriptionDetails);
		TokenValid tokenValid = new TokenValid("uid", "uname", true);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
	
		 when(subscriptionRepo.findById((long) 45)).thenReturn(Optional.of(subscriptionDetails));
		subscriptionServiceImplementation.getDrugNameBySubscriptionId((long) 45, "token");

		SubscriptionDetails actual= subscriptionRepo.findById((long) 45).orElse(new SubscriptionDetails());

		SubscriptionDetails expected = list.get(0);

		assertEquals(expected, actual);

	}

	@Test
	 void getDrugNameBySubscriptionIdTestFalse() {
		TokenValid tokenValid = new TokenValid("uid", "uname", false);
		ResponseEntity<TokenValid> response = new ResponseEntity<TokenValid>(tokenValid, HttpStatus.OK);	
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class,
				() -> subscriptionServiceImplementation.getDrugNameBySubscriptionId((long) 45, "token"));
	}

}
