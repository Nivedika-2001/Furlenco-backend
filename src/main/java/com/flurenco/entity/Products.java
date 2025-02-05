package com.flurenco.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productID;
	
	@Column(nullable = false)
	private String productName;
	
	@Column(nullable = false)
	private String productType;
	
	@Column(nullable = false)
	private String productPrice;
	
	@Column(nullable = false)
	private int availableStock;
	
	@Column(nullable = false)
	private String productURL;
}
