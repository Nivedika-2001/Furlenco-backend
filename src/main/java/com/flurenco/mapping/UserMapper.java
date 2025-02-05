package com.flurenco.mapping;

import org.springframework.stereotype.Component;
import com.flurenco.dto.UserDTO;
import com.flurenco.entity.User;


@Component
public class UserMapper {

	/****
	 * 
	 * @param user
	 * @return
	 * function which converts user to userdto
	 */
	public UserDTO toUserDTO(User user)
	{
		UserDTO userDTO= new UserDTO();
		userDTO.setPhoneNo(user.getPhoneNo());
		userDTO.setUserName(user.getUserName());
		userDTO.setUserEmail(user.getUserEmail());
		userDTO.setRole(user.getRole());
		return userDTO;
	}
	/****
	 * 
	 * @param userDTO
	 * @return
	 * function which converts userdto to user
	 */
	public User toUserEntity(UserDTO userDTO) {
		User user=new User();
		user.setPhoneNo(userDTO.getPhoneNo());
		user.setUserName(userDTO.getUserName());
		user.setUserEmail(userDTO.getUserEmail());
		user.setRole(userDTO.getRole());
		return user;
	}
}