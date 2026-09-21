package com.enotes.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.enotes.entity.User;
import com.enotes.repo.UserRepository;

@Service
public class UserdetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = repository.findByEmail(email);
		if(user==null) {
			throw new UsernameNotFoundException("invalid email");
		}
		
		return new CustomUserDetails(user);
	}

}
