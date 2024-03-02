package com.cognizant.drugs.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "drugs")
public class DrugDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer drugId;
	@Column(name = "name")
	private String name;
	@Column(name = "manufacturer")
	private String manufacturer;
	@Column(name = "manufactured_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate manufacturedDate;
	@Column(name = "expiry_date")
	private LocalDate expiryDate;
	@Column(name = "quantity")
	private int quantity;
	@OneToMany(mappedBy = "drugDetails", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DrugLocationDetails> drugLocationDetails;
}
