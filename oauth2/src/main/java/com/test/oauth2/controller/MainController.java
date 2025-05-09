package com.test.oauth2.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

	@GetMapping("/")
	public String index(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		//로그인 아이디
		String username = auth.getName();
		
		//권한
		Collection<? extends GrantedAuthority> authorities =  auth.getAuthorities();
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		GrantedAuthority gauth = iter.next();
		String role = gauth.getAuthority();	
		
		model.addAttribute("username", username);
		model.addAttribute("role", role);
		model.addAttribute("authentication", auth);
		
		return "index";
	}
	
}












