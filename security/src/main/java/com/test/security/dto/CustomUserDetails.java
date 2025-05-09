package com.test.security.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.test.security.entity.Member;

//인증 사용자 정보 객체
//- 구현된 메서드 > 개인 정보 열람 역할
public class CustomUserDetails implements UserDetails {
	
	//DTO or Entity > 회원의 추가 정보 저장 용도
	private Member member;
	
	public CustomUserDetails(Member member) {
		this.member = member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> authlist = new ArrayList<>();
		
		GrantedAuthority auth = new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return member.getRole();
			}
		};
		
		authlist.add(auth);
		
		return authlist;
	}

	@Override
	public String getPassword() {
		
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		
		return member.getUsername();
	}

}




