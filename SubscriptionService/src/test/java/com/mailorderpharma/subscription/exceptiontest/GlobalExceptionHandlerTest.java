package com.mailorderpharma.subscription.exceptiontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.naming.ServiceUnavailableException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.mailorderpharma.subscription.exceptions.GlobalExceptionHandler;
import com.mailorderpharma.subscription.exceptions.InvalidTokenException;
import com.mailorderpharma.subscription.exceptions.SubscriptionListEmptyException;

@SpringBootTest
 class GlobalExceptionHandlerTest {

	
	@InjectMocks
	GlobalExceptionHandler globalExceptionHandler;
	
	@Test
	 void invalidTokenException()
	{
		assertEquals(HttpStatus.UNAUTHORIZED, globalExceptionHandler.invalidTokenException
				(new InvalidTokenException("invalidTokenException")).getStatusCode());
	}
	
	@Test
	 void subscriptionListEmptyException()
	{
		assertEquals(HttpStatus.NOT_FOUND, globalExceptionHandler.subscriptionListEmptyException
				(new SubscriptionListEmptyException("SubscriptionListEmptyException")).getStatusCode());
	}
	
	@Test
	 void serviceUnavailableException()
	{
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, globalExceptionHandler.serviceUnavailableException
				().getStatus());
	}
}
