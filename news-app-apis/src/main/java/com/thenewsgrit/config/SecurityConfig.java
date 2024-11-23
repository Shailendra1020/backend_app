package com.thenewsgrit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.thenewsgrit.security.CustomUserDetailService;
import com.thenewsgrit.security.JwtAuthenticationEntryPoint;
import com.thenewsgrit.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity(prePostEnabled = true) // Replaces the deprecated @EnableGlobalMethodSecurity
		public class SecurityConfig {

	     public static final String[] PUBLIC_URLS = {
	    		 "/api/v1/auth/**", 
	    		 "/v3/api-docs",
	    		 "/v2/api-docs",
	    		 "/swagger-resources/**",
	    		 "/swagger-ui/**",
	    		 "/webjars/**"
	    		 };
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	 @Autowired
	    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
 
	 @Autowired
	    private JwtAuthenticationFilter jwtAuthenticationFilter; // Assuming this filter is created

	 @Bean
	 public DefaultSecurityFilterChain configure(HttpSecurity http) throws Exception {
	
	 http.csrf()
	        .disable()
	        .authorizeHttpRequests() // New method for request matching
	            .requestMatchers(PUBLIC_URLS).permitAll()
	            .requestMatchers(HttpMethod.GET).permitAll()
	            .anyRequest().authenticated() // secure all other endpoints
	            .and()
	            .exceptionHandling()
	            .authenticationEntryPoint(this.jwtAuthenticationEntryPoint) // Handle unauthorized access
	            .and()
	            .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Stateless sessions for JWT

	        // Adding JWT authentication filter
	        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	        return http.build(); // Return SecurityFilterChain
	 }

	 
   
    
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    	
    	auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder(); // BCryptPasswordEncoder for password hashing
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    
    
    //public AuthenticationManager authenticationManagerBean() throws Exception {
      //  return super.authenticationManagerBean();
    	  
}

