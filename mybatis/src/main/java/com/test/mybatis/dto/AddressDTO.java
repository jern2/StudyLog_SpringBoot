package com.test.mybatis.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//@Alias("adto")
public class AddressDTO {
	private String seq;
	private String name;
	private String age;
	private String address;
	private String gender;
}



