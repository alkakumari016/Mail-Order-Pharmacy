package com.mailorderpharma.refill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mailorderpharma.refill.dao.RefillOrderSubscriptionRepository;
import com.mailorderpharma.refill.entity.RefillOrderSubscription;
import com.mailorderpharma.refill.exception.InvalidTokenException;
import com.mailorderpharma.refill.restclients.AuthFeign;

import lombok.extern.slf4j.Slf4j;

/**Service class which holds the business logic*/
@Service
@Slf4j
public class RefillOrderSubscriptionServiceImpl implements RefillOrderSubscriptionService {

	@Autowired
	RefillOrderSubscriptionRepository refillOrderSubscriptionRepository;

	@Autowired
	private AuthFeign authFeign;

	String msg = "Invalid Credentials";

	/**
	 * @param subId
	 * @param memberId
	 * @param quantity
	 * @param time
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 */
	
	@Override
	public RefillOrderSubscription updateRefillOrderSubscription(long subId, String memberId, int quantity, int time,
			String token) throws InvalidTokenException {
		// start a refill for new subscriptions
		log.info("inside UpdateRefillOrderSubscription method");

		if (authFeign.getValidity(token).isValid()) {
			RefillOrderSubscription refillOrderSubscription = new RefillOrderSubscription();
			refillOrderSubscription.setSubscriptionId(subId);
			refillOrderSubscription.setRefillTime(time);
			refillOrderSubscription.setRefillQuantity(quantity);
			refillOrderSubscription.setMemberId(memberId);
			refillOrderSubscriptionRepository.save(refillOrderSubscription);

			return refillOrderSubscription;

		} else
			throw new InvalidTokenException(msg);
	}

	/**
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 */
	@Override
	public List<RefillOrderSubscription> getall(String token) throws InvalidTokenException {
		//get all refill order subscriptions
		log.info("inside getall method");

		if (authFeign.getValidity(token).isValid()) {
			return refillOrderSubscriptionRepository.findAll();
		} else
			throw new InvalidTokenException(msg);
	}

	/**
	 * @param subscriptionId
	 * @param token
	 * @throws InvalidTokenException
	 */
	@Override
	public void deleteBySubscriptionId(long subscriptionId, String token) throws InvalidTokenException {
		// stop refill orders after delete subscription
		log.info("inside deleteBySubscriptionId method");

		if (authFeign.getValidity(token).isValid()) {
			refillOrderSubscriptionRepository.deleteBySubscriptionId(subscriptionId);
		} else
			throw new InvalidTokenException(msg);
	}

}
