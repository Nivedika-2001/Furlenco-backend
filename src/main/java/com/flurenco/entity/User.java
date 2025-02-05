package com.flurenco.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Users1")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

	@Id
	@Column(nullable = false,length=10)
	private long phoneNo;
	
	@Column(nullable = false)
	private String userName;
	
	@Column(nullable = false)
	private String userEmail;
	
    @Enumerated(EnumType.STRING)
    private Role role;
}