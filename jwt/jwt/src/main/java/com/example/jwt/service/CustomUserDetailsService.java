package com.example.jwt.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jwt.domain.SecurityUser;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("custom userService");
		System.out.println("username = " + username);

		// 여기서 유저 정보를 담아 디비에서 끌어와서
		// id, pwd .. etc

		return new SecurityUser(username, "a123");
	}
}
