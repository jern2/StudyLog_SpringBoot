package com.test.oauth2.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

//CustomerUserDetails > 인증 정보 객체(세션)

public class CustomOAuth2User implements OAuth2User {

	private final UserDTO userDTO;
	
	public CustomOAuth2User(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	
	@Override
	public Map<String, Object> getAttributes() {
		//사용 안함
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> list = new ArrayList<>();
		
		GrantedAuthority auth = () -> userDTO.getRole();
		
		list.add(auth);
		
		return list;
	}

	@Override
	public String getName() {
		
		return userDTO.getName();
	}
	
	public String getUsername() {
		
		return userDTO.getUsername();
	}

}











