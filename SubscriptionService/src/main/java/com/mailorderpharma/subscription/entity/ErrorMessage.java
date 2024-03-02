package com.mailorderpharma.subscription.entity;


import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**Model class for the business details*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage 
{
	private HttpStatus status;
	private LocalDateTime timestamp;
	private String message;
}