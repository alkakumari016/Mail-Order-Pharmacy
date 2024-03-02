package com.mailorderpharma.subscription.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mailorderpharma.subscription.entity.PrescriptionDetails;
import com.mailorderpharma.subscription.entity.SubscriptionDetails;
import com.mailorderpharma.subscription.exceptions.InvalidTokenException;

/** Interface which have the methods of service class */
public interface SubscriptionService {
/**
 * @param prescriptionDetails
 * @param token
 * @return
 * @throws InvalidTokenException
 */
    ResponseEntity<String> subscribe(PrescriptionDetails prescriptionDetails,String token) throws InvalidTokenException;
/**
 * @param mId
 * @param sId
 * @param token
 * @return
 * @throws InvalidTokenException
 */
    ResponseEntity<String> unsubscribe(String mId,Long sId,String token) throws InvalidTokenException;
/**
 * @param mId
 * @param token
 * @return
 * @throws InvalidTokenException
 */
    List<SubscriptionDetails> getAllSubscriptions(String mId,String token) throws InvalidTokenException;
/**
 * @param sId
 * @param token
 * @return
 * @throws InvalidTokenException
 */
    ResponseEntity<String> getDrugNameBySubscriptionId(Long sId, String token) throws InvalidTokenException;
    
}
