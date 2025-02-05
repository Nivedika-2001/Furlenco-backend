package com.flurenco.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorStatus {

	private String message;
    private int code;
    private String details;
	
	

    
}

