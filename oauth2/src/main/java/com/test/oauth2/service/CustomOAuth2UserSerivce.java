package com.test.oauth2.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.test.oauth2.Oauth2Application;
import com.test.oauth2.controller.MyController;
import com.test.oauth2.dto.CustomOAuth2User;
import com.test.oauth2.dto.GoogleResponse;
import com.test.oauth2.dto.NaverResponse;
import com.test.oauth2.dto.OAuth2Response;
import com.test.oauth2.dto.UserDTO;

@Service
public class CustomOAuth2UserSerivce extends DefaultOAuth2UserService {


	//리소스 서버로부터 받아오는 개인 정보 > 네이버 회원 정보
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		System.out.println("============================");
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		System.out.println("받아온 개인 정보: " + oAuth2User);
		
		
		//네이버 or 구글 확인
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		
		/*
			OAuth2Response
			- 소셜 제공자마다 반환 데이터의 구조가 다르다.
			- 공통 타입 <- 상속 각각 구현
			- OAuth2Response <- NaverResponse
			                 <- GoogleResponse
			
			네이버 데이터
			{
				resultcoded=00,
				message=success,
				response={
					id=111,
					name=홍길동
				}
			}
			
			구글 데이터
			{
				resultcoded=00,
				message=success,
				id=111,
				name=홍길동
			}

		*/
		
		OAuth2Response oAuth2Response = null; 
		
		if (registrationId.equals("naver")) {
			
			oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
			
		} else if (registrationId.equals("google")) {
			
			oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
			
		}
		
		//받아온 네이버 정보 > 스프링 시큐리티
		UserDTO userDTO = new UserDTO();
		
		//네이버: hong > naver hong
		//구글: hong > google hong
		
		userDTO.setUsername(oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId());
		userDTO.setName(oAuth2Response.getName());
		userDTO.setRole("ROLE_MEMBER");
		userDTO.setEmail(oAuth2Response.getEmail());
		
		return new CustomOAuth2User(userDTO); 
	}
	
}























