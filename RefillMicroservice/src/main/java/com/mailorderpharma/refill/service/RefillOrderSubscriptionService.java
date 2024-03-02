package com.mailorderpharma.refill.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mailorderpharma.refill.entity.RefillOrderSubscription;
import com.mailorderpharma.refill.exception.InvalidTokenException;

/** Interface which have the methods of service class */
@Service
public interface RefillOrderSubscriptionService {

	/**
	 * @param subId
	 * @param memberId
	 * @param quantity
	 * @param time
	 * @param token 
	 * @return
	 * @throws InvalidTokenException
	 */
	public RefillOrderSubscription updateRefillOrderSubscription(long subId, String memberId, int quantity, int time, String token) throws InvalidTokenException;

	/**
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 */
	public List<RefillOrderSubscription> getall(String token) throws InvalidTokenException;

	/**
	 * @param subscriptionId
	 * @param token
	 * @throws InvalidTokenException
	 */
	public void deleteBySubscriptionId(long subscriptionId, String token) throws InvalidTokenException;

}
