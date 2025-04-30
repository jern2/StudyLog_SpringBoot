package com.test.thymeleaf.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.thymeleaf.dto.AddressDTO;
import com.test.thymeleaf.mapper.AddressMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {

	private final AddressMapper mapper;
	
	@GetMapping("/m1")
	public String m1(Model model) {
		
		System.out.println("m1");
		
		//단일 값 전달
		int count = mapper.count();
		String name = mapper.getName(1);
		
		
		//객체 전달
		AddressDTO dto = mapper.get(2);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("red","사과");
		map.put("blue","하늘");
		map.put("yellow","귤");
		
		model.addAttribute("count",count);
		model.addAttribute("name",name);
		model.addAttribute("dto",dto);
		model.addAttribute("map",map);
		
		//resources > templates > m1.html
		return "m1";
	}
	
	@GetMapping("/m2")
	public String m2(Model model) {
		
		//스프링 메세지,Spring Message
		//프로젝트 내에서 자주 사용되는 (반복되는)문자열 관리하는 기능
		//상주 문자열
		//다국어 지원
		
		//src/main messages.properties
		
		return "m2";
	}
	
	
	@GetMapping("/m3")
	public String m3(Model model) {
		
		
		int a= 10;
		int b = 3;
		String name="홍길동";
		
		model.addAttribute("a",a);
		model.addAttribute("b",b);
		model.addAttribute("name",name);
		
		return "m3";
	}
	
	@GetMapping("/m4")
	public String m4(Model model) {
		
		model.addAttribute("count", mapper.count());
		model.addAttribute("name", mapper.getName(3));
		model.addAttribute("color", "cornflowerblue");
	
		
		return "m4";
	}
	
	@GetMapping("/m5")
	public String m5(Model model) {
		
		String txt1 = "홍길동입니다.";
		String txt2 = "<b>홍길동</b>입니다";
	
			//단일 값 전달
			int count = mapper.count();
			String name = mapper.getName(1);
			
			
			//객체 전달
			AddressDTO dto = mapper.get(2);
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("red","사과");
			map.put("blue","하늘");
			map.put("yellow","귤");
			
			List<String>names= mapper.names();
			List<AddressDTO> list = mapper.list();
			
			
			model.addAttribute("count",count);
			model.addAttribute("name",name);
			model.addAttribute("dto",dto);
			model.addAttribute("map",map);
			model.addAttribute("txt1", txt1);
			model.addAttribute("txt2", txt2);
			model.addAttribute("names", names);
			model.addAttribute("list", list);
			
		return "m5";
	}
	
	@GetMapping("/m6")
	public String m6(Model model) {
		
		int num1 = 1234567;
		double num2 = 12345.6789;
		Calendar now = Calendar.getInstance();
		
		model.addAttribute("num1", num1);		
		model.addAttribute("num2", num2);		
		model.addAttribute("now", now);		
	
		
		return "m6";
	}
	@GetMapping("/m7")
	public String m7(Model model) {
		
		int seq= 10;
		String mode= "add";
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("search", "y");
		map.put("column", "content");
		map.put("word", "자바");
		
		model.addAttribute("seq", seq);
		model.addAttribute("mode", mode);
		model.addAttribute("map", map);
		
		return "m7";
	}
}
