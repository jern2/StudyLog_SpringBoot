package com.test.security.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.security.dto.CustomUserDetails;
import com.test.security.entity.Member;
import com.test.security.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Member> member = memberRepository.findByUsername(username);
		
		if (member.isPresent()) {
			return new CustomUserDetails(member.get());
		} else {
			return null; //로그인 실패
		}
		
	}
	
}













