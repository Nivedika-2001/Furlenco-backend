package com.flurenco.serviceInterface;

import com.flurenco.dto.UserDTO;
import com.flurenco.entity.Role;
import com.flurenco.exception.DuplicateRecordException;
import com.flurenco.exception.UserNotFound;
import com.flurenco.exception.UserRoleNotFoundException;

public interface UserInterface {

	public UserDTO saveRecord(UserDTO userDTO) throws DuplicateRecordException;
	public boolean fetchRecord(long phoneNo);
	public String getByName(long phoneNo) throws UserNotFound;
	public Role getRoleByPhoneNo(long phoneNo) throws UserNotFound,UserRoleNotFoundException ;
}
