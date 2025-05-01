package com.test.thymeleaf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.test.thymeleaf.dto.AddressDTO;

@Mapper
public interface AddressMapper {
	
	String time();

	int count();

	String getName(int seq);

	AddressDTO get(int seq);

	List<String> names();

	List<AddressDTO> list();

}





