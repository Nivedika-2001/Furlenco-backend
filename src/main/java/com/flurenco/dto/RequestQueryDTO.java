package com.flurenco.dto;


import com.flurenco.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestQueryDTO {

	private int queryID;
	
	private String serviceType;
	
	private String issueType;
	
	private String subject;
	
	private String description;
	
	private User user;
}
