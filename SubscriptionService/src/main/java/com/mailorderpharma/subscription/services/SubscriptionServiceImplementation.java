package com.mailorderpharma.subscription.services;

import java.time.LocalDate;
import java.util.List;

import com.mailorderpharma.subscription.entity.TokenValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mailorderpharma.subscription.Repository.PrescriptionRepository;
import com.mailorderpharma.subscription.Repository.SubscriptionRepository;
import com.mailorderpharma.subscription.entity.PrescriptionDetails;
import com.mailorderpharma.subscription.entity.SubscriptionDetails;
import com.mailorderpharma.subscription.exceptions.InvalidTokenException;
import com.mailorderpharma.subscription.exceptions.SubscriptionListEmptyException;
import com.mailorderpharma.subscription.restclients.AuthFeign;
import com.mailorderpharma.subscription.restclients.DrugDetailClient;
import com.mailorderpharma.subscription.restclients.RefillClient;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/** Service class which holds the business logic */
@Slf4j
@Service
public class SubscriptionServiceImplementation implements SubscriptionService {

	@Autowired
	private DrugDetailClient drugDetailClient;
	@Autowired
	private RefillClient refillClient;

	@Autowired
	private AuthFeign authFeign;

	@Autowired
	PrescriptionRepository prescriptionRepo;
	@Autowired
	SubscriptionRepository subscriptionRepo;


	String msg = "Invalid Credentials";

	/**
	 * @param prescriptionDetails
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 */
	@Override
	public ResponseEntity<String> subscribe(PrescriptionDetails prescriptionDetail, String token)
			throws InvalidTokenException, FeignException {
		log.info("Inside subscribe service method");
		if(authFeign.getValidity(token).getBody() !=null) {
			TokenValid tokenValid = authFeign.getValidity(token).getBody();
			if (tokenValid != null && tokenValid.isValid()) {
				log.info("subscribe service method- token is valid");
				drugDetailClient.getDrugByName(token, prescriptionDetail.getDrugName());
				PrescriptionDetails prescriptionDetails = prescriptionRepo.save(prescriptionDetail);
				log.info("prescription saved");
				SubscriptionDetails subscriptionDetail = new SubscriptionDetails(prescriptionDetails.getPrescriptionId(),
						prescriptionDetails.getCourseDuration(), prescriptionDetails.getQuantity(),
						prescriptionDetails.getMemberId(), LocalDate.now(), prescriptionDetails.getMemberLocation(),
						"active", prescriptionDetails.getDrugName());

				log.info("subs obj created");

				SubscriptionDetails subscriptionDetails = subscriptionRepo.save(subscriptionDetail);
				// inform refill service to start refilling for this subscription
				refillClient.requestRefillSubscription(token, subscriptionDetails.getSubscriptionId(),
						subscriptionDetails.getMemberId(), subscriptionDetails.getQuantity(),
						subscriptionDetails.getRefillCycle());

				log.info("subs obj saved");
			} else
				throw new InvalidTokenException(msg);
		}

		return new ResponseEntity<>("You have succesfully subscribed to " + prescriptionDetail.getDrugName(),
				HttpStatus.OK);
	}

	/**
	 * @param mId
	 * @param sId
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 */
	@Override
	public ResponseEntity<String> unsubscribe(String mId, Long sId, String token)
			throws InvalidTokenException, FeignException {

		if(authFeign.getValidity(token).getBody() !=null) {
			TokenValid tokenValid = authFeign.getValidity(token).getBody();
			if (tokenValid != null && tokenValid.isValid()) {
				if (!refillClient.isPendingPaymentDues(token, sId)) {
					log.info("payment not clear");
					return new ResponseEntity<>("You have to clear your payment dues first.", HttpStatus.OK);
				}
				subscriptionRepo.deleteById(sId);
				log.info("dleted ");
				// inform refill service to stop refilling for this sId
				refillClient.deleteRefillData(token, sId);
				log.info("delete refill success");
			} else
				throw new InvalidTokenException(msg);
		}

		return new ResponseEntity<>("You have succesfully Unsubscribed", HttpStatus.OK);
	}

	/**
	 * @param mId
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 */
	@Override
	public List<SubscriptionDetails> getAllSubscriptions(String mId, String token)
			throws InvalidTokenException, SubscriptionListEmptyException {
		// gets a list  of subscriptions for a given member id
		log.info("get subscription for ");

			if (authFeign.getValidity(token).getBody().isValid()) {
				if (subscriptionRepo.findByMemberId(mId).isEmpty())
					throw new SubscriptionListEmptyException("Currently you do not have any subscriptions");
				return subscriptionRepo.findByMemberId(mId);
			} else
				throw new InvalidTokenException(msg);


	}

	/**
	 * @param sId
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 */
	@Override
	public ResponseEntity<String> getDrugNameBySubscriptionId(Long sId, String token) throws InvalidTokenException {
		// extracts Drug name for given subscription Id
		log.info("getDrugNameBySubscriptionId");
		String drugName = null;
		if(authFeign.getValidity(token).getBody() !=null) {
			TokenValid tokenValid = authFeign.getValidity(token).getBody();
			if (tokenValid != null && tokenValid.isValid()) {
				drugName = subscriptionRepo.findById(sId)
						.orElseThrow(() -> new SubscriptionListEmptyException("DrugNotFound")).getDrugName();
			}
			else
				throw new InvalidTokenException(msg);
		}

		return new ResponseEntity<>(drugName, HttpStatus.OK);
	}
	
	
}
