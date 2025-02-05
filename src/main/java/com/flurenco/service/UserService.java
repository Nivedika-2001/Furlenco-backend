package com.flurenco.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flurenco.dto.UserDTO;
import com.flurenco.entity.Role;
import com.flurenco.entity.User;
import com.flurenco.exception.DuplicateRecordException;
import com.flurenco.exception.UserNotFound;
import com.flurenco.exception.UserRoleNotFoundException;
import com.flurenco.mapping.UserMapper;
import com.flurenco.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Function to save the user record.
     * 
     * @param userDTO The user details to be saved.
     * @return The saved UserDTO object.
     * @throws DuplicateRecordException If a user with the same phone number already exists.
     */
    public UserDTO saveRecord(UserDTO userDTO) throws DuplicateRecordException {
        logger.info("Saving user record for phone number: {}", userDTO.getPhoneNo());
        if (userRepository.existsById(userDTO.getPhoneNo())) {
            logger.error("User with phone number {} already exists", userDTO.getPhoneNo());
            throw new DuplicateRecordException("User with phone number " + userDTO.getPhoneNo() + " already exists");
        }
        userDTO.setRole(Role.USER);
        User savedUser = userRepository.save(userMapper.toUserEntity(userDTO));
        return userMapper.toUserDTO(savedUser);
    }

    /**
     * Function to check whether the visitor has logged in first time or already a user.
     * 
     * @param phoneNo The phone number to check.
     * @return True if the user exists, false otherwise.
     */
    public boolean fetchRecord(long phoneNo) {
        logger.info("Checking user record for phone number: {}", phoneNo);
        return userRepository.existsById(phoneNo);
    }

    /**
     * Function to get username by phone number.
     * 
     * @param phoneNo The phone number of the user.
     * @return The username.
     * @throws UserNotFound If the user is not found.
     */
    public String getByName(long phoneNo) throws UserNotFound {
        logger.info("Fetching username for phone number: {}", phoneNo);
        User user = userRepository.findById(phoneNo).orElseThrow(() -> new UserNotFound("User not found"));
        return user.getUserName();
    }

    /**
     * Function to get role by phone number.
     * 
     * @param phoneNo The phone number of the user.
     * @return The role of the user.
     * @throws UserNotFound             If the user is not found.
     * @throws UserRoleNotFoundException If the role is not found.
     */
    public Role getRoleByPhoneNo(long phoneNo) throws UserNotFound, UserRoleNotFoundException {
        logger.info("Fetching role for phone number: {}", phoneNo);
        User user = userRepository.findById(phoneNo)
                .orElseThrow(() -> new UserNotFound("User not found"));
        Role role = user.getRole();
        if (role == null) {
            logger.error("Role not found for user with phone number: {}", phoneNo);
            throw new UserRoleNotFoundException("Role not found for user with phone number: " + phoneNo);
        }
        return role;
    }
}
