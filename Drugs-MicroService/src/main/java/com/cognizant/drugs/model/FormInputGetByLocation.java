package com.cognizant.drugs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormInputGetByLocation {
	private Integer drugId;
	private String location;
}
