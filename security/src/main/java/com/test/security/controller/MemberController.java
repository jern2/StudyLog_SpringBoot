package com.test.security.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.test.security.dto.MemberDTO;
import com.test.security.entity.Member;
import com.test.security.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/member")
	public String member(Model model) {
		
		return "member";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		
		return "register";
	}
	
	@PostMapping("/registerok")
	public String registerok(Model model, MemberDTO dto) {
		
		//DB > MemberRepository
		//암호 > BCryptPasswordEncoder
		
		//아이디 중복 검사
		boolean result = memberRepository.existsByUsername(dto.getUsername());
		System.out.println(result);
		
		if (result) {
			return "redirect:/register";
		}
		
		//repo.save(entity) == insert
		//DTO > Entity
		Member member = Member.builder()
						.username(dto.getUsername())
				.password(bCryptPasswordEncoder.encode(dto.getPassword()))
						.role(dto.getRole())
						.build();
		
		memberRepository.save(member);		
		
		return "redirect:/login";
	}
	
}











