package com.mailorderpharma.subscription;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**Main class which contains the main function which 
 * triggers the whole application 
 */
@EnableFeignClients
@SpringBootApplication
public class SubscriptionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubscriptionServiceApplication.class, args);
	}

}
