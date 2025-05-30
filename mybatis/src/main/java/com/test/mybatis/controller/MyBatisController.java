package com.test.mybatis.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.mybatis.dto.AddressDTO;
import com.test.mybatis.mapper.AddressMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MyBatisController {

	private final AddressMapper mapper;
	
	@GetMapping("/time")
	public String time() {
		
		return mapper.time();
	}
	
	@GetMapping("/address")
	public List<AddressDTO> list() {
		
		return mapper.list();
	}
		
}












