package com.thenewsgrit.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// get token from authorization header 
		
		String requestToken=request.getHeader("Authorization");
		
		// Bearer 23335435sdtifg
		
		System.out.println("JWT Token from request" + requestToken);
		
		String username=null;
		
		String token=null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer")) 
		{
			token = requestToken.substring(7);
		// System.out.println("Extracted JWT Token: " + token);	
			  try {
		    username = this.jwtTokenHelper.getUsernameFromToken(token);
		    System.out.println("Username extracted from token: " + username);
			} catch (IllegalArgumentException e) {
				System.out.println("unable to get Jwt token" + e.getMessage());
			} catch (ExpiredJwtException e){
				System.out.println("Jwt token has expired" + e.getMessage());
			} catch (MalformedJwtException e){
				System.out.println("Invalid jWT token: " + e.getMessage());
			}
			
		}else 
		{
			System.out.println("Jwt token does not begin with Bearer");
		}
		// once we get the token, now validate
		if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null) 
		{
			// Load user details from UserDetailsService
			    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			    System.out.println("User details loaded for user: " + username);
			    
			 // Validate token with user details
			if(this.jwtTokenHelper.validateToken(token, userDetails)) 
			{// shi chal rha h  
				// authentication karna h
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// Set the authentication in the SecurityContextHolder
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
				System.out.println("Authentication successful for user: " + username);
		} else {
		 System.out.println("Invalid jwt token");				
		} 
		
		}else {
			System.out.println("username is null or context is not null or already authenticated ");
		}
		  
		filterChain.doFilter(request, response);
		
	}

}
