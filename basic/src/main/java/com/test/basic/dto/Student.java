package com.test.basic.dto;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class Student {
	
	private Long seq;
	private String name;
	private Integer age;
	private String address;
	private String gender;

}
