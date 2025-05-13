package com.test.oauth2.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.test.oauth2.dto.CustomOAuth2User;
import com.test.oauth2.entity.User;
import com.test.oauth2.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	private final UserRepository userRepository;
	private final HttpSession session;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		
		//인증 직후에 이 메서드 호출
		System.out.println("성공 후 호출");
		
		//인증받은 사람의 이메일?
		CustomOAuth2User oAuth2User =(CustomOAuth2User)authentication.getPrincipal();
		
		System.out.println(oAuth2User.getEmail());
		
		//DB 작업 > 이메일 존재 확인? > 회원 가입O,X
		Optional<User> user = userRepository.findByEmail(oAuth2User.getEmail());
		
		if (user.isPresent()) {
			//재방문 > 홈으로 이동
			super.onAuthenticationSuccess(request, response, authentication);
		} else {
			//첫방문 > 회원가입으로 이동
			session.setAttribute("user", oAuth2User);
			response.sendRedirect("/register");
		}
		
	}
	
}



















