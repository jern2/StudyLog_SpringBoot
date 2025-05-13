package com.test.oauth2.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.test.oauth2.dto.CustomOAuth2User;
import com.test.oauth2.dto.UserDTO;
import com.test.oauth2.entity.User;
import com.test.oauth2.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MyController {
	
	private final UserRepository userRepository;
	private final HttpSession session;

	@GetMapping("/login")
	public String login() {
		
		return "login";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		
		CustomOAuth2User oAuth2User = (CustomOAuth2User)session.getAttribute("user");
		
		if (oAuth2User == null) {
			//무단으로 register에 접근했다면
			return "redirect:/login";
		}
		
		model.addAttribute("user", oAuth2User);
		
		System.out.println("oAuth2User: " + oAuth2User);
		
		return "register";
	}
	
	@PostMapping("/registerok")
	public String registerok(Model model, UserDTO userDTO) {
		
		CustomOAuth2User oAuth2User = (CustomOAuth2User)session.getAttribute("user");
		
		if (oAuth2User == null) {
			return "redirect:/login";
		}
		
		//OAuth2 정보(oAuth2User) + 회원 직접 입력 정보(userDTO)
		
		//엔티티
		User user = User.builder()
						.username(oAuth2User.getUsername())
						.email(oAuth2User.getEmail())
						.name(oAuth2User.getName())
						.age(userDTO.getAge())
						.address(userDTO.getAddress())
						.build();
		
		userRepository.save(user);
		
		session.removeAttribute("user"); //회원 가입용
		
		return "redirect:/";
	}
	
}








