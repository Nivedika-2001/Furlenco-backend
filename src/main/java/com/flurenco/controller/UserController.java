package com.flurenco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flurenco.dto.UserDTO;
import com.flurenco.entity.Role;
import com.flurenco.exception.UserNotFound;
import com.flurenco.exception.UserRoleNotFoundException;
import com.flurenco.exception.DuplicateRecordException;
import com.flurenco.service.EmailService;
import com.flurenco.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/User")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;
	
	/**
	 * Saves a user record.
	 * 
	 * @param userDTO The UserDTO object containing user data.
	 * @return The saved UserDTO object.
	 * @throws DuplicateRecordException If a duplicate user record is found.
	 */
	@PostMapping("/save")
	public ResponseEntity<UserDTO> saveRecord(@RequestBody UserDTO userDTO) throws DuplicateRecordException {
		try {
			return new ResponseEntity<UserDTO>(userService.saveRecord(userDTO), HttpStatus.ACCEPTED);
		} catch (DuplicateRecordException e) {
			throw new DuplicateRecordException(e.getMessage(),e);
		}
	}

	/**
	 * Fetches a user record by phone number.
	 * 
	 * @param phoneNo The phone number of the user to fetch.
	 * @return A ResponseEntity containing a boolean indicating if the user exists.
	 */
	@GetMapping("/fetch/{phoneNo}")
	public ResponseEntity<?> fetchRecord(@PathVariable long phoneNo) {
		return new ResponseEntity<Boolean>(userService.fetchRecord(phoneNo), HttpStatus.ACCEPTED);
	}

	/**
	 * Gets the name of the user by phone number.
	 * 
	 * @param phoneNo The phone number of the user.
	 * @return The name of the user.
	 * @throws UserNotFound If the user is not found.
	 */
	@GetMapping("/getName/{phoneNo}")
	public ResponseEntity<String> getByName(@PathVariable long phoneNo) throws UserNotFound {
		try {
			return new ResponseEntity<String>(userService.getByName(phoneNo), HttpStatus.ACCEPTED);
		} catch (UserNotFound e) {
			throw new UserNotFound(e.getMessage(),e);
		}
	}

	/**
	 * Gets the role of the user by phone number.
	 * 
	 * @param phoneNo The phone number of the user.
	 * @return The Role object associated with the user.
	 * @throws UserNotFound          If the user is not found.
	 * @throws UserRoleNotFoundException If the role of the user is not found.
	 */
	@GetMapping("/getRoleByPhoneNo/{phoneNo}")
	public ResponseEntity<Role> getRoleByPhoneNo(@PathVariable long phoneNo)
			throws UserNotFound, UserRoleNotFoundException {
		try {
			return new ResponseEntity<Role>(userService.getRoleByPhoneNo(phoneNo), HttpStatus.ACCEPTED);
		} catch (UserNotFound e) {
			throw new UserNotFound(e.getMessage(),e);
		} catch (UserRoleNotFoundException e) {
			throw new UserRoleNotFoundException(e.getMessage(),e);
		}
	}

	/**
	 * Sends an email upon application startup.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void sendMail() {
		// Code to send mail
	}
}
