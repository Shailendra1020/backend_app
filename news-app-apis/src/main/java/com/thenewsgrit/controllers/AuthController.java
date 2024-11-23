package com.thenewsgrit.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thenewsgrit.exceptions.ApiException;
import com.thenewsgrit.payloads.JwtAuthRequest;
import com.thenewsgrit.payloads.JwtAuthResponse;
import com.thenewsgrit.payloads.UserDto;
import com.thenewsgrit.security.JwtTokenHelper;
import com.thenewsgrit.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@Valid @RequestBody JwtAuthRequest request) throws Exception{
	
		this.authenticate(request.getUsername(), request.getPassword());
		
		// Load user details after successful authentication
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
	
		// Generate the JWT token
	    String token = this.jwtTokenHelper.generateToken(userDetails);
		
	    // Set and return the response with the token
		JwtAuthResponse response=new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
		
	}
	// Method to authenticate credentials 
	private void authenticate(String username, String password) throws Exception {
		
	   UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, password);
	
	   try {
		   // this will authenticate the credentials 
	        this.authenticationManager.authenticate(authenticationToken);
	   }
	   catch (BadCredentialsException e) {
		   System.out.println("Invalid Details !!");
	       throw new ApiException("Invalid username or password !!");
	   
	   }     
	   
	} 
	
	// register new user api
    @PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto)
      {
		UserDto registeredUser = this.userService.registerNewUser(userDto);
	
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
	}
			
}