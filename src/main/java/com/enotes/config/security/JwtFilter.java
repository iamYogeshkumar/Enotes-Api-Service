package com.enotes.config.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.enotes.handler.GenericResponse;
import com.enotes.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
		String authHeader = request.getHeader("Authorization");
		
		//Authorization = Bearer hsjdhsdhhhhfjdhfjhdfsjhfjdhskshjhsajhdhjkfdhkjfhjksdhjfhdhfuejhdjh
		String token=null;
		String username=null;
		
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			 token = authHeader.substring(7);
			  username = jwtService.extractUsername(token);
			  
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			Boolean validateToken = jwtService.validateToken(token, userDetails);
			if(validateToken) {
				UsernamePasswordAuthenticationToken authenticationToken = new 
						UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
			}
		}
		}catch (Exception e) {
//			e.printStackTrace();
			response.setContentType("application/json");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			
			Object error = GenericResponse.builder()
			.status(HttpStatus.UNAUTHORIZED.value())
			.msg(e.getMessage())
			.responseStatus(HttpStatus.UNAUTHORIZED)
			.build().create().getBody();
			response.getWriter().write(new ObjectMapper().writeValueAsString(error));
			
			return ;
		}	
		
		filterChain.doFilter(request, response);
		
	}

}
