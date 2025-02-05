package com.flurenco.dto;

import com.flurenco.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {

	private long phoneNo;
	private String userName;
	private String userEmail;
	private Role role;
}
